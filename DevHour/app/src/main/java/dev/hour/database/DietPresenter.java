package dev.hour.database;

import dev.hour.contracts.MealContract;

public class DietPresenter implements MealContract.Diet.Presenter {
    //Private Members
    private MealContract.Diet.Database database;
    private MealContract.Diet.View view;

    @Override
    public void setDatabase(MealContract.Diet.Database database) {
        this.database = database;
    }

    @Override
    public void setView(MealContract.Diet.View view) {

    }
}
