package com.tbse.nes.bubblebobble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by tbsmith on 7/18/14.
 */
public class Dpad {

    private Bitmap dpad;

    public Rect dimensions;

    public Dpad(int x, int y, int width, Context context) {
        dpad = BitmapFactory.decodeResource(context.getResources(), R.drawable.dpad);
        dimensions = new Rect(x, y, x + width, y + width);
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
                    event.getRawX() >= getDimensions().left + getDimensions().width() / 3
                            &&
                            event.getRawX() <= getDimensions().right - getDimensions().width() / 3
                    ) {

                if (
                        event.getRawY() <= getDimensions().top + getDimensions().height() / 3
                        ) {
                    handleUp();
                } else if (
                        event.getRawY() >= getDimensions().bottom - getDimensions().height() / 3
                        ) {
                    handleDown();
                }
            }


            if (
                    event.getRawY() >= getDimensions().top + getDimensions().height() / 3
                            &&
                            event.getRawY() <= getDimensions().bottom - getDimensions().height() / 3

                    ) {


                if (event.getRawX() < getDimensions().left + getDimensions().height() / 3) {
                    handleLeft();
                } else if (event.getRawX() > getDimensions().right - getDimensions().height() / 3) {
                    handleRight();
                }


            }

        }
    }

    private void handleUp() {
        if (Player.isGrounded) {
            Player.currentPlayerState = Player.PlayerState.STANDING;
        }
    }

    private void handleDown() {
        if (Player.isGrounded) {
            Player.currentPlayerState = Player.PlayerState.STANDING;
        }
    }

    private void handleLeft() {
        if (Player.isGrounded) {
            Player.currentPlayerState = Player.PlayerState.WALKING;
        }
    }

    private void handleRight() {
        if (Player.isGrounded) {
            Player.currentPlayerState = Player.PlayerState.WALKING;
        }
    }

}
