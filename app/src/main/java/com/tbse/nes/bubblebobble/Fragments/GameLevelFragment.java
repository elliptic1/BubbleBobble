package com.tbse.nes.bubblebobble.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tbse.nes.bubblebobble.DrawSurface;
import com.tbse.nes.bubblebobble.GameThread;
import com.tbse.nes.bubblebobble.R;
import com.tbse.nes.bubblebobble.things.BubbleShot;
import com.tbse.nes.bubblebobble.characters.Enemy;
import com.tbse.nes.bubblebobble.characters.EnemyFactory;
import com.tbse.nes.bubblebobble.characters.Player;
import com.tbse.nes.bubblebobble.controls.AButton;
import com.tbse.nes.bubblebobble.controls.BButton;
import com.tbse.nes.bubblebobble.controls.Dpad;
import com.tbse.nes.bubblebobble.things.Fruit;

import java.util.ArrayList;

/**
 * Created by tbsmith on 7/18/14.
 */
public class GameLevelFragment extends Fragment implements View.OnTouchListener {

    private GameThread mGameThread;
    private DrawSurface mGameScreen;

    public static Player player;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<BubbleShot> bubbleShots;
    public static ArrayList<Fruit> fruits;

    public static AButton aButton;
    public static BButton bButton;

    public static Dpad dpad;

    private MediaPlayer music;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View levelView = inflater.inflate(R.layout.game_main, container, false);

        levelView.setOnTouchListener(this);

        context = getActivity().getApplicationContext();

        mGameScreen = (DrawSurface) levelView.findViewById(R.id.svGameScreen);

        player = new Player(300, 300, 120, getActivity().getApplicationContext());

        fruits = new ArrayList<Fruit>();

        enemies = new ArrayList<Enemy>();
        enemies.add(new EnemyFactory().getEnemy(1400, 400, context));
        enemies.add(new EnemyFactory().getEnemy(700, 200, context));
        enemies.add(new EnemyFactory().getEnemy(600, 100, context));
        enemies.add(new EnemyFactory().getEnemy(800, 300, context));

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

        bubbleShots = new ArrayList<BubbleShot>();

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

        music = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.main);
        music.setLooping(true);
        music.start();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        music.stop();
        music.release();

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

    public void update(float dt)
    {
        // Player
        player.update(dt);

        // Enemy
        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }

        // Bubbles
        for (BubbleShot bubbleShot : bubbleShots) {
            bubbleShot.update();
        }

        // Removed popped bubbles, add fruits
        int origSize = bubbleShots.size();
        for (int i=origSize; i>0; i--) {
            if (bubbleShots.get(i-1).shouldPop == true) {
                enemies.remove(bubbleShots.get(i-1).trappedEnemy);
                fruits.add(new Fruit(bubbleShots.get(i-1).getDimensions().centerX(),
                        bubbleShots.get(i-1).getDimensions().centerY(),
                        130, context
                        ));
                bubbleShots.remove(i-1);
            }

        }

        // Fruits
        for (Fruit fruit : fruits) {
            fruit.update(dt);
        }

        // Remove eaten fruits
        origSize = fruits.size();
        for (int i=origSize; i>0; i--) {
            if (fruits.get(i-1).hasBeenEaten) {
                fruits.remove(i-1);
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

//        Log.d("bb", "pointer count: " + event.getPointerCount());

        for (int i=0; i<event.getPointerCount(); i++) {

            float x = event.getX(i);
            float y = event.getY(i);
//            Log.d("bb", "touched at " + (int)x + ", " + (int)y);

            int action = event.getActionMasked();

            int simplifiedAction = 0;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    simplifiedAction = MotionEvent.ACTION_DOWN;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    simplifiedAction = MotionEvent.ACTION_UP;
                    break;
                default:
                    simplifiedAction = event.getAction();

            }


            if (aButton.getDimensions().contains((int) x, (int) y)) {
                aButton.onTouch(simplifiedAction);
            } else if (bButton.getDimensions().contains((int) x, (int) y)) {
                bButton.onTouch(simplifiedAction);
            } else if (dpad.getDimensions().contains((int) x, (int) y)) {
                dpad.onTouch((int)x, (int)y, simplifiedAction);
            }

        }

        return true;
    }
}
