package com.musinsa.bkjeon.feature.utils;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String isLongToformatCurrency(Long price) {
        return String.format("%,d", price);
    }

}
