package requests;

import io.restassured.response.Response;
import practicum.SiteAddress;
import practicum.model.User;

import static io.restassured.RestAssured.given;

public class OrderRequest {

    public static Response getUserOrders(String authToken){
        return given()
                .header("Authorization", authToken)
                .get(SiteAddress.ORDERS_USER);
    }

}
