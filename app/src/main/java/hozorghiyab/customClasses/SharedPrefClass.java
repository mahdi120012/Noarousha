package hozorghiyab.customClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClass {
    public static SharedPreferences sharedPreferences;

    public static String getUserId(Context c, String key){

        sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
        String username = sharedPreferences.getString(key, "");
        return username;
    }

    public static void clearData(Context c){
        sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
        sharedPreferences.edit().putString("user", "").commit();
        sharedPreferences.edit().putString("pass", "").commit();
        sharedPreferences.edit().putString("name", "").commit();
        sharedPreferences.edit().putString("noe", "").commit();
        sharedPreferences.edit().putString("tedad_payam_khande_nashode", "").commit();
    }

}
