package hozorghiyab.customClasses;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


public class AppVersionName {

    public static String getVersionName(Context c){

        PackageInfo pInfo = null;
        try {
            pInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String app_version_name = pInfo.versionName;

        return app_version_name;
    }
}
