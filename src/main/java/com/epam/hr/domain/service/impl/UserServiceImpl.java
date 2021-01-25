package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.BanDao;
import com.epam.hr.data.dao.DaoManager;
import com.epam.hr.data.dao.UserDao;
import com.epam.hr.data.dao.factory.impl.BanDaoFactory;
import com.epam.hr.data.dao.factory.impl.UserDaoFactory;
import com.epam.hr.data.dao.impl.DaoManagerImpl;
import com.epam.hr.domain.model.Ban;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.domain.util.DateUtils;
import com.epam.hr.domain.validator.UserValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.EntityNotFoundException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final int AVATAR_CANT_CHANGE_OFFSET_IN_MINUTES = 60 * 24;
    private static final String LOGIN_NOT_UNIQUE_FAIL = "loginNotUnique";
    private static final String CANT_CHANGE_AVATAR_FAIL = "cantChangeAvatar";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "incorrectLoginOrPassword";
    private final UserValidator userValidator;
    private final UserDaoFactory userDaoFactory;
    private final BanDaoFactory banDaoFactory;

    public UserServiceImpl(UserValidator userValidator,
                           UserDaoFactory userDaoFactory,
                           BanDaoFactory banDaoFactory) {

        this.userValidator = userValidator;
        this.userDaoFactory = userDaoFactory;
        this.banDaoFactory = banDaoFactory;
    }

    @Override
    public User authenticateUser(String login, String password) throws ServiceException {
        if (!userValidator.validateLoginAndPassword(login, password)) {
            throw new ValidationException(INCORRECT_LOGIN_OR_PASSWORD);
        }

        try (UserDao dao = userDaoFactory.create()) {
            Optional<User> optionalUser = dao.findUserByLoginAndPassword(login, password);
            if (!optionalUser.isPresent()) {
                throw new ValidationException(INCORRECT_LOGIN_OR_PASSWORD);
            }

            return optionalUser.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> banUser(long idTarget, long idAdministrant, String reason) throws ServiceException {
        Ban ban = new Ban.Builder()
                .setReason(reason)
                .setIdTarget(idTarget)
                .setIdAdministrant(idAdministrant)
                .build(true);

        try (DaoManager daoManager = new DaoManagerImpl()) {
            BanDao banDao = daoManager.addDao(banDaoFactory);
            UserDao userDao = daoManager.addDao(userDaoFactory);

            daoManager.beginTransaction();

            banDao.save(ban);
            userDao.banUser(idTarget);

            daoManager.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return findById(idTarget);
    }

    @Override
    public Optional<User> unbanUser(long idUser) throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            dao.unbanUser(idUser);
            return dao.findById(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Ban> findLastBan(long idUser) throws ServiceException {
        try (BanDao dao = banDaoFactory.create()) {
            return dao.findLast(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Ban tryFindLastBan(long idUser) throws ServiceException {
        Optional<Ban> optional = findLastBan(idUser);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException("No ban records found");
        }

        return optional.get();
    }

    @Override
    public Optional<User> findById(long idUser) throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            return dao.findById(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User tryFindById(long idUser) throws ServiceException {
        Optional<User> optionalUser = findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User doesn't exist");
        }

        return optionalUser.get();
    }

    @Override
    public List<User> findEmployees(int start, int count) throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            return dao.findAll(User.Role.EMPLOYEE, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findJobSeekers(int start, int count) throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            return dao.findAll(User.Role.JOB_SEEKER, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findEmployeesQuantity() throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            return dao.getUsersCountByRole(User.Role.EMPLOYEE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findJobSeekersQuantity() throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            return dao.getUsersCountByRole(User.Role.JOB_SEEKER);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User addUser(User user) throws ServiceException {
        List<String> validationFails = userValidator.getValidationFailsForRegister(user);
        user = new User.Builder(user)
                .setRole(User.Role.JOB_SEEKER)
                .setEnabled(false)
                .build(true);

        try (UserDao dao = userDaoFactory.create()) {
            Optional<User> userOptional = dao.findByLogin(user.getLogin());
            if (userOptional.isPresent()) {
                validationFails.add(LOGIN_NOT_UNIQUE_FAIL);
            }
            if (!validationFails.isEmpty()) {
                throw new ValidationException(validationFails);
            }

            dao.save(user);
            Optional<User> optionalUser = dao.findByLogin(user.getLogin());
            return optionalUser.get(); // Always present as it is recently saved
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        List<String> validationFails = userValidator.getValidationFailsForUpdate(user);

        try (UserDao dao = userDaoFactory.create()) {
            if (!dao.findById(user.getId()).isPresent()) {
                throw new EntityNotFoundException();
            }

            Optional<User> userOptional = dao.findByLogin(user.getLogin());
            if (userOptional.isPresent()) {
                User userByEmail = userOptional.get();
                if (userByEmail.getId() != user.getId()) {
                    validationFails.add(LOGIN_NOT_UNIQUE_FAIL);
                }
            }
            if (!validationFails.isEmpty()) {
                throw new ValidationException(validationFails);
            }

            user = new User.Builder(user).build(true);

            dao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User enable(long idUser) throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            Optional<User> optionalUser = dao.findById(idUser);
            User user = optionalUser.orElseThrow(EntityNotFoundException::new);
            user = user.changeEnabled(true);

            dao.save(user);
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User setAvatar(long idUser, String avatarPath) throws ServiceException {
        if (!canChangeAvatar(idUser)) {
            throw new ValidationException(CANT_CHANGE_AVATAR_FAIL);
        }

        try (UserDao dao = userDaoFactory.create()) {
            dao.setAvatarPath(idUser, avatarPath);
            Optional<User> userOptional = dao.findById(idUser);
            return userOptional.orElseThrow(EntityNotFoundException::new);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean canChangeAvatar(long idUser) throws ServiceException {
        try (UserDao dao = userDaoFactory.create()) {
            Date lastChangeDate = dao.getAvatarLastChangeDate(idUser);
            Date canChangeAvatarDate = DateUtils.calculateOffsetDateInMinutes(
                    lastChangeDate, AVATAR_CANT_CHANGE_OFFSET_IN_MINUTES);

            return canChangeAvatarDate.before(DateUtils.currentDate());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
