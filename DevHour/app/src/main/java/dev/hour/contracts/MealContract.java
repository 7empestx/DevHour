package dev.hour.contracts;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface MealContract {

    interface Meal {

        String getId();
        String getName();
        int getCalories();
        Map<String, String> getIngredients();
        OutputStream getImageStream();

        void setId(final String id);
        void setName(final String name);
        void setCalories(final int calories);
        void setIngredients(final Map<String, String> ingredients);
        void setImageStream(final OutputStream imageStream);

        interface Database {

            void setCredentials(final Map<String, String> credentials);
            Meal getMeal(final String id);
            void createMeal(final Map<String, Object> data);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setView(final View view);

        }

        interface View {

            void setMeal(final Meal meal);

        }

    }

    interface Diet {

        String getId();
        List<String> getAllergens();
        List<Meal> getMeals();
        List<String> getMealIds();
        
        void setId(final String id);
        void setAllergens(final List<String> allergens);
        void setMeals(final List<Meal> meals);
        void setMealIds(final List<String> mealIds);

        interface Database {

            void setCredentials(final Map<String, String> credentials);
            void updateDiet(final Map<String, Object> data);
            Diet getDiet(final String id);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setDiet(final String menuId);
            void setView(final View view);
            void createDiet(final Map<String, Object> data);

            interface InteractionListener {

                void onShowDietRequest();
                void onShowCustomerAddDietMeal(final Map<String, Object> export);
                void onShowCustomerAddDietAddTag(final Map<String, Object> export);
                void onMealSelected(final Meal meal);

            }

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
        List<Meal> getMeals();
        List<String> getMealIds();

        void setId(final String id);
        void setMeals(final List<Meal> meals);
        void setMealIds(final List<String> mealIds);

        interface Database {

            void setCredentials(final Map<String, String> credentials);
            void updateMenu(final Map<String, Object> data);
            Menu getMenu(final String id);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setMenu(final String menuId);
            void setView(final View view);
            void createMenu(final Map<String, Object> data);

            interface InteractionListener {

                void onShowMenuRequest();
                void onShowBusinessAddMenuMeal(final Map<String, Object> export);
                void onShowBusinessAddMenuAddTag(final Map<String, Object> export);
                void onMealSelected(final Meal meal);

            }

        }

        interface View {

            void setMenu(final List<Meal> meals);

        }

    }

}
