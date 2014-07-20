package com.tbse.nes.bubblebobble.controls;

import android.content.Context;
import android.view.MotionEvent;

import com.tbse.nes.bubblebobble.characters.Character;

/**
 * Created by tbsmith on 7/19/14.
 */
public class AButton extends Button {
    public AButton(int x, int y, int width, Context context) {
        super(x, y, width, context);
    }

    int startingHeight = 0;
    public void onTouch(int action) {

//        Log.d("bb", ": " + (startingHeight - getPlayer().getDimensions().centerY()));

        if (action == MotionEvent.ACTION_DOWN && getPlayer().isGrounded == true) {
            getPlayer().startingHeight = getPlayer().getDimensions().centerY();
            getPlayer().currentCharacterState = Character.CharacterState.JUMPING;
            getPlayer().isGrounded = false;
        } else
        if (action == MotionEvent.ACTION_UP) {
            getPlayer().currentCharacterState = Character.CharacterState.STANDING;
        }
    }
}
