package dev.hour.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.CoordinateBounds;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.observable.eventdata.RenderFrameFinishedEventData;
import com.mapbox.maps.extension.observable.eventdata.RenderFrameStartedEventData;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.delegates.listeners.OnRenderFrameFinishedListener;
import com.mapbox.maps.plugin.delegates.listeners.OnRenderFrameStartedListener;
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin;
import com.mapbox.maps.plugin.scalebar.ScaleBarUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.RestaurantContract;
import dev.hour.contracts.UserContract;

public class MapView extends FrameLayout implements
        OnRenderFrameStartedListener,
        OnRenderFrameFinishedListener,
        View.OnClickListener,
        SearchView.OnQueryTextListener {

    /// ----------------------
    /// Private Static Members

    private final static double MINIMUM_ZOOM         = 0.0  ;
    private final static double MAXIMUM_ZOOM         = 20.0 ;
    private final static double DEFAULT_MINIMUM_ZOOM = 15.0 ;
    private final static String DEFAULT_MAP_STYLE_ID = ""   ;
    private final static long   ANIMATION_DURATION   = 300  ;
    private final static double DEFAULT_CAMERA_ZOOM  = 15.0 ;

    /// ---------------
    /// Private Members

    /**
     * The MapView contained in this ViewGroup
     */
    private final com.mapbox.maps.MapView           mapView             ;

    /**
     * The SearchBar contained in this ViewGroup
     */
    private final SearchBar                         searchBar           ;

    /**
     * The ScaleBar contained in this ViewGroup
     */
    private final ScaleBarPlugin                    scaleBarPlugin      ;

    /**
     * The Map instance that is used to manipulate the
     * on-screen map
     */
    private final MapboxMap                         mapboxMap           ;

    /**
     * The collection of UserDotViews contained within the map
     */
    private final Map<String, UserDotView>          userDotViews        ;

    /**
     * The set of RestaurantDotViews displayed on the Map
     */
    private final Map<String, RestaurantDotView>    restaurantDotViews  ;

    /**
     * The focus MapObjectContract.MapObject
     */
    private MapObjectContract.MapObject             focusMapObject      ;

    /**
     * The focus MapObjectContract.MapObject
     */
    private SearchListener                          searchListener      ;

    /// ------------
    /// Constructors

    /**
     * Initializes the MapView to its' default state
     * @param context The constructing Context
     */
    public MapView(final Context context){
        super(context);

        this.userDotViews       = new HashMap<>();
        this.restaurantDotViews = new HashMap<>();
        this.mapView            = new com.mapbox.maps.MapView(context);
        this.mapboxMap          = this.mapView.getMapboxMap();
        this.scaleBarPlugin = ScaleBarUtils.getScaleBar(this.mapView);;
        this.searchBar          = new SearchBar(context);
        scaleBarPlugin.setPosition(1);
        // Set the layout parameters
        setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.mapView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.searchBar.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // We want to set the children draw order in order to draw the user's dot
        // on top of the map as well as the flag that enables drawing on the ViewGroup's
        // canvas
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(false);

        // Load the style
        this.mapboxMap.loadStyleUri(Style.MAPBOX_STREETS);

        this.mapboxMap.addOnRenderFrameStartedListener(this);
        this.mapboxMap.addOnRenderFrameFinishedListener(this);

        this.searchBar.setElevation(16.0f);
        this.searchBar.setZ(16.0f);
        this.searchBar.setBackground(
        this.getContext().getResources().getDrawable(R.drawable.search_bar_background, null));
        this.searchBar.setOnSearchClickListener(this);
        this.searchBar.setOnQueryTextListener(this);

        // Add the views
        addView(this.mapView);
        addView(this.searchBar);

    }

    /// --------------
    /// MapBox.MapView

    /**
     * Invoked when the MapView has started
     */
    public void onStart() {

        if(mapView != null)
            mapView.onStart();

    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        this.mapView.measure(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float density = getResources().getDisplayMetrics().density;

        int widthSpec = MeasureSpec.makeMeasureSpec((int)(getMeasuredWidth() - density*4), MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec((int)(density*32), MeasureSpec.EXACTLY);

        this.searchBar.measure(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        this.mapView.layout(left,top,right,bottom);
        left = (getMeasuredWidth() - this.searchBar.getMeasuredWidth()) / 2;
        top  = (int)(getResources().getDisplayMetrics().density * 4);

        this.searchBar.layout(left, top, left + this.searchBar.getMeasuredWidth(), top + this.searchBar.getMeasuredHeight());
    }

    /**
     * Invoked when the MapView is stopping
     */
    public void onStop() {

        if(mapView != null)
            mapView.onStop();

    }

    /**
     * Invoked when the MapView is low on memory
     */
    public void onLowMemory() {

        if(mapView != null)
            mapView.onLowMemory();

    }

    /**
     * Invoked when the MapView is about to be destroyed
     */
    public void onDestroy() {

        if(mapView != null)
            mapView.onDestroy();

    }

    /// --------------------
    /// View.OnClickListener

    /**
     * Invoked when a View contained in the MapView has been clicked by the User.
     * @param view The view that has been clicked
     */
    @Override
    public void onClick(View view) {

        if((view instanceof UserDotView) || (view instanceof RestaurantDotView)) {

            updateFocus((MapObjectView) view);

        }
        else if(view instanceof SearchBar)
        {
            SearchBar searchBar = (SearchBar) view;
            searchBar.getQuery();
        }

    }

    /// ------------------
    /// MapBoxMap Listener

    @Override
    public void onRenderFrameFinished(@NonNull RenderFrameFinishedEventData renderFrameFinishedEventData) {

        updateUserDotViewPositions();

    }

    @Override
    public void onRenderFrameStarted(@NonNull RenderFrameStartedEventData renderFrameStartedEventData) {

        updateUserDotViewPositions();

    }

    /// ---------------
    /// Private Methods

    /**
     * Updates or creates a new UserDotView that represents the User on the map.
     * @param user The User to create a UserDotView for
     */
    public void update(final UserContract.User user) {

        final String userId = user.getId();

        UserDotView userDotView = userDotViews.get(userId);

        if(userDotView == null) {

            userDotView = new UserDotView(getContext(), user);

            final ViewGroup.LayoutParams parameters = userDotView.getLayoutParams();
            final int widthSpecification =
                    MeasureSpec.makeMeasureSpec(parameters.width, MeasureSpec.EXACTLY);
            final int heightSpecification =
                    MeasureSpec.makeMeasureSpec(parameters.height, MeasureSpec.EXACTLY);

            userDotView.measure(widthSpecification, heightSpecification);
            userDotView.layout(0, 0, parameters.width, parameters.height);

            this.userDotViews.put(userId, userDotView);

            this.mapView.addView(userDotView);

        }

        userDotView.setMapObject((MapObjectContract.MapObject) user);
        userDotView.setVisibility(View.VISIBLE);
        userDotView.setOnClickListener(this);

        setScreenLocationFor(userDotView);

    }

    /**
     * Invoked when the the UserContract.User is to be removed. The corresponding UserDotView
     * will be removed from the view before the User is removed.
     * @param user The corresponding UserContract.User to remove
     */
    public void remove(final UserContract.User user) {

        final String      userId      = user.getId()                ;
        final UserDotView userDotView = userDotViews.get(userId)    ;

        userDotViews.remove(user.getId());

        mapView.removeView(userDotView);

    }

    /**
     * Invoked when the view should clear its' UserDotViews.
     * Removes the views from the viewgroup and the record
     */
    public void clearUsers() {

        for(final UserDotView userDotView : userDotViews.values()) {

            final UserContract.User user = (UserContract.User) userDotView.getMapObject();

            this.mapView.removeView(userDotView);

        }

        userDotViews.clear();

    }

    /**
     * Updates or creates a new RestaurantDotView that represents the Restaurant on the map.
     * @param restaurant The Restaurant to create a RestaurantDotView for
     */
    public void update(final RestaurantContract.Restaurant restaurant) {

        final String restaurantId = restaurant.getId();

        RestaurantDotView restaurantDotView = restaurantDotViews.get(restaurantId);

        if(restaurantDotView == null) {

            restaurantDotView = new RestaurantDotView(getContext(), restaurant);

            final ViewGroup.LayoutParams parameters = restaurantDotView.getLayoutParams();
            final int widthSpecification =
                    MeasureSpec.makeMeasureSpec(parameters.width, MeasureSpec.EXACTLY);
            final int heightSpecification =
                    MeasureSpec.makeMeasureSpec(parameters.height, MeasureSpec.EXACTLY);

            restaurantDotView.measure(widthSpecification, heightSpecification);
            restaurantDotView.layout(0, 0, parameters.width, parameters.height);

            this.restaurantDotViews.put(restaurantId, restaurantDotView);

            this.mapView.addView(restaurantDotView);

        }

        restaurantDotView.setMapObject((MapObjectContract.MapObject) restaurant);
        restaurantDotView.setVisibility(View.VISIBLE);
        restaurantDotView.setOnClickListener(this);

        setScreenLocationFor(restaurantDotView);

    }

    /**
     * Sets the visible restaurants on the Map
     * @param restaurants The List of RestaurantContract.Restaurants to set.
     */
    public void set(final List<RestaurantContract.Restaurant> restaurants) {

        final int difference = restaurants.size() - this.restaurantDotViews.size();

        int index = 0;

        // If we are hiding restaurants
        if(difference < 0) {

            for(final RestaurantDotView restaurantDotView : this.restaurantDotViews.values()) {

                // If we haven't reached the maximum amount of restaurant views
                if(index < this.restaurantDotViews.size()) {

                    // Bind the new MapContract.MapObject
                    restaurantDotView.setMapObject((MapObjectContract.MapObject) restaurants.get(index));
                    restaurantDotView.setVisibility(View.VISIBLE);

                    // Update the screen location
                    setScreenLocationFor(restaurantDotView);

                } else {

                    // Set the view to gone.
                    restaurantDotView.setVisibility(View.GONE);

                }

                index++;

            }

        // We have an equal amount of restaurants, or we're adding
        } else {

            for(final RestaurantDotView restaurantDotView: this.restaurantDotViews.values()) {

                // Bind the new MapContract.MapObject
                restaurantDotView.setMapObject((MapObjectContract.MapObject) restaurants.get(index));
                restaurantDotView.setVisibility(View.VISIBLE);

                // Update the screen location
                setScreenLocationFor(restaurantDotView);

                index++;

            }

            while(index < restaurants.size()) {

                // Add a new view
                update(restaurants.get(index));

                index++;

            }

        }

    }

    /**
     * Creates a [CameraOptions] instance at the center of the of the given
     * [CoordinateBounds]
     * @param boundaries The boundaries to get the center from
     * @return [CameraOptions] instance
     */
    private CameraOptions getCenterCameraPosition(final CoordinateBounds boundaries) {

        final CameraOptions position;

        final Point northEast = boundaries.getNortheast();
        final Point southWest = boundaries.getSouthwest();

        final double centerLongitude =
                southWest.longitude() + ((northEast.longitude() - southWest.longitude()) / 2.0);
        final double centerLatitude =
                southWest.latitude() + ((northEast.latitude() - southWest.latitude()) / 2.0);

        if(this.mapboxMap != null) {

            final EdgeInsets padding = new EdgeInsets(0.0, 0.0, 0.0, 0.0);

            position = this.mapboxMap.cameraForCoordinateBounds(boundaries, padding, 0.0, 0.0);

        } else {

            position = new CameraOptions.Builder()
                    .center(Point.fromLngLat(centerLongitude, centerLatitude))
                    .zoom(DEFAULT_CAMERA_ZOOM)
                    .build();

        }

        return position;

    }

    /**
     * Pans the map camera to the MapObjectContract.MapObject position
     * @param mapObject The desired MapObjectContract.MapObject to show on the map
     */
    private void panTo(final MapObjectContract.MapObject mapObject) {

        if(this.mapboxMap != null) {

            final CameraAnimationsPlugin camera =
                    CameraAnimationsUtils.getCamera(this.mapView);

            camera.flyTo(
                    (new CameraOptions.Builder())
                            .center(Point.fromLngLat(
                                    mapObject.getLongitude(),
                                    mapObject.getLatitude()))
                            .build(),
                    new MapAnimationOptions.Builder().duration(ANIMATION_DURATION).build());

        }

    }

    /**
     * Invoked when the view should react to a focus MapObjectView updating
     * @param mapObjectView The focus MapObjectView
     */
    private void updateFocus(final MapObjectView mapObjectView) {

        this.focusMapObject = mapObjectView.getMapObject();

        panTo(this.focusMapObject);

    }

    /**
     * Returns a [Point] instance containing the Latitude & Longitude values corresponding
     * to the screen location as given by [ScreenCoordinate]
     * @param screenCoordinate The screen location
     * @return [Point] instance holding the Latitude & Longitude points
     */
    private Point getLatitudeLongitudeFromPoint(final ScreenCoordinate screenCoordinate) {

        return (this.mapboxMap == null) ?
                Point.fromLngLat(0.0, 0.0) :
                this.mapboxMap.coordinateForPixel(screenCoordinate);

    }

    /**
     * Sets the screen location for the given MapObjectView
     *
     * @param mapObjectView the view to set the screen location for
     */
    private void setScreenLocationFor(final MapObjectView mapObjectView) {

        // Retrieve the associated MapObject
        final MapObjectContract.MapObject mapObject = mapObjectView.getMapObject();

        // Obligatory check
        if(mapObjectView.getMapObject() != null && mapboxMap != null) {

            // Retrieve the screen location
            final ScreenCoordinate screenLocation = mapboxMap.pixelForCoordinate(
                    Point.fromLngLat(mapObject.getLongitude(), mapObject.getLatitude()));

            // Set the translation x
            mapObjectView.setTranslationX(
                    (float) screenLocation.getX() - (mapObjectView.getMeasuredWidth() / 2.0f));

            // Set the translation y
            mapObjectView.setTranslationY(
                    (float) screenLocation.getY() - (mapObjectView.getMeasuredHeight() / 2.0f));

        }

    }

    /**
     * Updates all of the user dot positions relative the the map
     */
    private void updateUserDotViewPositions() {

        for(final UserDotView userDotView : userDotViews.values())
            setScreenLocationFor(userDotView);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        if(this.searchListener != null) {
            this.searchListener.onSearch(query.toLowerCase(Locale.ROOT));
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(this.searchListener != null) {
            this.searchListener.onTextChange(query);
            return true;
        }
        else
            return false;
    }

    public interface SearchListener{
        void onSearch(String query);
        void onTextChange(String query);
    }

    public void setSearchListener(SearchListener searchListener){
        this.searchListener = searchListener;
    }
}
