package org.example.usermanagementapi.util;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PasswordHashingTests extends TestCase {
    public void test_hash_is_correct() {
        String passwordHash = PasswordHashing.hashPassword("password");
        assertEquals(passwordHash, "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8");
    }

    public void test_hash_is_incorrect() {
        String passwordHash = PasswordHashing.hashPassword("password");
        assertNotEquals(passwordHash, "6edd8a12b3ab0b7567779b4b33f940dd91c75134");
    }

    public void test_empty_password() {
        String passwordHash = PasswordHashing.hashPassword("");
        assertNotEquals(passwordHash, null);
    }


}
