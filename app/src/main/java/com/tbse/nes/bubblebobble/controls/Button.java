package com.tbse.nes.bubblebobble.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;
import com.tbse.nes.bubblebobble.R;
import com.tbse.nes.bubblebobble.characters.Player;

/**
 * Created by tbsmith on 7/18/14.
 */
public class Button {

    private Bitmap button;
    private Rect dimensions;

    public Button(int x, int y, int width, Context context) {
        button = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_button);

        dimensions = new Rect(x, y, x+width, y+width);

    }

    public Bitmap getBitmap() {
        return button;
    }

    public void draw(Canvas c) {
        c.drawBitmap(getBitmap(), null, getDimensions(), null);
    }

    public Rect getDimensions() {

        return dimensions;
    }

    public Player getPlayer() {
        return GameLevelFragment.player;
    }

}
