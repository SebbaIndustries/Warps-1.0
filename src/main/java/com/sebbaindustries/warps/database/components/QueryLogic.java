package com.sebbaindustries.warps.database.components;

import com.sebbaindustries.warps.utils.Replace;
import org.jetbrains.annotations.NotNull;

public class QueryLogic {

    private String query;

    public QueryLogic(final @NotNull String query) {
        this.query = query;
    }

    public String query() {
        return Replace.removeLastChar(query) + ";";
    }

    public QueryLogic where(final @NotNull String argument) {
        // q:SELECT attribute_name FROM table_name WHERE argument...
        query = query + "WHERE " + argument + " ";
        return this;
    }

    public QueryLogic whereNot(final @NotNull String argument) {
        // q:SELECT attribute_name FROM table_name WHERE NOT argument...
        query = query + "WHERE NOT " + argument + " ";
        return this;
    }

    public QueryLogic and() {
        query = query + "AND ";
        return this;
    }

    public QueryLogic or() {
        query = query + "OR ";
        return this;
    }

    public QueryLogic innerJoin(final @NotNull String join, final @NotNull String joinCondition) {
        query = query + "INNER JOIN " + join + " ON " + joinCondition + " ";
        return this;
    }

    public QueryLogic leftJoin(final @NotNull String join, final @NotNull String joinCondition) {
        query = query + "LEFT JOIN " + join + " ON " + joinCondition + " ";
        return this;
    }

    public QueryLogic rightJoin(final @NotNull String join, final @NotNull String joinCondition) {
        query = query + "RIGHT JOIN " + join + " ON " + joinCondition + " ";
        return this;
    }

    public QueryLogic fullJoin(final @NotNull String join, final @NotNull String joinCondition) {
        query = query + "FULL OUTER JOIN " + join + " ON " + joinCondition + " ";
        return this;
    }

    public QueryLogic orderASC(final @NotNull String column) {
        query = query + "ORDER BY " + column + " ASC ";
        return this;
    }

    public QueryLogic orderDESC(final @NotNull String column) {
        query = query + "ORDER BY " + column + " DESC ";
        return this;
    }
}
