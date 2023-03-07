package requests;

import io.restassured.response.Response;
import practicum.SiteAddress;
import practicum.model.Order;
import practicum.model.User;

import static io.restassured.RestAssured.given;

public class OrderRequest {

    public static Response getUserOrders(String authToken){
        return given()
                .header("Authorization", authToken)
                .get(SiteAddress.ORDERS_USER);
    }

    public static Response createOrder(Order order, String authToken){
        return given()
                .header("Authorization", authToken)
                .header("Content-Type", "application/json")
                .body(order)
                .post(SiteAddress.ORDERS_USER);
    }

}
