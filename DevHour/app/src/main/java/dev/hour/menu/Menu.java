package dev.hour.menu;

import dev.hour.contracts.MealContract;
import dev.hour.meal.Meal;

public class Menu implements MealContract.Menu , MapObjectContract.MapObject {

    private String menuId;
    private List<Meal> meals;

    Menu(final String id){
        this.menuId = id;
    }

    Menu(final String id, List<Meal> meals){
        this.menuId = id;
        this.meals = meals;
    }

    @Override
    public String getId(){
        return this.menuId;
    }

    @Override
    public List<Meal> getMeals(){
        return this.meals;
    }

    public void setMeals(List<Meal> meals){
        this.meals =  meals;
    }

}
