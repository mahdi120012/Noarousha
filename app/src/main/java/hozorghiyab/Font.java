package hozorghiyab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

public class Font {

    public static Typeface    font;
    public static int         size;
    public static int         space;
    public static String      mood;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("setting", 0);
    }

    public static Typeface SetFont(Context context) {

        String h = getPrefs(context).getString("font", "AdobeArabic-Regular");
        font = Typeface.createFromAsset(context.getAssets(), "font/" + h + ".otf");
        size = getPrefs(context).getInt("size", 26);
        space = getPrefs(context).getInt("space", 10);
        mood = getPrefs(context).getString("mood", "day");

        return font;
    }

}
