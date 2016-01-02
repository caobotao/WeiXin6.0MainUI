package com.cbt.weixin6.defined_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.cbt.weixin6.R;

/**
 * Created by caobotao on 16/1/2.
 */
public class ChangeColorIconWithText extends View {
    /**
     * 自定义View步骤：
     * 1、创建attr.xml
     * 2、布局文件中使用
     * 3、构造方法中获取自定义属性
     * 4、重写onMeasure()
     * 5、重写onDraw()
     */

    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private float mAlpha;
    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    public ChangeColorIconWithText(Context context) {
        this(context,null);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /* 自定义属性第三步：在构造方法中获取自定义属性 */
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i ++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = (int) a.getDimension(attr,
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        /* 自定义属性第三步：在构造方法中获取自定义属性 */


        mTextBound = new Rect();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xFF45C01A);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }


    /* 自定义属性第四步：重写onMeasure() */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                                 getMeasuredHeight() - getPaddingTop() - getPaddingBottom()
                                  - mTextBound.height());
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth) / 2;

        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
//        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
//                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
//                - getPaddingBottom() - mTextBound.height());
//
//        int left = getMeasuredWidth() / 2 - iconWidth / 2;
//        int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth) / 2;
//        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }
    /* 自定义属性第四步：重写onMeasure() */


    /* 自定义属性第四步：重写onDraw() */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        int alpha = (int) Math.ceil(255 * mAlpha);

        //内存去准备mBitmap -》setAlpha -》设置纯色 -》设置xfermode -》设置图标
        setupTargetBitmap(alpha);

        //1.绘制原文本
        drawSourceText(canvas,alpha);

        //2.绘制变色的文本
        drawTargetText(canvas,alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    //绘制变色的文本
    private void drawTargetText(Canvas canvas, int alpha) {
//        mTextPaint.setColor(mColor);
//        mTextPaint.setAlpha(alpha);
//        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
//        int y = mIconRect.bottom + mTextBound.height();
//        canvas.drawText(mText, x, y, mTextPaint);
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    //绘制原文本
    private void drawSourceText(Canvas canvas, int alpha) {
//        mTextPaint.setColor(0xff333333);
//        mTextPaint.setAlpha(255 - alpha);
//        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
//        int y = mIconRect.bottom + mTextBound.height();
//        canvas.drawText(mText, x, y, mTextPaint);
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    //在内存中绘制可变色的Icon
    private void setupTargetBitmap(int alpha) {
//        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
//
//        mPaint = new Paint();
//        mPaint.setColor(mColor);
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setAlpha(alpha);
//        mCanvas.drawRect(mIconRect, mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
//        mPaint.setAlpha(255);
//        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);

    }
    /* 自定义属性第四步：重写onDraw() */

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    //重绘
    private void invalidateView() {
        //UI线程
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {//非UI线程
            postInvalidate();
        }
    }


    /*  防止出现Activity被回收时出现带颜色的Tab与Fragment不对应的情况  */


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
