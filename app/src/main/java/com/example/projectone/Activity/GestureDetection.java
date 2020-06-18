package com.example.projectone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectone.R;
import com.google.android.material.snackbar.Snackbar;

public class GestureDetection extends AppCompatActivity {
    private Button gesture;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detection);
        //initView
        initView();

        listeners();

    }

    private void initView() {
        gesture = findViewById(R.id.gesture);
        constraintLayout = findViewById(R.id.gesture_layout);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listeners() {

        gesture.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "Swiped top", Toast.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar.make(constraintLayout, "Swiped top", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "Swiped right", Toast.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar.make(constraintLayout, "Swiped right", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "Swiped left", Toast.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar.make(constraintLayout, "Swiped left", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "Swiped bottom", Toast.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar.make(constraintLayout, "Swiped bottom", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }

        });
    }

    class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 300;
            private static final int SWIPE_VELOCITY_THRESHOLD = 300;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i("TAG", "onSingleTapConfirmed:");
                Toast.makeText(getApplicationContext(), "Single Tap Detected", Toast.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar.make(constraintLayout, "Single Tap Detected", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("TAG", "onLongPress:");
                Toast.makeText(getApplicationContext(), "Long Press Detected", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(constraintLayout, "Long Press Detected", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(getApplicationContext(), "Double Tap Detected", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(constraintLayout, "Double Tap Detected", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}
