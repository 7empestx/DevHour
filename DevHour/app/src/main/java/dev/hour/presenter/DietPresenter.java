package dev.hour.presenter;

import java.util.Map;

import dev.hour.contracts.MealContract;

public class DietPresenter implements MealContract.Diet.Presenter, MealContract.Diet.View.Listener {

    private MealContract.Diet.Database database;
    private MealContract.Diet.View view;

    @Override
    public void setDatabase(MealContract.Diet.Database database) {
        this.database = database;
    }

    @Override
    public void setDiet(String menuId) {

    }

    @Override
    public void setView(MealContract.Diet.View view) {
        this.view = view;
    }

    @Override
    public void createDiet(Map<String, Object> data) {

    }

    @Override
    public void onGetDietRequest(String id) {
        MealContract.Diet diet = database.getDiet(id);
        view.onDisplayDietInfo(diet);
    }
}
