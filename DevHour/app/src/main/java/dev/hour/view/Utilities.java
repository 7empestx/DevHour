package dev.hour.view;

import android.animation.ValueAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.hour.view.dimension.Interval;
import dev.hour.view.dimension.Plane;

public class Utilities {

    /**
     * Creates and returns a [Plane] instance offset from the given amount
     * @param offsetX The quantity to offset horizontally
     * @param offsetY The quantity to offset vertically
     * @return [Plane] instance with the given constraints
     */
    public static Plane translationOffsetPlane(final float offsetX, final float offsetY) {

        final Plane translationPlane = new Plane();

        translationPlane.getHorizontalInterval().start = 0.0f;
        translationPlane.getHorizontalInterval().end   = offsetX;
        translationPlane.getVerticalInterval().start   = 0.0f;
        translationPlane.getVerticalInterval().end     = offsetY;

        return translationPlane;

    }

    /**
     * Returns a [Plane] instance with the starting value of the view translation, and the end
     * value offset from the starting value.
     * @param view The view of concern
     * @param offsetX The horizontal offset quantity away from the view's origin
     * @param offsetY The vertical offset quantity away from the view's origin
     * @return [Plane] instance
     */
    public static Plane viewTranslationOffsetPlane(final View view, final float offsetX, final float offsetY) {

        return translationOffsetPlane(offsetX, offsetY);
    }

    /**
     * Creates a float [ValueAnimator] based on the given interval and duration
     * @param interval The start and end values for the [ValueAnimator]
     * @param duration The animation duration
     * @return [ValueAnimator] instance, or null
     */
    public static ValueAnimator floatValueAnimatorForInterval(final Interval interval, final int duration) {

        final ValueAnimator animator = ValueAnimator.ofFloat(interval.start, interval.end);

        animator.setDuration(duration);

        return animator;

    }

    /**
     * Returns a [ValueAnimator] instance that mutates the given view's horizontal translation.
     * This method adds the appropriate [ValueAnimator.AnimatorUpdateListener]
     * that mutates the Listener's horiztonal translation
     * @param view The view of concern
     * @param interval The [Interval] instance specifying the starting and ending values
     * @param duration The Animation duration
     * @return [ValueAnimator] with the corresponding [ValueAnimator.AnimatorUpdateListener]
     */
    public static ValueAnimator floatHorizontalTranslationAnimator(final View view, final Interval interval, int duration) {

        final ValueAnimator horizontalAnimator = floatValueAnimatorForInterval(interval, duration);

        horizontalAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private final float start = view.getTranslationX();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setTranslationX(start + ((float) valueAnimator.getAnimatedValue()));
                view.invalidate();

            }
        });

        return horizontalAnimator;

    }

    /**
     * Returns a [ValueAnimator] instance that mutates the given view's vertical translation.
     * This method adds the appropriate [ValueAnimator.AnimatorUpdateListener]
     * that mutates the Listener's vertical translation
     * @param view The view of concern
     * @param interval The [Interval] instance specifying the starting and ending values
     * @param duration The Animation duration
     * @return [ValueAnimator] with the corresponding [ValueAnimator.AnimatorUpdateListener]
     */
    public static ValueAnimator floatVerticalTranslationAnimator(final View view, final Interval interval, final int duration) {

        final ValueAnimator verticalAnimator = floatValueAnimatorForInterval(interval, duration);

        verticalAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private final float start = view.getTranslationY();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setTranslationY(start + ((float) valueAnimator.getAnimatedValue()));
                view.invalidate();

            }
        });

        return verticalAnimator;

    }

    /**
     * Returns an [Collection] instance with two animators: a horizontal & vertical [ValueAnimator]
     * instances with their proper updateDeployment listeners set to the reference of the given Listener.
     * The [ValueAnimator] instances will animate the view from it's current position
     * up to its' offset from that position.
     * @param view The view of Concern
     * @param offsetX The Horizontal quantity to offset the view by
     * @param offsetY The Vertical quantity to offset the view by
     * @param duration The Animation duration
     * @return [Collection] instance containing the horizontal and vertical [ValueAnimator]
     * instances
     */
    public static Collection<ValueAnimator> floatTranslationOffsetAnimatorCollection(final View view, final float offsetX, final float offsetY, int duration) {

        final List<ValueAnimator>   animators = new ArrayList<>();
        final Plane                 plane     = viewTranslationOffsetPlane(view, offsetX, offsetY);

        animators.add(floatHorizontalTranslationAnimator(view, plane.getHorizontalInterval(), duration));
        animators.add(floatVerticalTranslationAnimator(view, plane.getVerticalInterval(), duration));

        return animators;

    }

}
