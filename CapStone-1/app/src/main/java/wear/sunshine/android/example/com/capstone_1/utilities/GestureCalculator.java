package wear.sunshine.android.example.com.capstone_1.utilities;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by jibin on 25/11/16.
 */

public abstract class GestureCalculator extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_DISTANCE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    public abstract void onSwipeLeftToRight();

    public abstract void onSwipeRightToLeft();

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        float distanceX = e2.getX() - e1.getX();
        float distanceY = e2.getY() - e1.getY();

        if (Math.abs(distanceX) > Math.abs(distanceY)
                && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD
                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (distanceX > 0) {
                onSwipeLeftToRight();
            } else {
                onSwipeRightToLeft();
            }

            return true;
        }
        return false;
    }

}

