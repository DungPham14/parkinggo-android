/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 03/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.prefs;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import parkinggo.com.injection.ApplicationContext;

@Singleton
public class GlobalPref {
    private final SharedPreferences mSharePreferences;
    private final SharedPreferences.Editor mEditor;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    @Inject
    public GlobalPref(@ApplicationContext Context context) {
        this.context = context;
        mSharePreferences = context.getSharedPreferences(PrefConst.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharePreferences.edit();
    }

    public void skipSignIn(boolean isSkip) {
        mEditor.putBoolean(PrefConst.SKIP_SIGN_IN_KEY, isSkip);
        mEditor.apply();
    }

    public boolean isSkipSignIn() {
        return mSharePreferences.getBoolean(PrefConst.SKIP_SIGN_IN_KEY, false);
    }
}
