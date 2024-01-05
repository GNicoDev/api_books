package com.books.books.entity;

public enum Genre {
    DRAMA, THRILLER, ADVENTURE, SELFHELP;
    public static boolean isEnumValue(String value) {
        try {
            Genre.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static Genre convertValue(String value) {
        if (Genre.isEnumValue(value))
            return Genre.valueOf(value.toUpperCase());
        return null;
    }
}


