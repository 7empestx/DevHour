package dev.hour.model;

import java.util.List;

import dev.hour.contracts.MealContract;

public class Menu implements MealContract.Menu {

    /// ---------------
    /// Private Members

    private String                  id      ;
    private List<MealContract.Meal> meals   ;

    /// -----------------
    /// MealContract.Meal

    @Override
    public String getId() {

        return this.id;

    }

    @Override
    public List<MealContract.Meal> getMeals() {

        return this.meals;

    }

    @Override
    public void setId(String id) {

        this.id = id;

    }

    @Override
    public void setMeals(List<MealContract.Meal> meals) {

        this.meals = meals;

    }

}
