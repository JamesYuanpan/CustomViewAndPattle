package com.dingmouren.paletteimageview;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            if (widthMode == MeasureSpec.AT_MOST && heigthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(getMaxChildWidth(), getTotalHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(getMaxChildWidth(), heightSize);
            } else if (heigthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSize, getTotalHeight());
            }
        }

    }

    private int getMaxChildWidth() {
        int maxWidth = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();

            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    private int getTotalHeight() {
        int totalHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            totalHeight += height;
        }
        return totalHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int current = t;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            child.layout(l, current, l + width, current + height);
            current += height;
        }
    }

}
