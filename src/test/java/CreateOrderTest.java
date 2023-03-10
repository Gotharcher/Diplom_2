import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.SiteAddress;
import practicum.model.Ingredient;
import practicum.model.Order;
import practicum.model.User;
import requests.OrderRequest;
import requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static requests.IngredientRequest.getFirstIngredientFromArray;

public class CreateOrderTest {

    String authToken;
    Ingredient validIngredient;

    @Before
    public void setUp() {
        RestAssured.baseURI = SiteAddress.SITE_ADDRESS;
        authToken = UserRequest.createUser(User.createRandomUser()).path("accessToken");
        validIngredient = getFirstIngredientFromArray();
    }

    public String[] getIngredientsArrayFromIngredient(Ingredient validIngredient) {
        return new String[]{validIngredient.get_id()};
    }

    @Test
    public void createOrderWithAuth() {
        Order order = new Order(validIngredient);
        Response response = OrderRequest.createOrder(order, authToken);
        assertTrue("Получили сообщение об успехе", response.path("success"));
    }

    @Test
    public void createOrderWithoutAuth() {
        Order order = new Order(validIngredient);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(order)
                .post(SiteAddress.ORDERS_USER);
        assertTrue("Получили сообщение об успехе", response.path("success"));
    }

    @Test
    public void createOrderWithAuthWithoutIngredients() {
        Order order = new Order();
        Response response = OrderRequest.createOrder(order, authToken);
        assertFalse("Получили сообщение о неудаче", response.path("success"));
        assertEquals("Код ответа - Bad Request", 400, response.statusCode());
        assertEquals("Сообщение требует ингридиенты", "Ingredient ids must be provided", response.path("message"));
    }

    @Test
    public void createOrderWithAuthIncorrectHash() {
        validIngredient.set_id("ПриветЯИнгридиент!");
        Order order = new Order(validIngredient);
        Response response = OrderRequest.createOrder(order, authToken);
        assertEquals("Код ответа - Internal Error", 500, response.statusCode());
    }

    @Test
    public void createOrderWithAuthChangedHash() {
        validIngredient.set_id(validIngredient.get_id().replace("a", "0"));
        Order order = new Order(validIngredient);
        Response response = OrderRequest.createOrder(order, authToken);
        assertFalse("Получили сообщение о неудаче", response.path("success"));
        assertEquals("Код ответа - Bad Request", 400, response.statusCode());
        assertEquals("Сообщение требует ингридиенты", "One or more ids provided are incorrect", response.path("message"));
    }

    @After
    public void tearDown() {
        UserRequest.deleteUser(authToken);
    }
}
