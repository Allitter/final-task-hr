package com.epam.hr.domain.service;

import com.epam.hr.data.dao.factory.impl.UserDaoFactory;
import com.epam.hr.data.dao.impl.UserDao;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserDataHolder;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.domain.util.DateUtils;
import com.epam.hr.domain.validator.UserValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.*;

public class UserService {
    private static final String LOGIN_NOT_UNIQUE_FAIL = "loginNotUnique";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "incorrectLoginOrPassword";
    private final UserValidator userValidator;
    private final UserDaoFactory userDaoFactory;

    public UserService(UserValidator userValidator, UserDaoFactory userDaoFactory) {
        this.userValidator = userValidator;
        this.userDaoFactory = userDaoFactory;
    }

    public User authenticateUser(String login, String password) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            List<String> fails = new LinkedList<>();
            fails.addAll(userValidator.validateLogin(login));
            fails.addAll(userValidator.validatePassword(password));
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }

            Optional<User> optionalUser = dao.findUserByLoginAndPassword(login, password);
            if (!optionalUser.isPresent()) {
                throw new ValidationException(Collections.singletonList(INCORRECT_LOGIN_OR_PASSWORD));
            }

            return optionalUser.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> banUser(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            dao.banUser(id);
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> unbanUser(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            dao.unbanUser(id);
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> findById(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User tryFindById(long id) throws ServiceException {
        Optional<User> optionalUser = findById(id);
        if (!optionalUser.isPresent()) {
            throw new ServiceException("User doesn't exist");
        }

        return optionalUser.get();
    }

    public List<User> findEmployees(int start, int count) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findAll(UserRole.EMPLOYEE, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> findJobSeekers(int start, int count) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findAll(UserRole.JOB_SEEKER, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int findEmployeesQuantity() throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findQuantity(UserRole.EMPLOYEE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int findJobSeekersQuantity() throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findQuantity(UserRole.JOB_SEEKER);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User addUser(UserDataHolder userDataHolder) throws ServiceException {
        List<String> validationFails = userValidator.validateForRegister(userDataHolder);
        Date date = DateUtils.tryParse(userDataHolder.getBirthDate());
        User user = new User.Builder(userDataHolder)
                .setRole(UserRole.JOB_SEEKER)
                .setBirthDate(date)
                .setEnabled(false)
                .build();

        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> userOptional = dao.findByLogin(userDataHolder.getLogin());
            if (userOptional.isPresent()) {
                validationFails.add(LOGIN_NOT_UNIQUE_FAIL);
            }
            if (!validationFails.isEmpty()) {
                throw new ValidationException(validationFails);
            }

            dao.save(user);
            Optional<User> optionalUser = dao.findByLogin(userDataHolder.getLogin());
            // Always present as it is recently saved
            return optionalUser.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User updateUser(UserDataHolder userDataHolder) throws ServiceException {
        List<String> validationFails = userValidator.validateForUpdate(userDataHolder);
        Date date = DateUtils.tryParse(userDataHolder.getBirthDate());
        User updatedUser = new User.Builder(userDataHolder)
                .setBirthDate(date)
                .setBanned(false)
                .setEnabled(true)
                .build();

        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> userOptional = dao.findByLogin(userDataHolder.getLogin());
            if (userOptional.isPresent()) {
                User userByEmail = userOptional.get();
                if (userByEmail.getId() != userDataHolder.getId()) {
                    validationFails.add(LOGIN_NOT_UNIQUE_FAIL);
                }
            }
            if (!validationFails.isEmpty()) {
                throw new ValidationException(validationFails);
            }

            dao.save(updatedUser);
            return updatedUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User enable(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> optionalUser = dao.findById(id);
            if (!optionalUser.isPresent()) {
                throw new ServiceException("User not exists");
            }

            User user = optionalUser.get();
            user = new User.Builder(user)
                    .setEnabled(true)
                    .build();

            dao.save(user);

            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
