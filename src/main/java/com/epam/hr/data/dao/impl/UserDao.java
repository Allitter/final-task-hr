package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.impl.UserMapper;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final String TABLE = "user";
    private static final String USER_BY_LOGIN_AND_PASSWORD_QUERY = String.format("select * from %s where login = ? and password = sha2(?, 256);", TABLE);
    private static final String ALL_EMPLOYEES = String.format("select * from %s where role = 'EMPLOYEE' limit ?, ?;", TABLE) ;
    private static final String ALL_JOB_SEEKERS = String.format("select * from %s where role = 'JOB_SEEKER' limit ?, ?;", TABLE) ;
    private static final String UPDATE_QUERY = String.format("update %s set login = ?, name = ?, last_name = ?, patronymic = ?, birth_date = ?, email = ?, phone = ?, enabled = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (login, password, name, last_name, patronymic, birth_date, role, email, phone) values (?, sha2(?, 256), ?, ?, ?, ?, ?, ?, ?);", TABLE) ;
    private static final String BAN_USER_QUERY = String.format("update %s set banned = 1 where id = ?;", TABLE);
    private static final String UNBAN_USER_QUERY = String.format("update %s set banned = 0 where id = ?;", TABLE) ;
    private static final String USER_BY_ID_QUERY = String.format("select * from %s where id = ?;", TABLE) ;
    private static final String USER_BY_LOGIN = String.format("select * from %s where login = ?;", TABLE) ;

    private static final String EMPLOYEE_CONDITION = "role = 'EMPLOYEE'";
    private static final String JOB_SEEKER_CONDITION = "role = 'JOB_SEEKER'";

    private final UserMapper mapper = new UserMapper();

    public UserDao(Connection connection) {
        super(connection);
    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_LOGIN_AND_PASSWORD_QUERY, mapper, login, password);
    }

    public void banUser(long id) throws DaoException {
        executeNoResultQueryPrepared(BAN_USER_QUERY, id);
    }

    public void unbanUser(long id) throws DaoException {
        executeNoResultQueryPrepared(UNBAN_USER_QUERY, id);
    }

    public List<User> findAll(UserRole role, int start, int count) throws DaoException {
        if (role != UserRole.EMPLOYEE && role != UserRole.JOB_SEEKER) {
            throw new DaoException("Incorrect role for query");
        }

        String query = role == UserRole.EMPLOYEE ? ALL_EMPLOYEES : ALL_JOB_SEEKERS;

        return executeQueryPrepared(query, mapper, start, count);
    }

    @Override
    public Optional<User> getById(long id) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_ID_QUERY, mapper, id);
    }

    @Override
    public List<User> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    public Optional<User> findByLogin(String login) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_LOGIN, mapper, login);
    }

    @Override
    public void removeById(long id) throws DaoException {
        removeById(TABLE, id);
    }

    @Override
    public int findQuantity() throws DaoException {
        return findQuantity(TABLE);
    }

    public int findQuantity(UserRole role) throws DaoException {
        if (role != UserRole.EMPLOYEE && role != UserRole.JOB_SEEKER) {
            throw new DaoException("Incorrect role for query");
        }

        String condition = role == UserRole.EMPLOYEE ? EMPLOYEE_CONDITION : JOB_SEEKER_CONDITION;
        return findQuantity(TABLE, condition);
    }

    @Override
    public void save(User user) throws DaoException {
        long id = user.getId();
        String login = user.getLogin();
        String name = user.getName();
        String password = user.getPassword();
        String lastName = user.getLastName();
        String patronymic = user.getPatronymic();
        String birthDate = user.getFormattedBirthDate();
        String email = user.getEmail();
        String phone = user.getPhone();
        UserRole role = user.getRole();
        String roleName = role.name();
        boolean enabled = user.isEnabled();

        Optional<User> optional = getById(id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, login, name, lastName,
                    patronymic, birthDate, email, phone, enabled, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, login, password, name,
                    lastName, patronymic, birthDate, roleName, email, phone);
        }
    }
}
