package dev.hour.model;

import java.util.ArrayList;
import java.util.List;

import dev.hour.contracts.MealContract;

public class Menu implements MealContract.Menu {

    /// ---------------
    /// Private Members

    private String                  id      ;
    private List<MealContract.Meal> meals   ;
    private List<String>            mealIds ;
    private List<String>            tags    ;

    /// -----------------
    /// MealContract.Meal

    @Override
    public String getId() {

        return this.id;

    }

    @Override
    public List<String> getMealIds() {

        return this.mealIds;

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
    public void setMealIds(final List<String> mealIds) {

        this.mealIds = mealIds;

    }

    @Override
    public void setMeals(List<MealContract.Meal> meals) {

        this.meals = meals;

    }

    @Override
    public void setTags(final List<String> tags) {

        this.tags = tags;

    }

    @Override
    public void addMealID(final String mealID) {

        if(this.mealIds == null) {
            this.mealIds = new ArrayList<>();
        }
        this.mealIds.add(mealID);
    }

}
