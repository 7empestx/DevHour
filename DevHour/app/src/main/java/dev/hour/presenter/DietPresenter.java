package dev.hour.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.hour.contracts.MealContract;

public class DietPresenter implements MealContract.Diet.Presenter, MealContract.Diet.View.Listener {

    /// ---------------
    /// Private Members

    private MealContract.Diet.Database  database    ;
    private MealContract.Diet.View      view        ;
    private List<String>                mealIds     ;
    private List<MealContract.Meal>     meals       ;

    /// ---------------------------
    /// MealContract.Diet.Presenter

    /**
     * Sets the [MealContract.Diet.Database] instance
     * @param database The [MealContract.Diet.Database] instance
     */
    @Override
    public void setDatabase(final MealContract.Diet.Database database) {

        this.database = database;

    }

    /**
     * Sets the [MealContract.Diet.View] instance.
     * @param view The [MealContract.Diet.View] instance.
     */
    @Override
    public void setView(final MealContract.Diet.View view) {

        this.view = view;

    }

    /**
     * Invokes the [MealContract.Diet.Database] to create a Diet with the given data.
     * @param data The Diet data.
     */
    @Override
    public void updateDiet(final Map<String, Object> data) {

        if(this.database != null)
            this.database.updateDiet(data);

    }

    /**
     * Retrieves the meal ids for the specified Diet
     * @param dietId The Id corresponding to the Diet
     */
    @Override
    public List<String> getMealIdsForDiet(final String dietId) {

        this.mealIds = new ArrayList<>();

        if(this.database != null)
            this.mealIds = this.database.getDiet(dietId).getMealIds();

        return this.mealIds;

    }

    /**
     * Sets the Diet from the database
     * @param meals The MealContract.Meals to set
     */
    @Override
    public void setMeals(List<MealContract.Meal> meals) {

        this.meals = meals;

        if(this.view != null)
            this.view.setDiet(this.meals);

    }

    @Override
    public void onGetDietRequest(final String id) {
        view.onDisplayDietInfo(this.database.getDiet(id));
    }

    @Override
    public void onUpdateDietRequest(final Map<String, Object> data) {
        this.database.updateDiet(data);
    }

}
