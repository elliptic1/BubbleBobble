package com.tbse.nes.bubblebobble;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Window;

import java.util.HashMap;


public class ActivityMain extends Activity
{
    private GameThread  mGameThread;
    private DrawSurface mGameScreen;

    private EntityBall  mBall;

    private HashMap<Integer, Fragment> fragments;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mGameScreen = (DrawSurface)findViewById(R.id.svGameScreen);

        mBall = new EntityBall(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        try
        {
            if (mGameThread != null)
            {
                mGameThread.setRunning(false);
                mGameThread.join();
            }
        }
        catch (Exception e) {}
    }

    public void initGame()
    {
        Rect r = getScreenDimensions();
        getBall().setPosition(r.width()>>1, r.height()>>1);
        getBall().setVelocity(300, 300);
    }

    public void startGame()
    {
        initGame();

        mGameThread = new GameThread(this, mGameScreen);
        mGameThread.setRunning(true);
        mGameThread.start();
    }

    public void update(float dt)
    {
        getBall().update(dt);
    }

    // this is hacky, should be a manager of entities
    public EntityBall getBall()
    {
        return mBall;
    }

    public Rect getScreenDimensions()
    {
        return mGameScreen.getDimensions();
    }
}
