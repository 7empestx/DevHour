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
        List<String> getTags();
        void setTags(final List<String> tags);

        void setId(final String id);
        void setName(final String name);
        void setCalories(final int calories);
        void setIngredients(final Map<String, String> ingredients);
        void setImageStream(final OutputStream imageStream);

        interface Database {

            void setCredentials(final Map<String, String> credentials);
            Meal getMeal(final String id);
            void createMeal(final Map<String, Object> data);
            List<Meal> getMealsFrom(final List<String> mealIds);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setView(final View view);
            void setMealFrom(final String id);
            List<Meal> getMealsFrom(final List<String> mealIds);

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
            void setMeals(final List<Meal> meals);
            void setView(final View view);
            void updateDiet(final Map<String, Object> data);
            List<String> getMealIdsForDiet(final String dietId);

            interface InteractionListener {

                void onShowDietRequest();
                void onShowCustomerAddDietMeal(final Map<String, Object> export);
                void onShowCustomerAddDietAddTag(final Map<String, Object> export);
                void onMealSelected(final Meal meal);

            }

        }

        interface View {

            void setDiet(final List<Meal> meals);
            void setDietListener(MealContract.Diet.View.Listener listener);
            void onDisplayDietInfo(MealContract.Diet diet);
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
        void setTags(final List<String> tags);

        interface Database {

            void setCredentials(final Map<String, String> credentials);
            void updateMenu(final Map<String, Object> data);
            Menu getMenu(final String id);

        }

        interface Presenter {

            void setDatabase(final Database database);
            void setMeals(final List<Meal> meals);
            void setView(final View view);
            void updateMenu(final Map<String, Object> data);
            List<String> getMealIdsForMenu(final String menuId);

            interface InteractionListener {

                void onAddMenuItemImageRequest(final Map<String, Object> export);
                void onAddMenuItemIngredientRequest(final Map<String, Object> export);
                void onAddMenuItemTagRequest(final Map<String, Object> export);
                void onShowMenuRequest();
                void onUpdateMealRequest(final Meal meal);
                void onCreateMealRequest(final Map<String, Object> export);
                void onCloseBusinessMenuRequest();
                void onBusinessMealSelected(final Meal meal);

            }

        }

        interface View {

            void setMenu(final List<Meal> meals);

        }

    }

}
