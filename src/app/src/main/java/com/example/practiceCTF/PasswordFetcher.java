package com.example.practiceCTF;

import java.util.Random;

public class PasswordFetcher {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMN01234567890";

    private Random random;
    private final int originKey;
    private int cycleCount;

    public PasswordFetcher(int key) {
        random = new Random(key);
        cycleCount = 0;
        originKey = key;
    }

    public String getPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
            sb.append(randomChar);
        }
        cycleCount++;
        if (cycleCount == 5) {
            random = new Random(originKey);
            cycleCount = 0;
        }
        return sb.toString();
    }
}