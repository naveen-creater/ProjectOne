package com.example.projectone.BindingLibrary;


import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class CustomBindingAdapters {
    @BindingAdapter("changeBackgroundAndTextOnTap")
    public static void changeBackgroundAndTextOnTap(final TextView view, boolean shouldChange) {
        if (shouldChange) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setBackgroundColor(Color.HSVToColor(new float[]{(int)Math.round(Math.random() * 360), 0.8f, 0.4f}));
                    view.setText("" + (Math.random() * 10000));
                }
            });
        }
    }
}
