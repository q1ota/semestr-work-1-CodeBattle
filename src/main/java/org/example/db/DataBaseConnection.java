package org.example.db;

import org.example.utils.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.username";
    private static final String PASS_KEY = "db.password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASS_KEY)
        );
    }
}
