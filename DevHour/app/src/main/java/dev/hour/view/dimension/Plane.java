package dev.hour.view.dimension;

/**
 * Representation of a defined plane. Holds two [Interval]
 * instances representing the Horizontal and Vertical constraints of the Plane
 *
 * @author: Carlos L. Cuenca
 * @since: 1.0.0.0
 */
public class Plane {

    public Plane() {

        this.horizontalInterval = new Interval();
        this.verticalInterval   = new Interval();

    }

    /// --------------
    /// Public Members

    /**
     * The horizontal interval
     */
    private Interval horizontalInterval;

    /**
     * The vertical interval
     */
    private Interval verticalInterval;

    /// --------------
    /// Public Methods

    /**
     * Sets the default value of the member variables
     */
    public void clear() {

        horizontalInterval .start = Interval.DEFAULT_START;
        horizontalInterval .end   = Interval.DEFAULT_END;
        verticalInterval   .start = Interval.DEFAULT_START;
        verticalInterval   .end   = Interval.DEFAULT_END;

    }

    /**
     * Mutates the current state to the values of the given [Plane]
     * @param plane The desired plane to set to
     */
    public void setTo(final Plane plane) {

        horizontalInterval.setTo(plane.horizontalInterval);
        verticalInterval.setTo(plane.verticalInterval);

    }

    public Interval getHorizontalInterval() {

        return this.horizontalInterval;

    }

    public Interval getVerticalInterval() {

        return this.verticalInterval;

    }

}