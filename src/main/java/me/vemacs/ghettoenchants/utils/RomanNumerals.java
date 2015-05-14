package me.vemacs.ghettoenchants.utils;

import com.google.common.collect.ImmutableBiMap;

public class RomanNumerals {
    private static final com.google.common.collect.BiMap<String, Integer> ROMAN_INT_MAP = new ImmutableBiMap.Builder<String, Integer>()
            .put("i", 1)
            .put("ii", 2)
            .put("iii", 3)
            .put("iv", 4)
            .put("v", 5)
            .put("vi", 6)
            .put("vii", 7)
            .put("viii", 8)
            .put("ix", 9)
            .put("x", 10)
            .build();

    public static int intFromRomanString(String roman) {
        return ROMAN_INT_MAP.get(roman.toLowerCase());
    }

    public static String romanStringFromInt(int integer) {
        return ROMAN_INT_MAP.inverse().get(integer).toUpperCase();
    }
}
