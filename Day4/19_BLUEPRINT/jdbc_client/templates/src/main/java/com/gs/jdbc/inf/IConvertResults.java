package com.gs.jdbc.inf;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public interface IConvertResults {
    public IResponse getRow(ResultSet resultSet, ResultSetMetaData md) throws SQLException;
}
