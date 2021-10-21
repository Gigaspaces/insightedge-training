package com.gs.jdbc.impl;

import com.gs.jdbc.inf.IConvertResults;
import com.gs.jdbc.inf.IRequest;
import com.gs.jdbc.inf.IResponse;
import org.openspaces.core.GigaSpace;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class Service {
    Connection connection;

    /*
    Intialize connection according to parameters
     */
    public Service(GigaSpace gigaSpace,boolean v3Driver){
        String url;
        if (v3Driver) {
            String locators = gigaSpace.getSpace().getFinderURL().getProperty("locators");
            locators = (locators != null ? locators : "localhost:4174");
            url = "jdbc:gigaspaces:v3://" + locators + "/" + gigaSpace.getSpaceName();
        }
        else {
            try {
                Class.forName("com.j_spaces.jdbc.driver.GDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            url = "jdbc:gigaspaces:url:jini://*/*/" + gigaSpace.getSpaceName();
        }

        try {
            connection = DriverManager.getConnection(url,new Properties() );
        } catch (SQLException e) {
             e.printStackTrace();
             throw new IllegalArgumentException("Unable to start JDBC connection: " + e);
        }
    }

    /*
    Execute the request and return the results
     */
    public Collection<IResponse> execute(IRequest request, IConvertResults convertResults){
        if (!request.isValid()) throw new IllegalArgumentException("Request is not valid");
        ArrayList<IResponse> responses = new ArrayList<>(100);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(request.getQuery());
            if (request.getParameters() != null) {
                for (int index = 0; index < request.getParameters().size(); index++) {
                    preparedStatement.setObject(index+1, request.getParameters().get(index));
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                IResponse response = convertResults.getRow(resultSet, resultSetMetaData);
                responses.add(response);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Request can't be executed : " + e.getMessage());
        }
        return responses;
    }

    public void close(){
        try {
            connection.close();
        }
        catch (Exception e){
            System.out.println("==== Fail to close connection");
        }
    }


}
