package dev.hour.model;

import java.util.List;

import dev.hour.contracts.MealContract;

public class Diet implements MealContract.Diet {

    /// --------------
    /// Private Fields

    private String                  id          ;
    private List<String>            allergens   ;
    private List<String>            ingredients ;
    private List<String>            diets       ;
    private List<MealContract.Meal> meals       ;
    private List<String>            mealIds     ;

    public Diet() {

    }

    @Override
    public String getId() {

        return this.id;

    }

    @Override
    public List<String> getAllergens() {

        return this.allergens;

    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public List<String> getDiets() {
        return diets;
    }

    @Override
    public List<MealContract.Meal> getMeals() {

        return this.meals;

    }

    @Override
    public List<String> getMealIds() {

        return this.mealIds;

    }

    @Override
    public void setId(final String id) {

        this.id = id;

    }

    @Override
    public void setAllergens(final List<String> allergens) {

        this.allergens = allergens;

    }

    @Override
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    @Override
    public void setMeals(final List<MealContract.Meal> meals) {

        this.meals = meals;

    }

    @Override
    public void setMealIds(final List<String> mealIds) {

        this.mealIds = mealIds;

    }

}
