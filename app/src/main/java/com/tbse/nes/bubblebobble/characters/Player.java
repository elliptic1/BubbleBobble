package com.tbse.nes.bubblebobble.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.tbse.nes.bubblebobble.R;
import com.tbse.nes.bubblebobble.controls.Dpad;

import java.util.HashMap;

/**
 * Created by tbsmith on 7/18/14.
 */
public class Player extends Character {

    private Rect dimensions;
    private Context context;

    public Player(int x, int y, int size, Context context) {

        this.context = context;

        dimensions = new Rect(x, y, x + size, y + size);

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

        falling = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.walk01)
        };

        dieing = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die01),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die02),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die03),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.die04)
        };

        animationLength = new HashMap<CharacterState, Integer>();
        animationLength.put(CharacterState.WALKING, 8);
        animationLength.put(CharacterState.SHOOTING, 1);
        animationLength.put(CharacterState.STANDING, 4);
        animationLength.put(CharacterState.DIEING, 4);
        animationLength.put(CharacterState.JUMPING, 1);
        animationLength.put(CharacterState.FALLING, 1);

    }

    public int startingHeight = 0;
    public void update(float dt) {
        super.update(dt);

        // move left or right no matter what's happening
         if (Dpad.currentDirection == Dpad.Direction.RIGHT && getDimensions().centerX() < 1750
                || Dpad.currentDirection == Dpad.Direction.LEFT && getDimensions().centerX() > 150)

            getDimensions().offset((Dpad.currentDirection == Dpad.Direction.RIGHT ? 1 : -1) * 4, 0);

        // set max jump height
        if (currentCharacterState == CharacterState.JUMPING) {
            if (startingHeight - getDimensions().centerY() > 300) {
                currentCharacterState = CharacterState.STANDING;
            }
        }



    }

    @Override
    public Rect getDimensions() {
        return dimensions;
    }


}
