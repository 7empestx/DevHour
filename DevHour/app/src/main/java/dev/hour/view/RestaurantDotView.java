package dev.hour.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.RestaurantContract;

@SuppressLint("ViewConstructor")
public class RestaurantDotView extends MapObjectView {

    /// --------------
    /// Private Fields

    private static Map<String, Integer> ICON_SET  = null;
    private static Map<String, Integer> COLOR_SET = null;

    /// ----------------------
    /// Private Static Methods

    /**
     * Initializes the color set for the user dots.
     * @param context The constructing context
     */
    private static void SetupColorSet(final Context context) {

        if(RestaurantDotView.COLOR_SET == null) {

            COLOR_SET = new HashMap<>();

            COLOR_SET.put(context.getString(R.string.restaurant), R.color.restaurant);

        }

    }

    /**
     * Initializes the Icon set for the user
     * @param context The constructing context
     */
    private static void SetupIconSet(final Context context) {

        if(RestaurantDotView.ICON_SET == null) {

            ICON_SET = new HashMap<>();

            ICON_SET.put(context.getString(R.string.restaurant), R.drawable.icon_store);
            ICON_SET.put(context.getString(R.string.cafe), R.drawable.icon_cafe);
            ICON_SET.put(context.getString(R.string.store), R.drawable.icon_store);

        }

    }

    /// ------------
    /// Constructors

    /**
     * Initializes the UserDotView to its' default state
     * @param context The constructing Context
     */
    public RestaurantDotView(final Context context, final RestaurantContract.Restaurant user) {
        super(context, (MapObjectContract.MapObject) user);

        final Resources resources   = getResources();
        final float     density     = resources.getDisplayMetrics().density;
        final float     elevation   = resources.getDimensionPixelSize(R.dimen.user_map_dot_view_elevation);

        final Drawable background = resources.getDrawableForDensity(
                R.drawable.restaurant_map_dot_view_dark, (int) density, null);

        setBackground(background);
        setElevation(elevation);

    }

    /// --------------
    /// Public Methods

    /**
     * Returns a Color from the specified type.
     * @param type The type corresponding with the color to retrieve
     * @return Integer representing a color
     */
    public Integer getColorFromType(final String type) {

        RestaurantDotView.SetupColorSet(getContext());

        Integer colorId = RestaurantDotView.COLOR_SET.get(type);

        if(colorId == null) {

            colorId = R.color.restaurant;

        }

        return getResources().getColor(colorId, null);

    }

    /**
     * Returns an Icon from the specified type.
     * @param type The type corresponding with the icon to retrieve
     * @return Integer representing an Icon
     */
    public Drawable getIconFromType(final String type) {

        RestaurantDotView.SetupIconSet(getContext());

        Integer iconId = RestaurantDotView.ICON_SET.get(type);

        if(iconId == null) {

            iconId = R.drawable.icon_store;

        }

        return getResources().getDrawableForDensity(
                iconId, (int) getResources().getDisplayMetrics().density, null);

    }

}
