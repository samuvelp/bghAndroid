package com.gospel.bethany.bgh.customViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.gospel.bethany.bgh.R;

public class SpinnerImageView extends AppCompatImageView {

    private static final int ROTATE_ANIMATION_DURATION = 800;
    private static final int IMAGE_RESOURCE_ID = R.drawable.ic_play_arrow_black_24dp;
    private boolean isSpinning = false;

    public SpinnerImageView(Context context) {
        super(context, null);
    }

    public SpinnerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Set and scale the image
        setImageResource(IMAGE_RESOURCE_ID);

        // Start the animation
//        startAnimation();
    }

    /**
     * Starts the rotate animation.
     */
    public void startAnimation() {
        isSpinning = true;
        clearAnimation();

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(ROTATE_ANIMATION_DURATION);
        rotate.setRepeatCount(Animation.INFINITE);
        startAnimation(rotate);
    }

    public void stopAnimation() {
        clearAnimation();
        isSpinning = false;
    }

    public boolean isSpinning() {
        return isSpinning;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == View.VISIBLE) {
//            startAnimation();
        } else {
            clearAnimation();
        }
    }
}
