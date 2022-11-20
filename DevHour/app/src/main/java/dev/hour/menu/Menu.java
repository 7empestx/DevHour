package dev.hour.menu;

import java.util.List;

import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.MealContract;

public class Menu implements MealContract.Menu , MapObjectContract.MapObject {

    private String menuId;
    private List<MealContract.Meal> meals;

    Menu(final String id){
        this.menuId = id;
    }

    Menu(final String id, List<MealContract.Meal> meals){
        this.menuId = id;
        this.meals = meals;
    }

    @Override
    public String getId(){
        return this.menuId;
    }

    @Override
    public List<MealContract.Meal> getMeals(){
        return this.meals;
    }

    @Override
    public void setId(String id) {

    }

    public void setMeals(List<MealContract.Meal> meals){
        this.meals =  meals;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public double getLatitude() {
        return 0;
    }
}
