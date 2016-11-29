package wear.sunshine.android.example.com.capstone_1.utilities;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by jibin on 25/11/16.
 */

public class FixedSpeedScroller
        extends Scroller {

    private int mDuration = 3000;

    public FixedSpeedScroller(Context context) {
        super(context);

    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);

    }

    public FixedSpeedScroller(Context context, Interpolator interpolator,
                              boolean flywheel) {
        super(context, interpolator, flywheel);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);

    }
}
