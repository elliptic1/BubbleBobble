package com.tbse.nes.bubblebobble.Fragments;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tbse.nes.bubblebobble.AButton;
import com.tbse.nes.bubblebobble.BButton;
import com.tbse.nes.bubblebobble.Dpad;
import com.tbse.nes.bubblebobble.DrawSurface;
import com.tbse.nes.bubblebobble.GameThread;
import com.tbse.nes.bubblebobble.Player;
import com.tbse.nes.bubblebobble.R;

/**
 * Created by tbsmith on 7/18/14.
 */
public class GameLevelFragment extends Fragment implements View.OnTouchListener {

    private GameThread mGameThread;
    private DrawSurface mGameScreen;

    private Player player;

    public static AButton aButton;
    public static BButton bButton;

    public static Dpad dpad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View levelView = inflater.inflate(R.layout.game_main, container, false);

        levelView.setOnTouchListener(this);

        mGameScreen = (DrawSurface) levelView.findViewById(R.id.svGameScreen);

        player = new Player(300, 300, getActivity().getApplicationContext());

        aButton = new AButton(
                container.getWidth()-225,
                container.getHeight()-400,
                200,
                getActivity().getApplicationContext());
        bButton = new BButton(
                container.getWidth()-500,
                container.getHeight()-250,
                200,
                getActivity().getApplicationContext());
        dpad = new Dpad(
                15,
                container.getHeight()-10-300,
                300,
                getActivity().getApplicationContext());


        return levelView;

    }

    @Override
    public void onResume() {
        super.onResume();

        int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();

        if ( ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions)) {
        } else {
            Log.i("bb", "Turning immersive mode mode on.");
            uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        if ( ((uiOptions | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == uiOptions)) {
        } else {
            Log.i("bb", "Turning hide nav mode mode on.");
            uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if ( ((uiOptions | View.SYSTEM_UI_FLAG_FULLSCREEN) == uiOptions)) {
        } else {
            Log.i("bb", "Turning fullscreen mode mode on.");
            uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        getActivity().getWindow().getDecorView().setSystemUiVisibility(uiOptions);

    }


    public void startGame()
    {
        mGameThread = new GameThread(this, mGameScreen);
        mGameThread.setRunning(true);
        mGameThread.start();
    }


    @Override
    public void onStop()
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

    public Rect getScreenDimensions()
    {
        return mGameScreen.getDimensions();
    }

    public void update(float dt)
    {
        player.update(dt);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (aButton.getDimensions().contains((int)event.getRawX(), (int)event.getRawY())) {
            aButton.onTouch(event);
        }
        if (bButton.getDimensions().contains((int)event.getRawX(), (int)event.getRawY())) {
            bButton.onTouch(event);
        }
        if (dpad.getDimensions().contains((int)event.getRawX(), (int)event.getRawY())) {
            dpad.onTouch(event);
        }

        return false;
    }
}
