package com.sebbaindustries.warps.utils;

public class EnumCheck {

    public static <T extends Enum<T>> boolean isValid(final Class<T> inputEnum, final String input) {
        for (T val : inputEnum.getEnumConstants()) {
            if (val.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
