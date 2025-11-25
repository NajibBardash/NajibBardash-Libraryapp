package se.yrgo.libraryapp.services;

import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.yrgo.libraryapp.dao.UserDao;
import se.yrgo.libraryapp.entities.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Test
    @SuppressWarnings("deprecation")
    void correctLogin() {
        final String userId = "1";
        final UserId id = UserId.of(userId);
        final String username = "testuser";
        final String password = "password";
        final String passwordHash = "password";
        final LoginInfo info = new LoginInfo(id, passwordHash);
        final PasswordEncoder encoder = org.springframework.security
                .crypto.password.NoOpPasswordEncoder.getInstance();

        when(userDao.getLoginInfo(username)).thenReturn(Optional.of(info));

        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.validate(username,
                password)).isEqualTo(Optional.of(id));
    }

    @Test
    @SuppressWarnings("deprecation")
    void successfullRegister() {
        final String username = "testuser";
        final String realname = "testrealname";
        final String password = "password";
        final PasswordEncoder encoder = org.springframework.security
                .crypto.password.NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        when(userDao.register(username, realname, password)).thenReturn(true);

        assertThat(userService.register(username, realname, password)).isTrue();
    }

    @Test
    @SuppressWarnings("deprecation")
    void unsuccessfullRegisterDueToInvalidUsername() {
        final String username = "***";
        final String realname = "testrealname";
        final String password = "password";
        final PasswordEncoder encoder = org.springframework.security
                .crypto.password.NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        assertThat(userService.register(username, realname, password)).isFalse();
    }

    @Test
    @SuppressWarnings("deprecation")
    void testWhenNameIsAvailable() {
        final String username = "testuser";
        final PasswordEncoder encoder = org.springframework.security
                .crypto.password.NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        when(userDao.isNameAvailable(username)).thenReturn(true);

        assertThat(userService.isNameAvailable(username)).isTrue();
    }

    @Test
    @SuppressWarnings("deprecation")
    void testWhenNameIsUnavailable() {
        final String username = "testuser";
        final PasswordEncoder encoder = org.springframework.security
                .crypto.password.NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        when(userDao.isNameAvailable(username)).thenReturn(false);

        assertThat(userService.isNameAvailable(username)).isFalse();
    }
}