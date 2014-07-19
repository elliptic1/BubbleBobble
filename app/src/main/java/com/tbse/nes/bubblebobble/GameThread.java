package com.tbse.nes.bubblebobble;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;


public class GameThread extends Thread
{
    private GameLevelFragment  mMain;
    private DrawSurface   mSurface;
    private SurfaceHolder mSurfaceHolder;
    private boolean       mRun = false;

    public GameThread(GameLevelFragment gameLevelFragment, DrawSurface surface)
    {
        mMain = gameLevelFragment;
        mSurface = surface;
        mSurfaceHolder = surface.getHolder();
    }

    public void setRunning(boolean run)
    {
        mRun = run;
    }

    @Override
    public void run()
    {
        Canvas c;

        float  maxfps   = 1 / 60.0f;
        double currtime = ((double)System.nanoTime()) / 1e9;

        while (mRun)
        {
            c = null;

            try
            {
                c = mSurfaceHolder.lockCanvas(null);

                synchronized (mSurfaceHolder)
                {
                    double newtime   = ((double)System.nanoTime()) / 1e9;
                    double frametime = newtime - currtime;
                    currtime = newtime;

                    while (frametime > 0)
                    {
                        float dt = Math.min((float)frametime, maxfps);

                        // update
                        mMain.update(dt);

                        frametime -= dt;
                    }

                    // draw
                    mSurface.postInvalidate();
                }
            } catch (IllegalStateException ise) {

            }
            finally
            {
                if (c != null)
                {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}