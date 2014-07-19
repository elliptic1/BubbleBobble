package com.tbse.nes.bubblebobble;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;

public class EntityBall
{
    private GameLevelFragment mMain;
    private Bitmap       mImage;

    // replace with simple math library
    private float   mX;
    private float   mY;
    private float   mDX;
    private float   mDY;

    public EntityBall(GameLevelFragment gameLevelFragment)
    {
        mMain = gameLevelFragment;

        mImage = BitmapFactory.decodeResource(mMain.getResources(), R.drawable.bricks);
    }

    public void setPosition(int x, int y)
    {
        mX = (float)x;
        mY = (float)y;
    }

    public void setVelocity(int dx, int dy)
    {
        mDX = (float)dx;
        mDY = (float)dy;
    }

    public Rect getDimensions()
    {
        // optimize this to not create object and use precompute math
        return new Rect((int)mX - (mImage.getWidth()>>1), (int)mY - (mImage.getWidth()>>1), (int)mX + (mImage.getWidth()>>1), (int)mY + (mImage.getHeight()>>1));
    }

    public Bitmap getBitmap()
    {
        return mImage;
    }

    public void update(float dt)
    {
        mX += mDX * dt;
        mY += mDY * dt;

        // ideally this should be in some manager
        Rect r = mMain.getScreenDimensions();

        // hack collision
        if (mX < -1)
        {
            mX = 0;
            mDX *= -1f;
        }
        else if (mX > r.width()+1)
        {
            mX = r.width();
            mDX *= -1f;
        }
        else if (mY < -1)
        {
            mY = 0;
            mDY *= -1f;
        }
        else if (mY > r.height()+1)
        {
            mY = r.height();
            mDY *= -1f;
        }
    }
}
