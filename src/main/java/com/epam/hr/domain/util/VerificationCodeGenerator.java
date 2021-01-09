package com.epam.hr.domain.util;

import java.util.Random;

public class VerificationCodeGenerator {
    private static final String CODE_ALLOWED_CHARACTERS = "0123456789@.,)(*%&$abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 6;

    public String generate() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CODE_ALLOWED_CHARACTERS.length());
            code.append(CODE_ALLOWED_CHARACTERS.charAt(index));
        }

        return code.toString();
    }

}
