package se.yrgo.libraryapp.controllers;

import io.jooby.Context;
import io.jooby.MockContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import se.yrgo.libraryapp.dao.RoleDao;
import se.yrgo.libraryapp.dao.SessionDao;
import se.yrgo.libraryapp.entities.Role;
import se.yrgo.libraryapp.entities.UserId;
import se.yrgo.libraryapp.entities.forms.LoginData;
import se.yrgo.libraryapp.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleDao roleDao;

    @Mock
    private SessionDao sessionDao;

    @Test
    void testSuccesfulLogin() {
        UserId userId = UserId.of(1);
        String username = "test";
        String password = "yrgoP4ssword";

        Context context = mock(Context.class);
        String sessionCookie = "sessionCookie";

        LoginData loginData = new LoginData(username, password);

        LoginController loginController = new LoginController(userService, roleDao, sessionDao);

        when(userService.validate(username, password)).thenReturn(Optional.of(userId));
        when(roleDao.get(userId)).thenReturn(List.of(Role.USER));
        when(sessionDao.create(userId)).thenReturn(UUID.randomUUID());

        assertThat(loginController.login(context, sessionCookie, loginData)).isEqualTo(List.of(Role.USER));
    }

    @Test
    void testWhenUserNotFound() {
        UserId userId = UserId.of(1);
        String username = "non-existent";
        String password = "non-existent";

        Context context = mock(Context.class);
        String sessionCookie = "sessionCookie";

        LoginData loginData = new LoginData(username, password);

        LoginController loginController = new LoginController(userService, roleDao, sessionDao);

        when(userService.validate(username, password)).thenReturn(Optional.empty());

        assertThat(loginController.login(context, sessionCookie, loginData)).isEqualTo(List.of());
    }
}