package dev.hour.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.hour.contracts.MealContract;
import dev.hour.contracts.RestaurantContract;

public class MenuPresenter implements MealContract.Menu.Presenter {

    /// ---------------
    /// Private Members

    private MealContract.Menu.Database  database    ;
    private MealContract.Menu.View      view        ;
    private List<String>                mealIds     ;
    private List<MealContract.Meal>     meals       ;

    /// ---------------------------
    /// MealContract.Menu.Presenter

    /**
     * Sets the [MealContract.Menu.Database] instance
     * @param database The [MealContract.Menu.Database] instance
     */
    @Override
    public void setDatabase(MealContract.Menu.Database database) {

        this.database = database;

    }

    /**
     * Sets the [MealContract.Menu.View] instance.
     * @param view The [MealContract.Menu.View] instance.
     */
    @Override
    public void setView(final MealContract.Menu.View view) {

        this.view = view;

        if(this.view != null)
            this.view.setMenu(meals);

    }

    /**
     * Invokes the [MealContract.Menu.Database] to create a Menu with the given data.
     * @param data The Menu data.
     */
    @Override
    public void updateMenu(final Map<String, Object> data) {

        if(this.database != null)
            this.database.updateMenu(data);

    }

    /**
     * Retrieves the meal ids for the specified Menu
     * @param menuId The Id corresponding to the Menu
     */
    @Override
    public List<String> getMealIdsForMenu(final String menuId) {

        this.mealIds = new ArrayList<>();

        if(this.database != null)
            this.mealIds = this.database.getMenu(menuId).getMealIds();

        return this.mealIds;

    }

    /**
     * Sets the Menu from the database
     * @param meals The MealContract.Meals to set
     */
    @Override
    public void setMeals(final List<MealContract.Meal> meals) {

        this.meals = meals;

        if(this.view != null)
            this.view.setMenu(this.meals);

    }

}

