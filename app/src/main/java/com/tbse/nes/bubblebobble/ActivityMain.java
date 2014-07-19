package com.tbse.nes.bubblebobble;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;
import com.tbse.nes.bubblebobble.Fragments.StartScreenFragment;

import java.util.HashMap;


public class ActivityMain extends Activity
{

   public static Typeface tf;

    public static HashMap<Integer, Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        tf = Typeface.createFromAsset(getAssets(), "fonts/prstart.ttf");

        fragments = new HashMap<Integer, Fragment>();

        fragments.put(0, new StartScreenFragment());
        fragments.put(1, new GameLevelFragment());

        setContentView(R.layout.blank_container);

        FrameLayout screen_layout = (FrameLayout) findViewById(R.id.mainFrameLayout);

        getFragmentManager().beginTransaction().add(screen_layout.getId(), fragments.get(0))
            .commit();

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();

        Log.d("bb", "setting hide nav and stuff");
        uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

   }

}
