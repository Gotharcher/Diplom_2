import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;

import static io.restassured.RestAssured.given;

public class UserRequest {
    public static void init(){
        RestAssured.baseURI = SiteAddress.SITE_ADDRESS;
    }

    public static Response createUser(User user){
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(SiteAddress.USER_CREATE);
    }

    public static Response authUser(User user){
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(SiteAddress.AUTH_LOGIN);
    }

    public static Response deleteUser(String authToken){
        String URIaddress = SiteAddress.USER_API;
        return given()
                .header("Authorization", authToken)
                .delete(URIaddress);
    }
}
