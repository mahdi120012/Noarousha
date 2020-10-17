package hozorghiyab.customClasses;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class Keyboard {
    
    public static void hideKeyboard(Context c,Activity activity){
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) ((Activity) c).getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((Activity) c).getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public static void showKeyboard(Context c,Activity activity){
        if (activity.getCurrentFocus() == null) {
            InputMethodManager imm = (InputMethodManager) ((Activity) c).getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((Activity) c).getCurrentFocus().getWindowToken(),InputMethodManager.SHOW_IMPLICIT);
        }

    }
}
