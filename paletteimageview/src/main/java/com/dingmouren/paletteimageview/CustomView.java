package com.dingmouren.paletteimageview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class CustomView extends ProgressBar {

    private static final int UNREACHEDCOLOR_DEFAULT = 0xFF69B4;
    private static final int REACHEDCOLOR_DEFAULT = 0xFF1493;
    private static final int INNERTEXTCOLOR_DEFAULT = 0xDC143C;
    private static final int INNERTEXTSIZE_DEFAULT = 10;
    private static final int PROGRESS_DEAULT = 0;
    private int unReachedColor;
    private int reachedColor;
    private int innerTextColor;
    private int innerTextSize;
    private int progress;
    private int realWidth;
    private int realHeight;
    private Paint underPaint;
    private Paint textPaint;
    private Path path;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private ArgbEvaluator argbEvaluator;
    private Context mContext;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        argbEvaluator = new ArgbEvaluator();
        this.mContext = context;
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.CustomView);
        unReachedColor = ta.getColor(R.styleable.CustomView_UnreachedColor, UNREACHEDCOLOR_DEFAULT);
        reachedColor = ta.getColor(R.styleable.CustomView_ReachedColor, REACHEDCOLOR_DEFAULT);
        innerTextSize = (int) ta.getDimension(R.styleable.CustomView_InnerTextSize, INNERTEXTCOLOR_DEFAULT);
        innerTextColor = ta.getColor(R.styleable.CustomView_InnerTextColor, INNERTEXTSIZE_DEFAULT);
        progress = ta.getInt(R.styleable.CustomView_Progress, PROGRESS_DEAULT);

        ta.recycle();

        underPaint = new Paint();
        textPaint = new Paint();
        path = new Path();

        underPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        underPaint.setStrokeWidth(5.0f);
        textPaint.setColor(innerTextColor);
        textPaint.setTextSize(innerTextSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int usedHeigth = getRealHeight(heightMeasureSpec);
        int usedWidth = getRealWidth(widthMeasureSpec);
        setMeasuredDimension(usedWidth, usedHeigth);
    }

    public int getRealWidth(int width) {
        int widthMode = MeasureSpec.getMode(width);
        int widthValue = MeasureSpec.getSize(width);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        if (widthMode == MeasureSpec.EXACTLY) {
            return paddingLeft + widthValue + paddingRight;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            return (int) (Math.abs(underPaint.ascent() - underPaint.descent()) + paddingLeft + paddingRight);
        } else {
            return (int) Math.min(Math.abs(underPaint.ascent() - underPaint.descent()) + paddingRight + paddingLeft
                , widthValue);
        }
    }

    public int getRealHeight(int width) {
        int widthMode = MeasureSpec.getMode(width);
        int widthValue = MeasureSpec.getSize(width);
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        if (widthMode == MeasureSpec.EXACTLY) {
            return paddingTop + widthValue + paddingBottom;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            return (int) (Math.abs(underPaint.ascent() - underPaint.descent()) + paddingTop + paddingBottom);
        } else {
            return (int) Math.min(Math.abs(underPaint.ascent() - underPaint.descent()) + paddingBottom + paddingTop
                    , widthValue);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        realHeight = h;
        realWidth = w;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();

        float pro = ((float) progress) / 100.0f;

        int nowColor = (int) argbEvaluator.evaluate(pro, unReachedColor, reachedColor);
        underPaint.setColor(nowColor);
        path.moveTo((float) (0.5 * realWidth), (float) (0.7 * realHeight));
        path.cubicTo((float) (0.15 * realWidth), (float) (-0.35 * realHeight), (float) (-0.4 * realWidth)
            , (float) (0.45 * realHeight), (float) (0.5 * realWidth), realHeight);
        path.moveTo((float) (0.5 * realWidth), realHeight);
        path.cubicTo((float) (realWidth + 0.4 * realWidth), (float) (0.45 * realHeight)
            , (float) (realWidth - 0.15 * realWidth), (float) (-0.35 * realWidth)
            , (float) (0.5 * realWidth), (float) (0.17 * realHeight));
        path.close();
        canvas.drawPath(path, underPaint);
        canvas.drawText(String.valueOf(progress), realWidth/2, realHeight/2, textPaint);
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
