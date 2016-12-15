package org.numisoft.usquarters.utils;

/**
 * Created by kukolka on 11/14/2016.
 */

public class UpdateHelper {

    private static boolean needsUpdate;

    public static boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public static void setNeedsUpdate(boolean needsUpdate) {
        UpdateHelper.needsUpdate = needsUpdate;
    }
}
