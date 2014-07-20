package com.tbse.nes.bubblebobble.characters;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.tbse.nes.bubblebobble.Levels.Level01;

/**
 * Created by tbsmith on 7/20/14.
 */
public abstract class GravityUser {

    protected Bitmap currentBitmap;

    public boolean isGrounded = false;


    public void update(float dt) {

        isGrounded = Level01.isGrounded(getDimensions().left, getDimensions().bottom);

    }


    public abstract Rect getDimensions();

}
