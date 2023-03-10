package practicum.model;

public class IngredientResponse {
    private boolean success;
    private Ingredient[] data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Ingredient[] getIngredients() {
        return data;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.data = ingredients;
    }
}
