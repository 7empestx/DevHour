package dev.hour.contracts;

import java.util.List;
import java.util.Map;

public interface MealContract {

    interface Meal {

        int getCalories();
        String getName();
        Map<String, Ingredient> getIngredients();

    }

    enum Ingredient {

    }

    interface Diet {

        List<Ingredient> getAllergens();

        interface Database {

            Diet getDiet(final String id);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setView(final View view);

        }

        interface View {

            void setDiet(final Diet diet);

        }

    }

    interface Menu {

        String getName();
        List<Meal> getMeals();

        interface Database {

            List<Meal> getMenu();

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setView(final View view);

        }

        interface View {

            void setMenu(final Menu menu);

        }

    }

}
