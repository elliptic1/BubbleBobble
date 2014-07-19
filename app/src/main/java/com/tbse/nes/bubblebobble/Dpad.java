package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by tbsmith on 7/18/14.
 */
public class Dpad {

    private Bitmap dpad;

    public Rect dimensions;

    public Dpad (int x, int y, int width, Context context) {
        dpad = BitmapFactory.decodeResource(context.getResources(), R.drawable.dpad);
        dimensions = new Rect(x, y, x+width, y+width);
    }

    public void draw(Canvas c) {
        c.drawBitmap(getBitmap(), null, getDimensions(), null);
    }

    public Bitmap getBitmap() {
        return dpad;
    }

    public Rect getDimensions() {
        return dimensions;
    }

    public void onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {

            // ignore corners

            if (
                event.getRawX() >= getDimensions().left + getDimensions().width()/3
                    &&
                event.getRawX() <= getDimensions().right - getDimensions().width()/3
                    // in middle vertical third
                ||
                    // in middle horizontal third
                event.getRawY() >= getDimensions().top + getDimensions().height()/3
                    &&
                event.getRawY() <= getDimensions().bottom - getDimensions().height()/3

                ) {

                Log.d("bb", "touch dpad at " + event.getRawX() + ", " + event.getRawY());

            }

        }
    }

}
