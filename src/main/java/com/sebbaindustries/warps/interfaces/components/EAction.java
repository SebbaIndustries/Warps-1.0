package com.sebbaindustries.warps.interfaces.components;

public enum EAction {

    WARP("warps"),
    PAGE_NEXT("next-page"),
    PAGE_PREV("previous-page"),
    NONE("none"),
    BACKGROUND("background")
    ;

    public String action;

    EAction(String action) {
        this.action = action;
    }
}
