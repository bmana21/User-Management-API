package org.example.usermanagementapi.util;

import java.security.SecureRandom;

public class TokenGenerator {
    public static final int TOKEN_LENGTH = 32; // Length of the token in bytes

    public static String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH / 2];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return bytesToHex(randomBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}