package com.tbse.nes.bubblebobble.things;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;
import com.tbse.nes.bubblebobble.R;
import com.tbse.nes.bubblebobble.characters.GravityUser;

/**
 * Created by tbsmith on 7/20/14.
 */
public class Fruit extends GravityUser {

    private Bitmap bitmap;
    private Rect dimensions;

    private MediaPlayer fruitSound;

    public boolean hasBeenEaten = false;

    public Fruit(int x, int y, int width, Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.banana);
        dimensions = new Rect(x, y, x+width, y+width);

        fruitSound = MediaPlayer.create(context, R.raw.getbanana);
        fruitSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                fruitSound.stop();
                fruitSound.reset();
                fruitSound.release();
            }
        });
    }

    public void update(float dt) {
        super.update(dt);

        if (!isGrounded) {
            getDimensions().offset(0, 9);
        }

        if (getDimensions().contains(GameLevelFragment.player.getDimensions().centerX(),
                GameLevelFragment.player.getDimensions().centerY())) {
           hasBeenEaten = true;

           fruitSound.start();
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void draw(Canvas c) {
        c.drawBitmap(getBitmap(), null, getDimensions(), null);
    }

    @Override
    public Rect getDimensions() {
        return dimensions;
    }
}
