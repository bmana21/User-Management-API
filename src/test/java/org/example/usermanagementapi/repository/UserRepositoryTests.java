package org.example.usermanagementapi.repository;

import org.example.usermanagementapi.model.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void test_should_save_and_fetch_user() {
        User newUser = new User("beso123", "password", "123", "Beso", "Managadze");
        userRepository.save(newUser);

        User foundUser = userRepository.findByUsername("beso123");

        assertEquals(newUser, foundUser);
    }

    @Test
    public void test_should_find_token() {
        User newUser = new User("beso123", "password", "123", "Beso", "Managadze");
        userRepository.save(newUser);

        User foundUser = userRepository.findByToken("123");

        assertEquals(foundUser, newUser);
    }

    @Test
    public void test_should_not_find_token() {
        User foundUser = userRepository.findByToken("111");
        assertNull(foundUser);
    }

    @Test
    public void test_should_find_by_user_id() {
        User newUser = new User("beso123", "password", "123", "Beso", "Managadze");
        userRepository.save(newUser);

        long user_id = newUser.getUserId();

        User foundUser = userRepository.findByUserId(user_id);

        assertEquals(foundUser, newUser);
    }

    @Test
    public void test_should_not_find_by_user_id() {
        User foundUser = userRepository.findByUserId(222);
        assertNull(foundUser);
    }


}
