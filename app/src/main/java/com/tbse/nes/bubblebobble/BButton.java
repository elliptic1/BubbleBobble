package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by tbsmith on 7/19/14.
 */
public class BButton extends Button {
    public BButton(int x, int y, int width, Context context) {
        super(x, y, width, context);
    }

    public void onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("bb", "Shoot");
        }
    }
}