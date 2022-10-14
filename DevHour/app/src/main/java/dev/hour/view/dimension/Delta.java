package dev.hour.view.dimension;

/**
 * Simple Class that holds two values and returns the value
 * of the Delta
 */
public class Delta {

    /// --------------
    /// Static Members

    private final static int DEFAULT_START  = 0;
    private final static int DEFAULT_END    = 0;

    /// --------------
    /// Public Members

    /**
     * The starting value
     */
    public int start = DEFAULT_START;

    /**
     * The ending value
     */
    public int end = DEFAULT_END;

    /**
     * Returns the current value of the Delta
     * by subtracting the mEnd value from the mStart value
     * @return int Signed delta value
     */
    public int getValue() {

        return end - start;

    }

}