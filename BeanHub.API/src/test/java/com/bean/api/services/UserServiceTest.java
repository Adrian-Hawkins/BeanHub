package com.bean.api.services;

import com.bean.api.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<User> query;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("testuser");

        userService.saveUser(user);

        verify(entityManager, times(1)).persist(user);
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");

        userService.updateUser(user);

        verify(entityManager, times(1)).merge(user);
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");

        when(entityManager.contains(user)).thenReturn(true);

        userService.deleteUser(user);

        verify(entityManager, times(1)).remove(user);
    }

    @Test
    void getUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUserId(1);
        user.setUsername(username);

        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        User result = userService.getUserByUsername(username);

        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setUserId(1);
        user1.setUsername("testuser1");

        User user2 = new User();
        user2.setUserId(2);
        user2.setUsername("testuser2");

        List<User> users = Arrays.asList(user1, user2);

        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(users));
    }
}