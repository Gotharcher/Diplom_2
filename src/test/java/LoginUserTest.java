import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.SiteAddress;
import practicum.model.UserResponse;
import practicum.model.User;
import requests.UserRequest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginUserTest {
    User user;
    UserResponse createdModel;

    @Before
    public void setUp() {
        RestAssured.baseURI = SiteAddress.SITE_ADDRESS;
        user = User.createRandomUser();
        createdModel = UserRequest.createUser(user).body().as(UserResponse.class);
    }

    @Test
    public void checkCorrectLogin() {
        Response response = UserRequest.authUser(user);
        assertTrue("В ответе сообщение об успехе", response.path("success"));
    }

    @Test
    public void checkIncorrectLogin() {
        user.setEmail(RandomStringUtils.randomAlphabetic(10)+"mail.com");
        Response response = UserRequest.authUser(user);
        assertFalse("В ответе сообщение о неудаче", response.path("success"));
    }

    @Test
    public void checkIncorrectPassword() {
        user.setPassword("87654321");
        Response response = UserRequest.authUser(user);
        assertFalse("В ответе сообщение о неудаче", response.path("success"));
    }

    @After
    public void tearDown() {
        UserRequest.deleteUser(createdModel.getAccessToken());
    }
}
