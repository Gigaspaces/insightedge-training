package com.gs.jdbc.impl;

import com.gs.jdbc.inf.IResponse;

import java.util.HashMap;
import java.util.Map;

public class {{service.name}}Response extends HashMap<String,Object> implements IResponse {
    @Override
    public Map<String, Object> getResults() {
        return this;
    }
}
