package dev.hour.contracts;

import java.util.List;
import java.util.Map;

public interface MealContract {

    interface Meal {

        int getCalories();
        String getName();
        Map<String, Ingredient> getIngredients();

        interface View {

            void setIngredients(final Map<String, Ingredient> ingredients);

        }

    }

    enum Ingredient {
        Potato,
        Salt,


    }

    enum Diets {
        VEGAN,
        VEGETARIAN,
        HALAL,
        KOSHER,
        LACTOSE_FREE,
        GLUTEN_FREE

    }

    interface Diet {

        List<Ingredient> getAllergens();
        List<Diets> getDiets();

        interface Database {

            void setCredentials(final Map<String, String> credentials);
            Diet getDiet(final String id);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setView(final View view);

        }

        interface View {

            void setDiet(final Diet diet);
            void setDietListener(MealContract.Diet.View.Listener listener);
            void onDisplayDietInfo(final Diet diet);

            interface Listener {

                void onGetDietRequest(String id);

            }

        }

    }

    interface Menu {

        String getId();
        List<Meal>  getMeals();

        interface Database {

            List<Meal> getMenu(final String menuId);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setMenu(final String menuId);
            void setView(final View view);

            interface InteractionListener {

                void onShowMenuRequest();
                void onShowBusinessAddMenuMeal(final Map<String, Object> export);
                void onShowBusinessAddMenuAddTag(final Map<String, Object> export);

            }

        }

        interface View {

            void setMenu(final List<Meal> meals);

        }

    }

}
