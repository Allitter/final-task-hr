package com.epam.hr.domain.util;

import java.util.Random;

public class VerificationCodeGenerator {
    private static final String CODE_ALLOWED_CHARACTERS = "0123456789@.,)(*%&$abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();
    private static final int LENGTH = 6;

    public static String generate() {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(CODE_ALLOWED_CHARACTERS.length());
            code.append(CODE_ALLOWED_CHARACTERS.charAt(index));
        }

        return code.toString();
    }

}
