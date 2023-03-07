package requests;

import practicum.SiteAddress;
import practicum.model.Ingredient;
import practicum.model.IngredientResponse;

import static io.restassured.RestAssured.given;

public class IngredientRequest {

    public Ingredient[] getIngredientsArray(){
        return getIngredientResponse().getIngredients();
    }

    public IngredientResponse getIngredientResponse(){
        return given().get(SiteAddress.INGREDIENTS).as(IngredientResponse.class);
    }
}
