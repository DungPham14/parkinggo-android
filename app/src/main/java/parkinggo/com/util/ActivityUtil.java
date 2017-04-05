/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.util;


import android.support.v7.app.AppCompatActivity;

public class ActivityUtil {

    public static void backToScreen(AppCompatActivity activity, Class<?> clazz) {
        for (int entry = 0; entry < activity.getSupportFragmentManager().getBackStackEntryCount(); entry++) {
            if (activity.getSupportFragmentManager().getBackStackEntryAt(entry).getName().equals(clazz.getSimpleName())) {
                return;
            } else {
                activity.getSupportFragmentManager().popBackStack();
            }
        }
        activity.finish();
    }
}
