package com.espresso.config.mysql;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * @author Mingze Ma
 */
public class MySQL5Utf8Dialect extends MySQL5Dialect {

    @Override
    public String getTableTypeString() {
        return "ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci";
    }
}
