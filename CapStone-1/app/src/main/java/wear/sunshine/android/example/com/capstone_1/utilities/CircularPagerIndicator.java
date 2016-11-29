package wear.sunshine.android.example.com.capstone_1.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import wear.sunshine.android.example.com.capstone_1.R;

/**
 * Created by jibin on 25/11/16.
 */

public class CircularPagerIndicator extends View {

    private final int mSelectedColor;
    private final int mNormalColour;
    private boolean mIsStrokeNormal = false;
    private int mCurrentPageIndex = 0;
    private int mCount;
    private float mRadius;
    private float mLeftPadding;
    private Paint mItemPaint;
    private Paint mSelectedItemPaint;

    public CircularPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircularPagerIndicator,
                0, 0);

        try {
            mSelectedColor = a.getColor(R.styleable.CircularPagerIndicator_selectedColor,
                    ContextCompat.getColor(context.getApplicationContext()
                            , R.color.selected));
            mIsStrokeNormal = a.getBoolean(R.styleable.CircularPagerIndicator_selectedColor,
                    false);
            mNormalColour = a.getColor(R.styleable.CircularPagerIndicator_selectedColor,
                    ContextCompat.getColor(context.getApplicationContext()
                            , R.color.normal));
            mRadius = a.getDimension(R.styleable.CircularPagerIndicator_selectedColor
                    , getResources().getDimension(R.dimen.radius));
        } finally {
            a.recycle();
        }
        mLeftPadding = mRadius + getResources().getDimension(R.dimen.width_1);
        setPaintParams();

    }

    private void setPaintParams() {
        mItemPaint = new Paint();
        if (mIsStrokeNormal) {
            mItemPaint.setStyle(Paint.Style.STROKE);
            mItemPaint.setStrokeWidth(getResources().getDimension(R.dimen.width_1));
        } else {
            mItemPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        mItemPaint.setAntiAlias(true);
        mItemPaint.setDither(true);
        mItemPaint.setColor(mNormalColour);
        mSelectedItemPaint = new Paint();
        mSelectedItemPaint.setAntiAlias(true);
        mSelectedItemPaint.setDither(true);
        mSelectedItemPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mSelectedItemPaint.setColor(mSelectedColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int centerY = getHeight() / 2;
        float centerX;

        if (mCount == 0) {
            return;
        }

        for (int i = 0; i < mCount; i++) {
            centerX = mLeftPadding + (3 * mRadius * i);
            canvas.drawCircle(centerX, centerY, mRadius, mItemPaint);
        }
        centerX = mLeftPadding + (3 * mRadius * mCurrentPageIndex);
        canvas.drawCircle(centerX, centerY, mRadius, mSelectedItemPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLong(widthMeasureSpec),
                measureShort(heightMeasureSpec));

    }

    private int measureLong(int measureSpec) {
        int result;
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mCount == 0)) {
            result = specSize;
        } else {

            result = (int) (mLeftPadding + getPaddingRight()
                    + (mCount * 2 * mRadius) + (mCount - 1) * mRadius + 1);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;

    }

    private int measureShort(int measureSpec) {
        int result;
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // Measure the height
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;

    }

    public void pageChanged(int arg0) {
        mCurrentPageIndex = arg0;
        invalidate();

    }

    public void setmCount(int mCount) {
        this.mCount = mCount;

    }
}