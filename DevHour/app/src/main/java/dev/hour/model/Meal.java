package dev.hour.model;

import java.io.OutputStream;
import java.time.chrono.MinguoDate;
import java.util.List;
import java.util.Map;

import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.MealContract;

public class Meal implements MealContract.Meal {

    /// --------------
    /// Private Fields

    private String              id          ;
    private String              name        ;
    private int                 calories    ;
    private Map<String, String> ingredients ;
    private OutputStream        imageStream ;
    private List<String>        tags        ;

    /// -----------
    /// Constructor

    public Meal() { /* Empty */ }

    /// -----------------
    /// MealContract.Meal

    @Override
    public String getId() {

        return this.id;

    }

    @Override
    public String getName() {

        return name;

    }

    @Override
    public int getCalories() {

        return calories;

    }

    @Override
    public Map<String, String> getIngredients() {

        return ingredients;

    }

    @Override
    public OutputStream getImageStream() {

        return this.imageStream;

    }

    @Override
    public void setId(String id) {

        this.id = id;

    }

    @Override
    public void setName(String name) {

        this.name = name;

    }

    @Override
    public void setCalories(int calories) {

        this.calories = calories;

    }

    @Override
    public void setIngredients(Map<String, String> ingredients) {

        this.ingredients = ingredients;

    }

    @Override
    public void setImageStream(final OutputStream imageStream) {

        this.imageStream = imageStream;

    }

    @Override
    public void setTags(final List<String> tags) {

        this.tags = tags;

    }

    @Override
    public List<String> getTags() {

        return this.tags;

    }

}

