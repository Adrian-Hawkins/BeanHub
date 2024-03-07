package com.bean.api.entitities;

import com.bean.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @InjectMocks
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserId() {
        user.setUserId(1);
        assertEquals(1, user.getUserId());
    }

    @Test
    public void testUsername() {
        user.setUsername("test_user");
        assertEquals("test_user", user.getUsername());
    }
}
