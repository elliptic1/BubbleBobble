package com.tbse.nes.bubblebobble.Levels;

import android.graphics.Rect;

/**
 * Created by tbsmith on 7/19/14.
 */
public class Level01 {

    static Rect[] platforms = new Rect[10];

    public static void init() {

        // top row
        platforms[0] = new Rect(0, 400, 258, 450);
        platforms[1] = new Rect(346, 400, 1500, 450);
        platforms[2] = new Rect(1600, 400, 1822, 450);

        // middle row
        platforms[3] = new Rect(0, 597, 258, 635);
        platforms[4] = new Rect(346, 597, 1500, 635);
        platforms[5] = new Rect(1600, 597, 1822, 635);

        // bottom row
        platforms[6] = new Rect(0, 779, 258, 824);
        platforms[7] = new Rect(346, 779, 1500, 824);
        platforms[8] = new Rect(1600, 779, 1822, 824);

        // floor
        platforms[9] = new Rect(0, 960, 1900, 1038);
    }

    public static boolean isGrounded(int x, int y) {

        for (int i=0; i<platforms.length; i++) {
            if (platforms[i].contains(x, y)) {
                return true;
            }
        }

        return false;
    }

    public int getLevelNumber() {
        return 1;
    }
}
