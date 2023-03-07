package requests;

import practicum.SiteAddress;
import practicum.model.Ingredient;
import practicum.model.IngredientResponse;

import static io.restassured.RestAssured.given;

public class IngredientRequest {

    public static Ingredient[] getIngredientsArray(){
        return getIngredientResponse().getIngredients();
    }

    public static IngredientResponse getIngredientResponse(){
        return given().get(SiteAddress.INGREDIENTS).as(IngredientResponse.class);
    }

    public static Ingredient getFirstIngredientFromArray(){
        return getIngredientsArray()[0];
    }
}
