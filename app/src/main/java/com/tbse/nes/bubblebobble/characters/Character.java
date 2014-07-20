package com.tbse.nes.bubblebobble.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.HashMap;

/**
 * Created by tbsmith on 7/20/14.
 */
public abstract class Character extends GravityUser {

    public boolean isFacingRight = true;

    protected int currentFrame;

    protected Bitmap[] standing;
    protected Bitmap[] walking;
    protected Bitmap[] shooting;
    protected Bitmap[] dieing;
    protected Bitmap[] falling;

    protected HashMap<CharacterState, Integer> animationLength;

    public static enum CharacterState {STANDING, WALKING, SHOOTING, DIEING, JUMPING, FALLING};

    public CharacterState currentCharacterState = CharacterState.STANDING;


    protected float totalGameTime = 0;

    protected int fps = 4;

    public void update(float dt) {
        totalGameTime += fps * dt;

        super.update(dt);

        if (isGrounded == false &&
                currentCharacterState != CharacterState.JUMPING) {
            currentCharacterState = CharacterState.FALLING;
        }

        // Just hit the ground from falling
        if (isGrounded == true && currentCharacterState == CharacterState.FALLING) {
            currentCharacterState = CharacterState.STANDING;
        }

        if (currentCharacterState == CharacterState.JUMPING) {
            getDimensions().offset(0, -10);
        } else if (currentCharacterState == CharacterState.FALLING) {
            getDimensions().offset(0, 5);
        }

        // find current frame
        currentFrame = (int) (totalGameTime - ((int) (totalGameTime
                / animationLength.get(currentCharacterState)))
                * animationLength.get(currentCharacterState));

        if (currentFrame < 0) currentFrame = 0;

        if (currentCharacterState == CharacterState.WALKING) {
            if (currentFrame >= walking.length) currentFrame = 0;
            currentBitmap = walking[currentFrame];
            getDimensions().offset(0, isGrounded ? 0 : 2);
        } else if (currentCharacterState == CharacterState.STANDING) {
            if (currentFrame >= standing.length) currentFrame = 0;
            currentBitmap = standing[currentFrame];
        } else if (currentCharacterState == CharacterState.FALLING) {
            currentBitmap = falling[0];
        } else if (currentCharacterState == CharacterState.JUMPING) {
            currentBitmap = walking[0];
        } else if (currentCharacterState == CharacterState.SHOOTING) {
            currentBitmap = shooting[0];
        } else if (currentCharacterState == CharacterState.DIEING) {
            if (currentFrame >= dieing.length) currentFrame = 0;
            currentBitmap = dieing[currentFrame];
        }


    }

    public Bitmap getBitmap() {
        return currentBitmap;
    }

    public void draw(Canvas c) {
        c.drawBitmap(getBitmap(), null, getDimensions(), null);
    }



}
