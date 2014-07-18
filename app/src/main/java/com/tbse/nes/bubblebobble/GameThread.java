package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


class GameThread extends Thread
{
    private ActivityMain  mMain;
    private DrawSurface   mSurface;
    private SurfaceHolder mSurfaceHolder;
    private boolean       mRun = false;

    public GameThread(Context context, DrawSurface surface)
    {
        mMain = (ActivityMain)context;
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