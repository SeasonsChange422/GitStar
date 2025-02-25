package com.hx.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author dhx
 * @date 2025/1/8 18:27
 */
public class RandomUtil {
    private static final String CHARACTERS = "0123456789";

    public static String generateCode(int len) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    public static Long generateUUID() {
        return Long.valueOf(String.valueOf(UUID.randomUUID()));
    }

    public static Long getRandId() {
        return Long.parseLong(generateCode(9));
    }
}
