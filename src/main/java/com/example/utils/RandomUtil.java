package com.example.utils;

import java.util.Random;

public class RandomUtil {
    public static String getRandomSmsCode() {
        Random random = new Random();
        String parole = "123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(parole.length());
            password.append(parole.charAt(index));
        }
        return password.toString();
    }
}
