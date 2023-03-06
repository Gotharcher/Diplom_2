import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.SiteAddress;
import practicum.model.OrderResponse;
import practicum.model.User;
import practicum.model.UserResponse;
import requests.OrderRequest;
import requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class UsersOrdersListTest {
    User user;
    UserResponse createdModel;

    @Before
    public void setUp() {
        UserRequest.init();
        user = User.createRandomUser();
        createdModel = UserRequest.createUser(user).body().as(UserResponse.class);
    }

    @Test
    public void checkAuthorizedOrdersRequest() {
        Response response = OrderRequest.getUserOrders(createdModel.getAccessToken());
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertTrue("В ответе сообщение об успехе", response.path("success"));
        assertNotNull("В ответе есть список заказов", orderResponse.getOrders());
    }

    @Test
    public void checkUnauthorizedOrdersRequest() {
        Response response = given()
                .get(SiteAddress.ORDERS_USER);
        assertFalse("В ответе сообщение о неудаче", response.path("success"));
        assertEquals("Код неавторизованного запроса", 401, response.statusCode());
    }

    @After
    public void tearDown() {
        UserRequest.deleteUser(createdModel.getAccessToken());
    }
}
