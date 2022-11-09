package dev.hour.presenter;

import dev.hour.contracts.MealContract;

public class DietPresenter implements MealContract.Diet.Presenter,
        MealContract.Diet.View.Listener {
    //Private Members
    private MealContract.Diet.Database database;
    private MealContract.Diet.View view;

    @Override
    public void setDatabase(MealContract.Diet.Database database) {
        this.database = database;
    }

    @Override
    public void setView(MealContract.Diet.View view) {
        this.view = view;
    }

    @Override
    public void onGetDietRequest(String id) {
        MealContract.Diet diet = database.getDiet(id);
        view.onDisplayDietInfo(diet);
    }
}
