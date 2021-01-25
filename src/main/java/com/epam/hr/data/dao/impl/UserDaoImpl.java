package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.UserDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.util.DateUtils;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String TABLE = "user";
    private static final String USER_BY_LOGIN_AND_PASSWORD_QUERY = String.format("select * from %s where login = ? and password = sha2(?, 256) and removed = 0;", TABLE);
    private static final String ALL_EMPLOYEES = String.format("select * from %s where role = 'EMPLOYEE' and removed = 0 limit ?, ?;", TABLE);
    private static final String ALL_JOB_SEEKERS = String.format("select * from %s where role = 'JOB_SEEKER' and removed = 0 limit ?, ?;", TABLE);
    private static final String UPDATE_QUERY = String.format("update %s set login = ?, name = ?, last_name = ?, patronymic = ?, birth_date = ?, email = ?, phone = ?, enabled = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (login, password, name, last_name, patronymic, birth_date, role, email, phone, avatar_change_date) values (?, sha2(?, 256), ?, ?, ?, ?, ?, ?, ?, ?);", TABLE);
    private static final String BAN_USER_QUERY = String.format("update %s set banned = 1 where id = ?;", TABLE);
    private static final String UNBAN_USER_QUERY = String.format("update %s set banned = 0 where id = ?;", TABLE);
    private static final String USER_BY_ID_QUERY = String.format("select * from %s where id = ? and removed = 0;", TABLE);
    private static final String USER_BY_LOGIN = String.format("select * from %s where login = ? and removed = 0;", TABLE);
    private static final String SET_AVATAR_PATH_QUERY = String.format("update %s set avatar = ?, avatar_change_date = ? where id = ?;", TABLE);
    private static final String AVATAR_LAST_CHANGE_DATE_QUERY = String.format("select avatar_change_date as date from %s where id = ?;", TABLE);
    private static final String EMPLOYEE_CONDITION = "role = 'EMPLOYEE'";
    private static final String JOB_SEEKER_CONDITION = "role = 'JOB_SEEKER'";
    private static final int REGISTRATION_AVATAR_CHANGE_DATE_OFFSET_IN_DAYS = -1;
    private final Mapper<User> mapper;
    private final Mapper<Date> dateMapper;


    public UserDaoImpl(Connection connection, Mapper<User> mapper, Mapper<Date> dateMapper) {
        this(connection, mapper, dateMapper, true);
    }

    public UserDaoImpl(Connection connection, Mapper<User> mapper, Mapper<Date> dateMapper, boolean canCloseConnection) {
        super(canCloseConnection, connection);
        this.mapper = mapper;
        this.dateMapper = dateMapper;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_LOGIN_AND_PASSWORD_QUERY, mapper, login, password);
    }

    @Override
    public void banUser(long idUser) throws DaoException {
        executeNoResultQueryPrepared(BAN_USER_QUERY, idUser);
    }

    @Override
    public void unbanUser(long idUser) throws DaoException {
        executeNoResultQueryPrepared(UNBAN_USER_QUERY, idUser);
    }

    @Override
    public List<User> findAll(User.Role role, int start, int count) throws DaoException {
        if (role != User.Role.EMPLOYEE && role != User.Role.JOB_SEEKER) {
            throw new DaoException("Incorrect role for query");
        }

        String query = role == User.Role.EMPLOYEE ? ALL_EMPLOYEES : ALL_JOB_SEEKERS;

        return executeQueryPrepared(query, mapper, start, count);
    }

    @Override
    public Optional<User> findById(long idUser) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_ID_QUERY, mapper, idUser);
    }

    @Override
    public List<User> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return executeSingleResultQueryPrepared(USER_BY_LOGIN, mapper, login);
    }

    @Override
    public void removeById(long idUser) throws DaoException {
        removeById(TABLE, idUser);
    }

    @Override
    public int getRowCount() throws DaoException {
        return getRowCount(TABLE);
    }

    @Override
    public void setAvatarPath(long idUser, String path) throws DaoException {
        Date date = DateUtils.currentDate();
        executeNoResultQueryPrepared(SET_AVATAR_PATH_QUERY, path, date, idUser);
    }

    @Override
    public int getUsersCountByRole(User.Role role) throws DaoException {
        if (role != User.Role.EMPLOYEE && role != User.Role.JOB_SEEKER) {
            throw new DaoException("Incorrect role for query");
        }

        String condition = role == User.Role.EMPLOYEE ? EMPLOYEE_CONDITION : JOB_SEEKER_CONDITION;
        return getRowCount(TABLE, condition);
    }

    @Override
    public Date getAvatarLastChangeDate(long idUser) throws DaoException {
        Optional<Date> optional = executeSingleResultQueryPrepared(AVATAR_LAST_CHANGE_DATE_QUERY, dateMapper, idUser);
        return optional.orElseThrow(DaoException::new);
    }

    @Override
    public void save(User user) throws DaoException {
        if (!user.isValid()) {
            throw new DaoException("attempt to save invalid object");
        }

        long id = user.getId();
        String login = user.getLogin();
        String name = user.getName();
        String password = user.getPassword();
        String lastName = user.getLastName();
        String patronymic = user.getPatronymic();
        String birthDate = user.getBirthDate();
        String email = user.getEmail();
        String phone = user.getPhone();

        Optional<User> optional = findById(id);
        if (optional.isPresent()) {
            boolean enabled = user.isEnabled();
            executeNoResultQueryPrepared(UPDATE_QUERY, login, name, lastName,
                    patronymic, birthDate, email, phone, enabled, id);
        } else {
            Date avatarLastChangeDate = DateUtils.calculateOffsetDateInDays(
                    REGISTRATION_AVATAR_CHANGE_DATE_OFFSET_IN_DAYS);
            User.Role role = user.getRole();
            String roleName = role.name();

            executeNoResultQueryPrepared(INSERT_QUERY, login, password, name,
                    lastName, patronymic, birthDate, roleName, email, phone,
                    avatarLastChangeDate);
        }
    }
}
