package com.gs;

import org.openspaces.core.GigaSpace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AbsractJDBCClient {
    protected Connection connect(GigaSpace gigaSpace) throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        // properties.put("readModifiers", ReadModifiers.MEMORY_ONLY_SEARCH);
        return DriverManager.getConnection("jdbc:gigaspaces:v3://localhost:4174/" + gigaSpace.getSpaceName(), properties);
    }

    protected void readTable(Connection connection, String table) throws SQLException {
        String query = "select * from \"" +  table +"\"";
        Statement statement = connection.createStatement();
        try {
            dumpResult(statement.executeQuery(query));
        }
        catch (Throwable t){

            System.out.println("Fail to run query:" + t);
            t.printStackTrace();
        }
        finally {
            statement.close();
        }
    }



    public List<String> dumpResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        List<String> rows = new ArrayList<>();
        for (int k=1; k<= columnsNumber; k++) {
            if (k > 1) System.out.print(",  ");
            System.out.print(rsmd.getColumnName(k));
        }

        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue);
                rows.add(columnValue);
            }
            System.out.println();
        }
        return rows;
    }

}
