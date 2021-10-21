package com.gs.jdbc.inf;

import java.util.ArrayList;

public interface IRequest {
    public ArrayList<Object> getParameters();
    public  String getQuery();
    public boolean isValid();
}
