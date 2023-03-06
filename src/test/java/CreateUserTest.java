import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import practicum.model.ResponseModel;
import practicum.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.UserRequest;

import static org.junit.Assert.*;

public class CreateUserTest {

    User user;
    ResponseModel resModel;

    @Before
    public void setUp() {
        UserRequest.init();
        user = User.createRandomUser();
    }

    @Test
    @Description("Create User with random login, password and name")
    public void createNewUser() {
        Response response = createUser(user);
        assertEquals("Положительный код ответа", 200, response.statusCode());
        resModel = response.body().as(ResponseModel.class);
        assertTrue("В ответе сообщение об успехе", resModel.isSuccess());
    }

    @Test
    @Description("Create User with existing login")
    public void createSameUser() {
        Response response = createUser(user);
        assertEquals("Положительный код ответа", 200, response.statusCode());
        resModel = response.body().as(ResponseModel.class);
        response = createUser(user);
        ResponseModel failResMod = response.body().as(ResponseModel.class);
        assertEquals("Негативный код ответа", 403, response.statusCode());
        assertFalse("В ответе сообщение о неудаче", failResMod.isSuccess());
        assertEquals("Текст сообщения информирует о причине - логин занят", "User already exists", failResMod.getMessage());
    }

    @Test
    public void createInvalidUserEmail() {
        user.setEmail("");
        checkCreationFailureDueNoField();
    }

    @Test
    public void createInvalidUserPassword() {
        user.setPassword("");
        checkCreationFailureDueNoField();
    }

    @Test
    public void createInvalidUserName() {
        user.setName("");
        checkCreationFailureDueNoField();
    }

    public void checkCreationFailureDueNoField() {
        Response response = createUser(user);
        resModel = response.body().as(ResponseModel.class);
        assertEquals("Негативный код ответа", 403, response.statusCode());
        assertFalse("В ответе сообщение о неудаче", resModel.isSuccess());
        assertEquals("Текст сообщения информирует о причине - не заполнено обязательное поле", "Email, password and name are required fields", resModel.getMessage());

    }

    @Step("Sending data to create new User")
    public Response createUser(User user) {
        return UserRequest.createUser(user);
    }

    @After
    public void tearDown() {
        if (resModel.getAccessToken() == null) {
            return;
        }
        UserRequest.deleteUser(resModel.getAccessToken());
    }
}
