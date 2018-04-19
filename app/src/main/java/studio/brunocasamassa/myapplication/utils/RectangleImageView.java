package studio.brunocasamassa.myapplication.utils;

/**
 * Created by bruno on 19/04/2018.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import studio.brunocasamassa.myapplication.R;


public class RectangleImageView extends android.support.v7.widget.AppCompatImageView {

    public float radius = 0.0f;

    public RectangleImageView(Context context) {
        this(context, null);
    }

    public RectangleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectangleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecycleListView, 0, 0);
        radius = a.getDimension(R.styleable.RectangleImageView_roundedRadius, 10.f);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float v = getMeasuredWidth() * 1.5f;
        setMeasuredDimension(getMeasuredWidth(), (int) v);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 36.0f;
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}

