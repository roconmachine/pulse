package com.edu.io.pulse.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Canvas extends View {
    public Canvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(@NonNull android.graphics.Canvas canvas) {
        super.onDraw(canvas);
    }
}
