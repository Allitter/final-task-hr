package com.epam.hr.data.dao;

import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.DaoException;
import com.epam.hr.data.mapper.UserMapper;
import com.epam.hr.domain.model.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final String TABLE = "user u join role r on u.id_role = r.id";
    private static final String ALL_USER_ATTRIBUTES = "u.id, u.login, u.password, u.name, u.last_name, u.patronymic, u.birth_date, r.name role_name";
    private static final String USER_BY_LOGIN_AND_PASSWORD_QUERY = "select " + ALL_USER_ATTRIBUTES + " from " + TABLE + " where u.login = ? and u.password = ?;";
    private static final String ALL_EMPLOYEES = "select " + ALL_USER_ATTRIBUTES + " from " + TABLE + " where u.id_role = 3 limit ?, ?;";
    private static final String ALL_JOB_SEEKERS = "select " + ALL_USER_ATTRIBUTES + " from " + TABLE + " where u.id_role = 2 limit ?, ?;";
    private static final String UPDATE_QUERY = "update user set login = ?, password = ?, name = ?, last_name = ?, patronymic = ?, birth_date = ?, where id = ?;";
    private static final String INSERT_QUERY = "insert into user (login, password, name, last_name, patronymic, birth_date, role_id) values (?, ?, ?, ?, ?, ?, ?);";
    private static final String EMPLOYEE_CONDITION = "u.id_role = 3";
    private static final String JOB_SEEKER_CONDITION = "u.id_role = 2";
    private final UserMapper mapper = new UserMapper();

    public Optional<User> getUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_LOGIN_AND_PASSWORD_QUERY, mapper, login, password);
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
        return findById(TABLE, mapper, id);
    }

    @Override
    public List<User> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        removeById(TABLE, id);
    }

    @Override
    public int findQuantity() throws DaoException {
        return findQuantity(TABLE);
    }

    @Override
    public void save(User user) throws DaoException {
        long id = user.getId();
        String login = user.getLogin();
        String password = user.getPassword();
        String name = user.getName();
        String lastName = user.getLastName();
        String patronymic = user.getPatronymic();
        Date birthDate = user.getBirthDate();
        UserRole role = user.getRole(); // TODO

        Optional<User> optional = findById(TABLE, mapper, id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, login, password, name, lastName, patronymic, birthDate, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, login, password, name, lastName, password, birthDate, role.ordinal());
        }
    }

    public int findQuantity(UserRole role) throws DaoException {
        if (role != UserRole.EMPLOYEE && role != UserRole.JOB_SEEKER) {
            throw new DaoException("Incorrect role for query");
        }

        String condition = role == UserRole.EMPLOYEE ? EMPLOYEE_CONDITION : JOB_SEEKER_CONDITION;
        return findQuantity(TABLE, condition);
    }
}
