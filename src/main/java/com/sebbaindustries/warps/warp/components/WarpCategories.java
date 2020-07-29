package com.sebbaindustries.warps.warp.components;

import java.util.ArrayList;
import java.util.List;

public class WarpCategories {

    private final List<String> categories = new ArrayList<>();

    public final boolean addCategory(String category) {
        if (categories.contains(category)) return false;
        categories.add(category);
        return true;
    }

    public final boolean removeCategory(String category) {
        if (categories.contains(category)) {
            categories.remove(category);
            return true;
        }
        return false;
    }

    public final void clear() {
        categories.clear();
    }

    public final List<String> getCategories() {
        return categories;
    }

}
