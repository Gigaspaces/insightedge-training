package com.gs.jdbc.impl;

import com.gs.jdbc.inf.IConvertResults;
import com.gs.jdbc.inf.IResponse;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class {{service.name}}ConvertResults implements IConvertResults {
    @Override
    public IResponse getRow(ResultSet resultSet, ResultSetMetaData md) throws SQLException {
        {{service.name}}Response response= new {{service.name}}Response();

        for (int i = 1; i <= md.getColumnCount(); ++i) {
            response.put(md.getColumnName(i), resultSet.getObject(i));
        }
        return response;

    }
}
