package com.gs.jdbc.impl;

import com.gs.jdbc.inf.IRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class {{service.name}}Request implements IRequest {
    String query;
    ArrayList<Object> parameters = new ArrayList<>();;
    public {{service.name}}Request(String query, Object ... parameters) {
        this.query = query;
        Arrays.stream(parameters).forEach(p-> this.parameters.add(p));
        if (!isValid())
            throw new IllegalArgumentException("Request is not valid number of paramters dosn't match number of ? in the query");
    }

    @Override
    public ArrayList<Object> getParameters() {
        return parameters;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    /*
    Checks if number of ? is the same as supplied number of parameters
     */
    public boolean isValid() {
        long splits = query.chars().filter(num -> num == '?').count();
        if ((parameters == null && splits==0) || (parameters != null && parameters.size() == splits))
            return  true;
        return false;
    }
}
