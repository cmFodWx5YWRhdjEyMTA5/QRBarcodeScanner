package com.example.framgiadoanmanhtuan.myandroidqrbarcodescanner.cameratool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.example.framgiadoanmanhtuan.myandroidqrbarcodescanner.R;
import me.dm7.barcodescanner.core.DisplayUtils;
import me.dm7.barcodescanner.core.IViewFinder;

public class MyViewFinderView extends View implements IViewFinder {
    private static final String TAG = "ViewFinderView";
    private Rect mFramingRect;
    private static final float PORTRAIT_WIDTH_RATIO = 0.75F;
    private static final float PORTRAIT_WIDTH_HEIGHT_RATIO = 0.75F;
    private static final float LANDSCAPE_HEIGHT_RATIO = 0.625F;
    private static final float LANDSCAPE_WIDTH_HEIGHT_RATIO = 1.4F;
    private static final int MIN_DIMENSION_DIFF = 50;
    private static final float SQUARE_DIMENSION_RATIO = 0.625F;
    private static final int[] SCANNER_ALPHA = new int[]{0, 64, 128, 192, 255, 192, 128, 64};
    private int scannerAlpha;
    private static final int POINT_SIZE = 10;
    private static final long ANIMATION_DELAY = 80L;
    private final int mDefaultLaserColor;
    private final int mDefaultMaskColor;
    private final int mDefaultBorderColor;
    private final int mDefaultBorderStrokeWidth;
    private final int mDefaultBorderLineLength;
    protected Paint mLaserPaint;
    protected Paint mFinderMaskPaint;
    protected Paint mBorderPaint;
    protected int mBorderLineLength;
    protected boolean mSquareViewFinder;

    public MyViewFinderView(Context context) {
        super(context);
        this.mDefaultLaserColor = this.getResources().getColor(R.color.laser);
        this.mDefaultMaskColor = this.getResources().getColor(R.color.mask);
        this.mDefaultBorderColor = this.getResources().getColor(R.color.border);
        this.mDefaultBorderStrokeWidth = this.getResources().getInteger(
                me.dm7.barcodescanner.core.R.integer.viewfinder_border_width);
        this.mDefaultBorderLineLength = this.getResources().getInteger(
                me.dm7.barcodescanner.core.R.integer.viewfinder_border_length);
        this.init();
    }

