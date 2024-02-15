package org.example.usermanagementapi.util;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final int TOKEN_LENGTH = 32; // Length of the token in bytes

    public static String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static void main(String[] args) {
        String token = generateToken();
        System.out.println("Generated Token: " + token);
    }
}