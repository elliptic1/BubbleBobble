package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;


public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private GameLevelFragment mMain;
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
        mMain = (GameLevelFragment) ActivityMain.fragments.get(1);

        getHolder().addCallback(this);
//        setOnTouchListener(this);
        setWillNotDraw(false);

        mBMPField = BitmapFactory.decodeResource(getResources(), R.drawable.map1);

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
        // board
        c.drawBitmap(mBMPField, null, mDimensions, null);

        // dpad
        GameLevelFragment.dpad.draw(c);

        // buttons
        GameLevelFragment.aButton.draw(c);
        GameLevelFragment.bButton.draw(c);

    }

}
