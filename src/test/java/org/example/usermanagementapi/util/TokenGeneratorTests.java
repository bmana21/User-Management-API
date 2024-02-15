package org.example.usermanagementapi.util;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenGeneratorTests extends TestCase {
    public void test_token_length() {
        String token = TokenGenerator.generateToken();
        assertEquals(token.length(), TokenGenerator.TOKEN_LENGTH);
    }

    public void test_tokens_are_different() {
        String token1 = TokenGenerator.generateToken();
        String token2 = TokenGenerator.generateToken();
        String token3 = TokenGenerator.generateToken();
        assertNotEquals(token1, token2);
        assertNotEquals(token2, token3);
        assertNotEquals(token1, token3);
    }
}
