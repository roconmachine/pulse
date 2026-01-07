package com.edu.io.pulse.core;

import android.content.Context;
import android.graphics.Canvas;
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
        // Initialize paints by copying the TextView's default paint.
        // This respects attributes like textSize and textColor set in XML.
        englishTextPaint = new TextPaint(getPaint());
        englishTextPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

        banglaTextPaint = new TextPaint(getPaint());
        // Load the custom font for the second style.
        Typeface customFont = ResourcesCompat.getFont(getContext(), R.font.sutonnymj_regular);
        if (customFont != null) {
            banglaTextPaint.setTypeface(customFont);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.text = getText().toString();
        if (text == null || text.isEmpty()) {
            super.onDraw(canvas); // Let super handle hint text or empty state
            return;
        }

        // We are handling the drawing ourselves, so we don't call super.onDraw().

        boolean isBangla = true;
        float cursorX = getPaddingLeft();
        // getBaseline() is a good starting Y for the first line.
        float cursorY = getBaseline();
        float lineHeight = getPaint().getFontSpacing() + 5;
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();


        String[] arrMixedText = text.split("\\$");
        for (String segment : arrMixedText) {
            if (segment.isEmpty()) {
                isBangla = !isBangla;
                continue;
            }

            TextPaint currentPaint = isBangla ? banglaTextPaint : englishTextPaint;

            int charsProcessed = 0;
            while (charsProcessed < segment.length()) {
                float remainingWidthOnLine = availableWidth - (cursorX - getPaddingLeft());
                
                // Determine how many characters from the segment can fit on the rest of the current line.
                int charsToDraw = currentPaint.breakText(segment, charsProcessed, segment.length(), true, remainingWidthOnLine, null);

                // If no characters fit on the current line, it means the line is full.
                if (charsToDraw == 0 && cursorX > getPaddingLeft()) {
                    // Move to the next line.
                    cursorX = getPaddingLeft();
                    cursorY += lineHeight;
                    // Recalculate how many characters fit on a full new line.
                    charsToDraw = currentPaint.breakText(segment, charsProcessed, segment.length(), true, availableWidth, null);
                    // If still zero, it implies a single character is wider than the view. We draw at least one to avoid an infinite loop.
                    if (charsToDraw == 0) {
                        charsToDraw = 1;
                    }
                }
                
                String chunkToDraw = segment.substring(charsProcessed, charsProcessed + charsToDraw);
                canvas.drawText(chunkToDraw, cursorX, cursorY, currentPaint);

                cursorX += currentPaint.measureText(chunkToDraw);
                charsProcessed += charsToDraw;
            }

            isBangla = !isBangla;
        }
    }
}
