package android.designdemo.util;

import android.os.Build;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/10
 * @description
 */
public final class SDKUtils {

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT > 20;
    }
}
