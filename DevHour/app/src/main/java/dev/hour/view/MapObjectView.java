package dev.hour.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import dev.hour.R;
import dev.hour.contracts.MapObjectContract;

public class MapObjectView extends View {

    /// ---------------
    /// Private Members

    private MapObjectContract.MapObject mapObject;

    /// ------------
    /// Constructors

    /**
     * Initializes the MapObjectView to its' default state
     * @param context The constructing Context
     */
    public MapObjectView(final Context context, final MapObjectContract.MapObject mapObject) {
        super(context);

        this.mapObject = mapObject;

        final Resources resources = getResources();

        setLayoutParams(
                new ViewGroup.LayoutParams(
                        resources.getDimensionPixelSize(R.dimen.map_object_view_width),
                                resources.getDimensionPixelSize(R.dimen.map_object_view_height)));

    }

    /// --------------
    /// Public Methods

    /**
     * Returns the MapObjectContract.MapObject bound to this instance
     * @return MapObjectContract.MapObject
     */
    public MapObjectContract.MapObject getMapObject() {

        return this.mapObject;

    }

    /**
     * Sets the MapObjectContract.MapObject corresponding to this view.
     * @param mapObject The MapObjectContract.MapObject to set.
     */
    public void setMapObject(final MapObjectContract.MapObject mapObject) {

        this.mapObject = mapObject;

    }

}
