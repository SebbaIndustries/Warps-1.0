package com.sebbaindustries.warps.database.components;

public class Select {

    private String query;

    public Select(String query) {
        this.query = query;
    }

    public QueryLogic from(String table) {
        // q:SELECT attribute_name FROM table_name ...
        return new QueryLogic(query + "FROM " + table + " ");
    }

}
