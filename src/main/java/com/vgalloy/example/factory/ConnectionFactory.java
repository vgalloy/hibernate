package com.vgalloy.example.factory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/02/16.
 */
public enum ConnectionFactory {
    INSTANCE;

    private Properties properties;
    private String url;

    ConnectionFactory() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            throw new RuntimeException("can not load Driver", e);
        }

        properties = new Properties();
        try {
            final InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(is);
            url = properties.getProperty("url");
        } catch (Exception e) {
            throw new RuntimeException("can not load config.properties", e);
        }

    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection", e);
        }
    }
}