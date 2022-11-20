package dev.hour.model;

import java.util.List;

import dev.hour.contracts.MealContract;

public class Diet implements MealContract.Diet {

    /// --------------
    /// Private Fields

    private String                  id          ;
    private List<String>            allergens   ;
    private List<MealContract.Meal> meals       ;

    public Diet() {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public List<String> getAllergens() {
        return allergens;
    }

    @Override
    public List<MealContract.Meal> getMeals() {
        return meals;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setAllergens(List<String> allergens) {

    }

    @Override
    public void setMeals(List<MealContract.Meal> meals) {

    }

}
