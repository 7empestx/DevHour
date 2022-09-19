package dev.hour.contracts;

import java.util.List;

public interface MealContract {

    interface Meal {

        int getCalories();
        String getName();
        List<Diet> getDiets();
        List<Ingredient> getIngredients();

    }

    enum Diet {

    }

    enum Ingredient {

    }

}
