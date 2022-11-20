package dev.hour.model;

import java.time.chrono.MinguoDate;
import java.util.Map;

import dev.hour.contracts.MealContract;

public class Meal implements MealContract.Meal {
    /// --------------
    /// Private Fields

    private int calories;
    private String name;
    private Map<String, MealContract.Ingredient> ingredients;

    public Meal(final String name, final int calories, final Map<String, MealContract.Ingredient> ingredients) {

        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;

    }

    @Override
    public int getCalories() { return calories; }

    @Override
    public String getName() { return name; }

    @Override
    public Map<String, MealContract.Ingredient> getIngredients() { return ingredients; }
}
