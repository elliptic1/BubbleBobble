package com.tbse.nes.bubblebobble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class ActivitySplashscreen extends Activity
{
    private final int DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splashscreen);

        // anonymous handler to start the main activity and close splashscreen
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // create an Intent that will start the main activity
                Intent mainIntent = new Intent(ActivitySplashscreen.this, ActivityMain.class);
                ActivitySplashscreen.this.startActivity(mainIntent);
                ActivitySplashscreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                ActivitySplashscreen.this.finish();
            }

        }, DISPLAY_LENGTH);
    }
}