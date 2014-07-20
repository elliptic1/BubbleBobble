package com.tbse.nes.bubblebobble.controls;

import android.content.Context;
import android.view.MotionEvent;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;
import com.tbse.nes.bubblebobble.characters.Character;
import com.tbse.nes.bubblebobble.things.BubbleShot;

/**
 * Created by tbsmith on 7/19/14.
 */
public class BButton extends Button {

    private Context context;
    public BButton(int x, int y, int width, Context context) {
        super(x, y, width, context);
        this.context = context;
    }

    public void onTouch(int action) {

        if (action == MotionEvent.ACTION_DOWN) {
//            Log.d("bb", "shooting");
            getPlayer().currentCharacterState = Character.CharacterState.SHOOTING;
            GameLevelFragment.bubbleShots.add(new BubbleShot(
                    getPlayer().getDimensions().centerX(),
                    getPlayer().getDimensions().centerY()-40, 100,
                    getPlayer().isFacingRight,
                    context));
        } else
        if (action == MotionEvent.ACTION_UP) {
            getPlayer().currentCharacterState = Character.CharacterState.STANDING;
        }
    }
}
