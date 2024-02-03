package com.example.utils;

import java.util.Random;

public class RandomUtil {
    public static String getRandomSmsCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
        // to generate 6-digit number
    }
}
