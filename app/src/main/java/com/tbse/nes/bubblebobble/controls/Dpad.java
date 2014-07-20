package com.tbse.nes.bubblebobble.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;
import com.tbse.nes.bubblebobble.R;
import com.tbse.nes.bubblebobble.characters.Character;
import com.tbse.nes.bubblebobble.characters.Player;

/**
 * Created by tbsmith on 7/18/14.
 */
public class Dpad {

    private Bitmap dpad;

    public Rect dimensions;

    public static enum Direction {LEFT, DOWN, RIGHT, UP, NONE};
    public static Direction currentDirection = Direction.NONE;

    private Player player;

    public Dpad(int x, int y, int width, Context context) {
        dpad = BitmapFactory.decodeResource(context.getResources(), R.drawable.dpad);
        dimensions = new Rect(x, y, x + width, y + width);
        player = GameLevelFragment.player;
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

    public void onTouch(int x, int y, int action) {
        if (action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_UP) {
//            Log.d("bb", "cancel || up");
            player.currentCharacterState = Character.CharacterState.STANDING;
            currentDirection = Direction.NONE;
        } else

        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE) {

            // ignore corners

            if (
                    x >= getDimensions().left + getDimensions().width() / 3
                            &&
                            x <= getDimensions().right - getDimensions().width() / 3
                    ) {

                if (
                        y <= getDimensions().top + getDimensions().height() / 3
                        ) {
                    handleUp();
                } else if (
                        y >= getDimensions().bottom - getDimensions().height() / 3
                        ) {
                    handleDown();
                }
            }


            if (
                    y >= getDimensions().top + getDimensions().height() / 3
                            &&
                            y <= getDimensions().bottom - getDimensions().height() / 3

                    ) {


                if (x < getDimensions().left + getDimensions().height() / 3) {
                    handleLeft();
                } else if (x > getDimensions().right - getDimensions().height() / 3) {
                    handleRight();
                }


            }

        }
    }

    private void handleUp() {
        currentDirection = Direction.UP;
        if (player.isGrounded) {
            player.currentCharacterState = Character.CharacterState.STANDING;
        }
    }

    private void handleDown() {
        currentDirection = Direction.DOWN;
        if (player.isGrounded) {
            player.currentCharacterState = Character.CharacterState.STANDING;
        }
    }

    private void handleLeft() {
        currentDirection = Direction.LEFT;
        player.isFacingRight = false;
        if (player.isGrounded) {
            player.currentCharacterState = Character.CharacterState.WALKING;
        }
    }

    private void handleRight() {
        currentDirection = Direction.RIGHT;
        player.isFacingRight = true;
        if (player.isGrounded) {
            player.currentCharacterState = Character.CharacterState.WALKING;
        }
    }

}
