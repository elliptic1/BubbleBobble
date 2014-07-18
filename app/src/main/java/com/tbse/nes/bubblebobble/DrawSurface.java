package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;


public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener
{
    private ActivityMain    mMain;
    private SurfaceHolder   mHolder;
    private Rect            mDimensions;

    private Bitmap          mBMPField;

    public DrawSurface(Context context)
    {
        super(context);
        initialize();
    }

    public DrawSurface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }

    public DrawSurface(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize()
    {
        mMain = (ActivityMain)getContext();

        getHolder().addCallback(this);
        setOnTouchListener(this);
        setWillNotDraw(false);

        mBMPField = BitmapFactory.decodeResource(getResources(), R.drawable.field);

        mDimensions = new Rect();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mHolder = holder;

        // get true screen dimensions
        Canvas c = mHolder.lockCanvas();
        mDimensions.set(0,0,c.getWidth(),c.getHeight());
        mHolder.unlockCanvasAndPost(c);

        mMain.startGame();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    public Rect getDimensions()
    {
        return mDimensions;
    }

    @Override
    public void onDraw(Canvas c)
    {
        // field
        c.drawBitmap(mBMPField, null, mDimensions, null);

        // entities
        EntityBall b = mMain.getBall();
        c.drawBitmap(b.getImage(), null, b.getDimensions(), null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            Point p = new Point((int)e.getX(), (int)e.getY());

            Toast.makeText(this.getContext(), "do something", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
