package dev.hour.presenter;

import java.util.List;
import java.util.Map;

import dev.hour.contracts.MealContract;
import dev.hour.contracts.RestaurantContract;

public class MenuPresenter implements MealContract.Menu.Presenter {
    //Private Members
    private MealContract.Menu.Database  database    ;
    private MealContract.Menu.View      view        ;
    private List<MealContract.Meal>     meals       ;

    @Override
    public void setDatabase(MealContract.Menu.Database database) {
        this.database = database;
    }

    @Override
    public void setMenu(String menuId) {

    }

    @Override
    public void createMenu(final Map<String, Object> data) {

        if(this.database != null)
            this.database.updateMenu(data);

    }

    @Override
    public void setView(MealContract.Menu.View view) {
        // Set the view
        this.view = view;

        if(this.view != null)
            this.view.setMenu(meals);
    }
}

