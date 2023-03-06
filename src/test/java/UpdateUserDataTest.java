import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.SiteAddress;
import practicum.model.UserResponse;
import practicum.model.User;
import requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class UpdateUserDataTest {
    User user;
    UserResponse resModel;

    @Before
    public void setUp() {
        UserRequest.init();
        user = User.createRandomUser();
        resModel = UserRequest.createUser(user).body().as(UserResponse.class);
    }

    @Test
    public void checkAuthorizedFieldsChange() {
        User newUserData = User.createRandomUser();
        newUserData.setPassword("0987654321");
        Response response = UserRequest.updateUserData(newUserData, resModel.getAccessToken());
        assertTrue("В ответе сообщение об успехе", response.path("success"));
        assertTrue("С новыми данными можно залогиниться", UserRequest.authUser(newUserData).path("success"));
        assertFalse("Старые данные больше не позволяют логиниться", UserRequest.authUser(user).path("success"));
    }

    @Test
    public void checkUnauthorizedFieldsChange() {
        User newUserData = User.createRandomUser();
        newUserData.setPassword("0987654321");
        Response response = given().header("Content-Type", "application/json").body(user).patch(SiteAddress.USER_API);
        assertFalse("В ответе сообщение о неудаче", response.path("success"));
        assertEquals("Код неавторизованного запроса", 401, response.statusCode());
    }

    @After
    public void tearDown() {
        UserRequest.deleteUser(resModel.getAccessToken());
    }
}
