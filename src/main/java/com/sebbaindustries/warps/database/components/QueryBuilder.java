package com.sebbaindustries.warps.database.components;

public class QueryBuilder {

    private String query;

    public QueryBuilder(String query) {
        this.query = query;
    }

    public Select select(String select) {
        // q:SELECT attribute_name ...
        return new Select(query + "SELECT " + select + " ");
    }

}

class Update {

}

class Alter {

}

