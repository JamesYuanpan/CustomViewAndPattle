package com.dingmouren.paletteimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

public class BigView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
        ScaleGestureDetector.OnScaleGestureListener {
    private float mImageWidth, mImageHeight;
    private float mViewWidth, mViewHeight;
    private float mScale = 1;
    private float mCurrentScale = 1;
    private int mMultiple = 3;
    private final Rect mRect = new Rect();

    private BitmapRegionDecoder mRegionDecoder;
    private BitmapFactory.Options mOptions;

    private Bitmap mBitmap;

    private Scroller mScroller;

    private Matrix mMatrix;

    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    public BigView(Context context) {
        super(context);
        init();
    }

    public BigView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mOptions = new BitmapFactory.Options();
        mScroller = new Scroller(getContext());
        mMatrix = new Matrix();
        mGestureDetector = new GestureDetector(getContext(), this);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
    }

    public void setImage(InputStream is) {
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOptions);
        mImageHeight = mOptions.outHeight;
        mImageWidth = mOptions.outWidth;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        try {
            mRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mRect.top = 0;
        mRect.left = 0;
        mRect.right = (int) mViewWidth;
        mRect.bottom = (int) mViewHeight;
        mScale = mViewWidth / mImageWidth;
        mCurrentScale = mScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRegionDecoder == null) {
            return;
        }
        mOptions.inBitmap = mBitmap;
        mBitmap = mRegionDecoder.decodeRegion(mRect, mOptions);
        mMatrix.setScale(mCurrentScale, mCurrentScale);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (mCurrentScale > mScale) {
            mCurrentScale = mScale;
        } else {
            mCurrentScale = mScale * mMultiple;
        }
        mRect.right = mRect.left + (int) (mViewWidth / mCurrentScale);
        mRect.bottom = mRect.top + (int) (mViewHeight / mCurrentScale);
        handleBorder();
        invalidate();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset((int) distanceX, (int) distanceY);
        handleBorder();
        invalidate();
        return false;
    }

    private void handleBorder() {
        if (mRect.left < 0) {
            mRect.left = 0;
            mRect.right = (int) (mViewWidth / mCurrentScale);
        }
        if (mRect.right > mImageWidth) {
            mRect.right = (int) mImageWidth;
            mRect.left = (int) (mImageWidth - mViewWidth / mCurrentScale);
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mCurrentScale);
        }
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = (int) mImageHeight;
            mRect.top = (int) (mImageHeight - mViewHeight / mCurrentScale);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            if (mRect.top + mViewHeight / mCurrentScale < mImageHeight) {
                mRect.top = mScroller.getCurrY();
                mRect.bottom = (int) (mRect.top + mViewHeight / mCurrentScale);
            }
            if (mRect.bottom > mImageHeight) {
                mRect.top = (int) (mImageHeight - mViewHeight / mCurrentScale);
                mRect.bottom = (int) mImageHeight;
            }
            invalidate();
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mRect.left, mRect.top, -(int) velocityX, -(int) velocityY, 0
            , (int) mImageWidth, 0 , (int) mImageHeight);
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        mCurrentScale *= scaleFactor;
        if (mCurrentScale > mScale * mMultiple) {
            mCurrentScale = mScale * mMultiple;
        } else if (mCurrentScale <= mScale){
            mCurrentScale = mScale;
        }
        mRect.right = mRect.left + (int) (mViewWidth / mCurrentScale);
        mRect.bottom = mRect.top + (int) (mViewHeight / mCurrentScale);
        invalidate();
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
