package dev.hour.presenter;

import java.util.ArrayList;
import java.util.List;

import dev.hour.contracts.MealContract;

public class MealPresenter implements MealContract.Meal.Presenter {

    /// ---------------
    /// Private Members

    private MealContract.Meal.Database database ;
    private MealContract.Meal.View     view     ;
    private MealContract.Meal          meal     ;

    /**
     * Sets the [MealContract.Meal.Database] instance
     * @param database The [MealContract.Meal.Database] instance
     */
    @Override
    public void setDatabase(final MealContract.Meal.Database database) {

        this.database = database;

    }

    /**
     * Sets the [MealContract.Meal.View] instance.
     * @param view The [MealContract.Meal.View] instance.
     */
    @Override
    public void setView(final MealContract.Meal.View view) {

        this.view = view;

        if(this.view != null)
            this.view.setMeal(this.meal);

    }

    /**
     * Sets the Meal. This updates the presenter's view, if any is bound.
     * @param id The [String] id corresponding to the Meal
     */
    @Override
    public void setMealFrom(final String id) {

        if(this.database != null) {

            this.meal = this.database.getMeal(id);

            if(this.view != null)
                this.view.setMeal(this.meal);

        }

    }

    /**
     * Retrieves a set of MealContract.Meals from the specified set of ids
     * @param mealIds The [String] ids corresponding to the Meals
     */
    @Override
    public List<MealContract.Meal> getMealsFrom(List<String> mealIds) {

        List<MealContract.Meal> result = new ArrayList<>();

        if(this.database != null)
            result = this.database.getMealsFrom(mealIds);

        return result;

    }

}
