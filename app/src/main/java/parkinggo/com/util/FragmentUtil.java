/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class FragmentUtil {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int containerId) {

        Fragment mCurrentFragment = fragmentManager.findFragmentById(containerId);

        // Ignore replace fragment if it is showing on screen
        if (mCurrentFragment != null && mCurrentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        String backStateName = fragment.getClass().getName();

        // Re-use fragment if it was added to back-stack

        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(containerId, fragment);

            if (mCurrentFragment != null)
                ft.hide(mCurrentFragment);

            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public static void replaceFragmentWithoutBackStack(FragmentManager fragmentManager, Fragment fragment, int containerId) {

        Fragment mCurrentFragment = fragmentManager.findFragmentById(containerId);

        // Ignore replace fragment if it is showing on screen
        if (mCurrentFragment != null && mCurrentFragment.getClass().equals(fragment.getClass())) {
            return;
        }
        String backStateName = fragment.getClass().getName();

        // Re-use fragment if it was added to back-stack
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(containerId, fragment);

            if (mCurrentFragment != null)
                ft.hide(mCurrentFragment);
            ft.commit();
        }
    }

    public static void showNewFragment(AppCompatActivity context, int placeHolder,
                                       Fragment newFragment, Bundle bundle) {
        try {
            android.support.v4.app.FragmentManager fragmentManager = context.getSupportFragmentManager();
            String fragmentTag = newFragment.getClass().getSimpleName();
            if (fragmentManager.findFragmentByTag(fragmentTag) == null
                    || !fragmentManager.findFragmentByTag(fragmentTag).isAdded()) {
                if (bundle != null) {
                    newFragment.setArguments(bundle);
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(placeHolder, newFragment).addToBackStack(fragmentTag)
                        .commitAllowingStateLoss();
            }
        } catch (Exception ex) {
            Log.d("Failed", ex.getMessage());
        }
    }

    public static void showNewFragmentNotBackStack(AppCompatActivity context, int placeHolder,
                                                   Fragment newFragment, Bundle bundle) {
        try {
            android.support.v4.app.FragmentManager fragmentManager = context.getSupportFragmentManager();
            String fragmentTag = newFragment.getClass().getSimpleName();
            if (fragmentManager.findFragmentByTag(fragmentTag) == null
                    || !fragmentManager.findFragmentByTag(fragmentTag).isAdded()) {
                if (bundle != null) {
                    newFragment.setArguments(bundle);
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(placeHolder, newFragment)
                        .commitAllowingStateLoss();
            }
        } catch (Exception ex) {
            Log.d("Failed", ex.getMessage());
        }
    }

    public static Fragment getCurrentFragment(AppCompatActivity activity, int containerId) {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(containerId);
        return currentFragment;
    }
}
