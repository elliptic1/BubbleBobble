package com.tbse.nes.bubblebobble.things;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;
import com.tbse.nes.bubblebobble.R;
import com.tbse.nes.bubblebobble.characters.Enemy;
import com.tbse.nes.bubblebobble.controls.Dpad;

/**
 * Created by tbsmith on 7/20/14.
 */
public class BubbleShot {

    private Bitmap bitmap;
    private Rect dimensions;

    private Dpad.Direction direction;

    public boolean poppable = false;
    public boolean shouldPop = false;

    private MediaPlayer bubbleSound;

    private int bubbleSpeed = 9;
    public Enemy trappedEnemy = null;

    public BubbleShot(int x, int y, int width, boolean isFacingRight, Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubbleshot);
        dimensions = new Rect(x, y, x+width, y+width);
        direction = isFacingRight ? Dpad.Direction.RIGHT : Dpad.Direction.LEFT;
        bubbleSound = MediaPlayer.create(context, R.raw.shoot);
        bubbleSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                bubbleSound.stop();
                bubbleSound.reset();
                bubbleSound.release();
            }
        });
        bubbleSound.start();
    }

    public void draw(Canvas c) {
        c.drawBitmap(getBitmap(), null, getDimensions(), null);
    }


    public void update() {

        if (poppable && GameLevelFragment.player.getDimensions().contains(
                getDimensions().centerX(), getDimensions().centerY())) {

                shouldPop = true;
                return;

        }

        // check if bubble has hit any enemies
        if (trappedEnemy == null) {
            for (Enemy enemy : GameLevelFragment.enemies) {
                if (enemy.getDimensions().contains(getDimensions().centerX(), getDimensions().centerY())) {
                    enemy.isInBubble = true;
                    trappedEnemy = enemy;
                    poppable = true;
                }
            }
        }


        if (getDimensions().centerY() < 150) {

            if (getDimensions().centerX() < 900) {
                dimensions.offset(bubbleSpeed, 0);
            } else {
                dimensions.offset(-bubbleSpeed, 0);
            }

        } else {

            switch (direction) {

                case UP:
                    dimensions.offset(0, -bubbleSpeed);
                    break;
                case LEFT:
                    if (dimensions.centerX() < 150) {
                        dimensions.offset(0, -bubbleSpeed);
                    } else {
                        dimensions.offset(-bubbleSpeed, 0);
                    }
                    break;
                case RIGHT:
                    if (dimensions.centerX() > 1750) {
                        dimensions.offset(0, -bubbleSpeed);
                    } else {
                        dimensions.offset(bubbleSpeed, 0);
                    }
                    break;
            }

        }

        if (trappedEnemy != null) {
            trappedEnemy.getDimensions().set(getDimensions());
        }

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Rect getDimensions() {

        return dimensions;
    }



}
