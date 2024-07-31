package json;

import java.util.List;

public class CreateOrderRequestCard {
    private List<String> ingredients;

    public CreateOrderRequestCard(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void addIngredients(String newIngredients) {
        this.ingredients.add(newIngredients);
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void clear(){
        ingredients.clear();
    }
}
