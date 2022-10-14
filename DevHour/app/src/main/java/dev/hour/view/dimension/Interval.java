package dev.hour.view.dimension;

/**
 * Defines an Interval with Starting and Ending values, as well as including
 * methods that return calculated Deltas and Values that are guaranteed to be constrained
 * in [start, end]
 *
 * @since 1.0.0
 */
public class Interval {

    public final static float DEFAULT_START = 0f;
    public final static float DEFAULT_END   = 0f;

    public Interval() {

        this.start = DEFAULT_START;
        this.end   = DEFAULT_END;

    }

    /// --------------
    /// Public Members

    /**
     * The interval's start value
     */
    public float start;

    /**
     * The interval's end value
     */
    public float end;

    /**
     * Returns the value of the [Interval]'s delta
     */
    public float getDelta() {

        return end - start;

    }

    /// --------------
    /// Public Methods

    /**
     * Returns a value that's guaranteed within the interval
     */
    public float getConstrainedValue(final float value) {

        return (value < start) ? start : Math.min(value, end);

    }

    /**
     * Mutates the current state to the values of the given instance.
     * @param interval The interval values to set this instance to
     */
    public void setTo(final Interval interval) {

        start = interval.start;
        end   = interval.end;

    }

}