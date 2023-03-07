package practicum.model;

public class Order {
    private String[] ingredients;
    private String _id;
    private String status;
    private String number;

    public Order() {
    }

    public Order(Ingredient ingredient) {
        this.ingredients = new String[] {ingredient.get_id()};
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
