package dev.hour.presenter;

import dev.hour.contracts.MealContract;

public class MenuPresenter implements MealContract.Menu.Presenter {
    //Private Members
    private MealContract.Menu.Database database;
    private MealContract.Menu.View view;

    @Override
    public void setDatabase(MealContract.Menu.Database database) {
        this.database = database;
    }

    @Override
    public void setView(MealContract.Menu.View view) {

    }
}

