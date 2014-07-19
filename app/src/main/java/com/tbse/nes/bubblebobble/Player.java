package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by tbsmith on 7/18/14.
 */
public class Player {

    private Bitmap currentBitmap;

    private Bitmap[] standing;
    private Bitmap[] walking;
    private Bitmap[] shooting;
    private Bitmap[] dieing;

    public static enum PlayerState {STANDING, WALKING, SHOOTING, DIEING, JUMPING};
    public static PlayerState currentPlayerState = PlayerState.STANDING;

    private float totalGameTime = 0;
    public static boolean isGrounded = false;

    private int fps = 4;

    public Player(int x, int y, Context context) {

        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk01);

        standing = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk01),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk02),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk03),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk04),
        };

        walking = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk01),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk02),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk03),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk04),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk05),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk06),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk07),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk08)
        };

        shooting = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot01)
        };

        dieing = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die01),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die02),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die03),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die04)
        };

    }

    public void update(float dt) {
        totalGameTime += dt;



    }

}
