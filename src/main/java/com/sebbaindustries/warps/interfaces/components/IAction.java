package com.sebbaindustries.warps.interfaces.components;

public enum IAction {

    WARP("warps"),
    PAGE_NEXT("next-page"),
    PAGE_PREV("previous-page"),
    NONE("none"),
    BACKGROUND("background")
    ;

    public String action;

    IAction(String action) {
        this.action = action;
    }
}
