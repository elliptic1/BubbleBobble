package com.tbse.nes.bubblebobble.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.tbse.nes.bubblebobble.R;

import java.util.HashMap;

/**
 * Created by tbsmith on 7/20/14.
 */
public class PurpleMonster extends Enemy {

    private Rect dimensions;

    public PurpleMonster(int x, int y, int size, Context context) {

        dimensions = new Rect(x, y, x + size, y + size);

        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.monster01);

        standing = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.monster01),
        };

        walking = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.monster01),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.monster02)
        };

        shooting = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.monster02)
        };

        falling = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.monster01)
        };

        dieing = new Bitmap[] {
                BitmapFactory.decodeResource(context.getResources(), R.drawable.monster01)
        };

        animationLength = new HashMap<CharacterState, Integer>();
        animationLength.put(CharacterState.WALKING, 2);
        animationLength.put(CharacterState.SHOOTING, 1);
        animationLength.put(CharacterState.STANDING, 1);
        animationLength.put(CharacterState.DIEING, 1);
        animationLength.put(CharacterState.JUMPING, 0);
        animationLength.put(CharacterState.FALLING, 1);

    }

    @Override
    public Rect getDimensions() {
        return dimensions;
    }
}
