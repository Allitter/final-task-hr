package com.epam.hr.data.dao.factory;

import com.epam.hr.data.dao.AbstractDao;

import java.sql.Connection;

public interface DaoFactory<T extends AbstractDao> {

    T create();
    T create(Connection connection);

}
