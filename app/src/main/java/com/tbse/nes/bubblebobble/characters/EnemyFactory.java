package com.tbse.nes.bubblebobble.characters;

import android.content.Context;

/**
 * Created by tbsmith on 7/20/14.
 */
public class EnemyFactory {

    public Enemy getEnemy(int x, int y, Context context) {
        return new PurpleMonster(x, y, 120, context);
    }

}
