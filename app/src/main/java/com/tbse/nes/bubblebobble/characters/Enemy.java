package com.tbse.nes.bubblebobble.characters;

import android.graphics.Rect;

import com.tbse.nes.bubblebobble.Fragments.GameLevelFragment;

/**
 * Created by tbsmith on 7/20/14.
 */
public abstract class Enemy extends Character {

    public boolean isInBubble = false;

    private int speed = 2;

    @Override
    public void update(float dt) {


        if (!isInBubble) {

            super.update(dt);

            if (GameLevelFragment.player.getDimensions().centerX() < getDimensions().centerX()) {

                boolean wouldCrash = false;
                for (Enemy enemy : GameLevelFragment.enemies) {

                    if (enemy.getDimensions().centerX() == getDimensions().centerX()
                            && enemy.getDimensions().centerY() == getDimensions().centerY())
                    {
                        // must be this one
                        continue;
                    }

                    Rect testRect = getDimensions();
                    testRect.offset(-2, 0);
                    if (enemy.getDimensions().contains(testRect.left, testRect.centerY())) {
                        wouldCrash = true;
                        break;
                    }
                }

                if (!wouldCrash) {
//                    getDimensions().offset(-1, 0);
                }


            } else if (GameLevelFragment.player.getDimensions().centerX() > getDimensions().centerX()) {

                boolean wouldCrash = false;
                for (Enemy enemy : GameLevelFragment.enemies) {

                    if (enemy.getDimensions().centerX() == getDimensions().centerX()
                            && enemy.getDimensions().centerY() == getDimensions().centerY())
                    {
                        // must be this one
                        continue;
                    }

                    Rect testRect = getDimensions();
                    testRect.offset(2, 0);
                    if (enemy.getDimensions().contains(testRect.right, testRect.centerY())) {
                        wouldCrash = true;
                        break;
                    }
                }

                if (!wouldCrash) {
//                    getDimensions().offset(1, 0);
                }



            }

        }


    }


    @Override
    public abstract Rect getDimensions();

}
