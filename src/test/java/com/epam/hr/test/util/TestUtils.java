package com.epam.hr.test.util;

import java.util.Arrays;

public class TestUtils {

    public static String fillString(int size, char ch) {
        char[] chars = new char[size];
        Arrays.fill(chars, ch);
        return new String(chars);
    }
}