    public MyViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDefaultLaserColor = this.getResources().getColor(R.color.laser);
        this.mDefaultMaskColor = this.getResources().getColor(R.color.mask);
        this.mDefaultBorderColor = this.getResources().getColor(R.color.border);
        this.mDefaultBorderStrokeWidth = this.getResources().getInteger(
                me.dm7.barcodescanner.core.R.integer.viewfinder_border_width);
        this.mDefaultBorderLineLength = this.getResources().getInteger(
                me.dm7.barcodescanner.core.R.integer.viewfinder_border_length);
        this.init();
    }

    private void init() {
        this.mLaserPaint = new Paint();
        this.mLaserPaint.setColor(this.mDefaultLaserColor);
        this.mLaserPaint.setStyle(Paint.Style.FILL);
        this.mFinderMaskPaint = new Paint();
        this.mFinderMaskPaint.setColor(this.mDefaultMaskColor);
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setColor(this.mDefaultBorderColor);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setStrokeWidth((float)this.mDefaultBorderStrokeWidth);
        this.mBorderLineLength = this.mDefaultBorderLineLength;
    }

    public void setLaserCviolor(int laserColor) {
        this.mLaserPaint.setColor(laserColor);
    }

    @Override
    public void setLaserColor(int i) {

    }

    public void setMaskColor(int maskColor) {
        this.mFinderMaskPaint.setColor(maskColor);
    }

    public void setBorderColor(int borderColor) {
        this.mBorderPaint.setColor(borderColor);
    }

    public void setBorderStrokeWidth(int borderStrokeWidth) {
        this.mBorderPaint.setStrokeWidth((float)borderStrokeWidth);
    }

    public void setBorderLineLength(int borderLineLength) {
        this.mBorderLineLength = borderLineLength;
    }

    @Override
    public void setLaserEnabled(boolean b) {

    }

    @Override
    public void setBorderCornerRounded(boolean b) {

    }

    @Override
    public void setBorderAlpha(float v) {

    }

    @Override
    public void setBorderCornerRadius(int i) {

    }

    @Override
    public void setViewFinderOffset(int i) {

    }

    public void setSquareViewFinder(boolean set) {
        this.mSquareViewFinder = set;
    }

    public void setupViewFinder() {
        this.updateFramingRect();
        this.invalidate();
    }

    public Rect getFramingRect() {
        return this.mFramingRect;
    }

    public void onDraw(Canvas canvas) {
        if (this.getFramingRect() != null) {
            this.drawViewFinderMask(canvas);
            this.drawViewFinderBorder(canvas);
            this.drawLaser(canvas);
        }
    }

    public void drawViewFinderMask(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Rect framingRect = this.getFramingRect();
        canvas.drawRect(0.0F, 0.0F, (float)width, (float)framingRect.top, this.mFinderMaskPaint);
        canvas.drawRect(0.0F, (float)framingRect.top, (float)framingRect.left, (float)(framingRect.bottom + 1), this.mFinderMaskPaint);
        canvas.drawRect((float)(framingRect.right + 1), (float)framingRect.top, (float)width, (float)(framingRect.bottom + 1), this.mFinderMaskPaint);
        canvas.drawRect(0.0F, (float)(framingRect.bottom + 1), (float)width, (float)height, this.mFinderMaskPaint);
    }

    public void drawViewFinderBorder(Canvas canvas) {
        Rect framingRect = this.getFramingRect();
        canvas.drawLine((float)(framingRect.left - 1), (float)(framingRect.top - 1), (float)(framingRect.left - 1), (float)(framingRect.top - 1 + this.mBorderLineLength), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.left - 1), (float)(framingRect.top - 1), (float)(framingRect.left - 1 + this.mBorderLineLength), (float)(framingRect.top - 1), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.left - 1), (float)(framingRect.bottom + 1), (float)(framingRect.left - 1), (float)(framingRect.bottom + 1 - this.mBorderLineLength), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.left - 1), (float)(framingRect.bottom + 1), (float)(framingRect.left - 1 + this.mBorderLineLength), (float)(framingRect.bottom + 1), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.right + 1), (float)(framingRect.top - 1), (float)(framingRect.right + 1), (float)(framingRect.top - 1 + this.mBorderLineLength), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.right + 1), (float)(framingRect.top - 1), (float)(framingRect.right + 1 - this.mBorderLineLength), (float)(framingRect.top - 1), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.right + 1), (float)(framingRect.bottom + 1), (float)(framingRect.right + 1), (float)(framingRect.bottom + 1 - this.mBorderLineLength), this.mBorderPaint);
        canvas.drawLine((float)(framingRect.right + 1), (float)(framingRect.bottom + 1), (float)(framingRect.right + 1 - this.mBorderLineLength), (float)(framingRect.bottom + 1), this.mBorderPaint);
    }

    public void drawLaser(Canvas canvas) {
        Rect framingRect = this.getFramingRect();
        this.mLaserPaint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
        this.scannerAlpha = (this.scannerAlpha + 1) % SCANNER_ALPHA.length;
        int middle = framingRect.height() / 2 + framingRect.top;
        canvas.drawRect((float)(framingRect.left + 2), (float)(middle - 1), (float)(framingRect.right - 1), (float)(middle + 2), this.mLaserPaint);
        this.postInvalidateDelayed(80L, framingRect.left - 10, framingRect.top - 10, framingRect.right + 10, framingRect.bottom + 10);
    }

    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        this.updateFramingRect();
    }

    public synchronized void updateFramingRect() {
        Point viewResolution = new Point(this.getWidth(), this.getHeight());
        int orientation = DisplayUtils.getScreenOrientation(this.getContext());
        int width;
        int height;
        if (this.mSquareViewFinder) {
            if (orientation != 1) {
                height = (int)((float)this.getHeight() * 0.625F);
                width = height;
            } else {
                width = (int)((float)this.getWidth() * 0.625F);
                height = width;
            }
        } else if (orientation != 1) {
            height = (int)((float)this.getHeight() * 0.625F);
            width = (int)(1.4F * (float)height);
        } else {
            width = (int)((float)this.getWidth() * 0.75F);
            height = (int)(0.75F * (float)width);
        }

        if (width > this.getWidth()) {
            width = this.getWidth() - 50;
        }

        if (height > this.getHeight()) {
            height = this.getHeight() - 50;
        }

        int leftOffset = (viewResolution.x - width) / 2;
        int topOffset = (viewResolution.y - height) / 2;
        this.mFramingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
    }
}
