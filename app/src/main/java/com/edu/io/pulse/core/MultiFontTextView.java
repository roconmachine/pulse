package com.edu.io.pulse.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.edu.io.pulse.R;

public class MultiFontTextView extends AppCompatTextView {

    private TextPaint englishTextPaint; // First font style
    private TextPaint banglaTextPaint; // Second font style

    private String text;

    public MultiFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public MultiFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Initialize the first font style
        englishTextPaint = new TextPaint();
        englishTextPaint.setAntiAlias(true);
        //englishTextPaint.setColor(getCurrentTextColor());
        englishTextPaint.setTextSize(getTextSize());
        englishTextPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

        // Initialize the second font style
        banglaTextPaint = new TextPaint();
        banglaTextPaint.setAntiAlias(true);
        //banglaTextPaint.setColor(getCurrentTextColor());
        banglaTextPaint.setTextSize(getTextSize());
//        banglaTextPaint.setTypeface(Typeface.create("serif", Typeface.ITALIC));
        Typeface customFont = ResourcesCompat.getFont(getContext(), com.edu.io.pulse.R.font.sutonnymj_regular);
        banglaTextPaint.setTypeface(customFont);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        boolean isBangla = true;
        float cursorX = 0;
        float cursorY = getBaseline();
        this.text = super.getText().toString();
        if (text != null) {
            String[] arrMixedText = text.split("\\$");
            for(String segment: arrMixedText){
                TextPaint currentPaint = isBangla ? banglaTextPaint : englishTextPaint;
                float segmentWidth = currentPaint.measureText(segment);
                if (cursorX + segmentWidth > getWidth()) {
                    cursorX = 0;
                    cursorY += currentPaint.getTextSize() + 0;
                }

                canvas.drawText(segment, cursorX, cursorY, currentPaint);
                cursorX += segmentWidth;
                isBangla=!isBangla;
            }
        }
    }
}
