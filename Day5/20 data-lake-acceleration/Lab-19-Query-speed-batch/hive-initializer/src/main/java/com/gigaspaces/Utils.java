package com.gigaspaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {
    public static String hiveUrl = "jdbc:hive2://hive-server:10000/;ssl=false";

    public static void query(String sql) throws SQLException {
        System.out.println("Running query: " + sql);
        try (Connection connection = DriverManager.getConnection(hiveUrl)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }
    }

    public static void dropTable(String tableName) throws SQLException {
        String sqlStatementDrop = "DROP TABLE IF EXISTS " + tableName;
        query(sqlStatementDrop);
    }
}
