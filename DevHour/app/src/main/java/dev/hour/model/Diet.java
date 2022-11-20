package dev.hour.model;

import java.util.ArrayList;
import java.util.List;

import dev.hour.contracts.MealContract;

public class Diet implements MealContract.Diet {

    /// --------------
    /// Private Fields

    private String id;
    private List<MealContract.Ingredient> allergens;
    private List<MealContract.Diets> diets;

    public Diet(List<String> allergens, List<String> diets) {
        // TODO: Set allergens after deciding on enum values for ingredients
        this.diets = new ArrayList<MealContract.Diets>();
        for (String diet : diets) {
            this.diets.add(MealContract.Diets.valueOf(diet));
        }
    }

    @Override
    public List<MealContract.Ingredient> getAllergens() {
        return allergens;
    }

    @Override
    public List<MealContract.Diets> getDiets() {
        return diets;
    }
}
