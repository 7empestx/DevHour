package dev.hour.model;

import java.io.OutputStream;
import java.time.chrono.MinguoDate;
import java.util.Map;

import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.MealContract;

public class Meal implements MealContract.Meal {
    /// --------------
    /// Private Fields

    private int calories;
    private String name;
    private Map<String, MealContract.Ingredient> ingredients;
    private OutputStream imageStream;

    public Meal(final String name, final int calories, final Map<String, MealContract.Ingredient> ingredients) {

        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;

    }
    //getter
    @Override
    public OutputStream getImageStream() {

        return this.imageStream;

    }

    //setter
    @Override
    public int getCalories() { return calories; }

    @Override
    public String getName() { return name; }

    @Override
    public Map<String, MealContract.Ingredient> getIngredients() { return ingredients; }

    @Override
    public void setImageStream(final OutputStream imageStream) {

        this.imageStream = imageStream;

    }
}

