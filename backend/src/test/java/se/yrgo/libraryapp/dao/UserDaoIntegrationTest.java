package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.radcortez.flyway.test.annotation.H2;
import se.yrgo.libraryapp.entities.LoginInfo;
import se.yrgo.libraryapp.entities.User;
import se.yrgo.libraryapp.entities.UserId;

@Tag("integration")
@H2
public class UserDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        UserDaoIntegrationTest.ds = ds;
    }

    @Test
    void getExistingUserById() {
        // this data comes from the test migration files
        final String username = "test";
        final UserId userId = UserId.of(1);
        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getName()).isEqualTo(username);
        assertThat(maybeUser.get().getId()).isEqualTo(userId);
    }

    @Test
    void getNonExistingUserById() {
        // this data comes from the test migration files
        final String username = "non-existing";
        final UserId userId = UserId.of(3);
        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));
        assertThat(maybeUser).isEmpty();
    }

    @Test
    void getExistingLoginInfo() {
        final String username = "test";
        final UserId id = UserId.of(1);
        final String passwordHash =
                "$argon2i$v=19$m=16,t=2,p=1$MTIzNDU2Nzg5MDEyMzQ1NjA$LmFqTZeUWwqsnbZCS2E8XQ";

        UserDao userDao = new UserDao(ds);
        LoginInfo info = userDao.getLoginInfo(username).get();
        assertThat(info.getUserId()).isEqualTo(id);
        assertThat(info.getPasswordHash()).isEqualTo(passwordHash);
    }

    @Test
    void getNonExistingLoginInfo() {
        final String username = "non-existing";
        final UserId id = UserId.of(1);
        final String passwordHash = "incorrect";

        UserDao userDao = new UserDao(ds);
        assertThat(userDao.get(username)).isEmpty();
    }

    @Test
    void getExistingLoginInfoWithWrongPassword() {
        final String username = "test";
        final UserId id = UserId.of(1);
        final String passwordHash = "wrong";

        UserDao userDao = new UserDao(ds);
        LoginInfo info = userDao.getLoginInfo(username).get();
        assertThat(info.getUserId()).isEqualTo(id);
        assertThat(info.getPasswordHash()).isNotEqualTo(passwordHash);
    }

    @Test
    void testSuccessFullyRegisterUser() {
        final String username = "newtestuser";
        final String realname = "newrealnametest";
        final String password = "newpassword";

        UserDao userDao = new UserDao(ds);
        assertThat(userDao.register(username, realname, password)).isTrue();
    }

    @Test
    void testUnsuccessFullyRegisterUserWithSameUsername() {
        final String username = "newtestuser";
        final String realname = "newrealnametest";
        final String password = "newpassword";

        UserDao userDao = new UserDao(ds);
        userDao.register(username, realname, password);
        assertThat(userDao.register(username, realname, password)).isFalse();
    }

    @Test
    void testNameThatShouldBeAvailable() {
        final String username = "available";

        UserDao userDao = new UserDao(ds);

        assertThat(userDao.isNameAvailable(username)).isTrue();
    }

    @Test
    void testNameThatShouldNotBeAvailable() {
        final String username = "test";

        UserDao userDao = new UserDao(ds);

        assertThat(userDao.isNameAvailable(username)).isFalse();
    }
}