package hozorghiyab.customClasses;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogClass {
    public static ProgressDialog progressDialog;

    public static void showProgress(Context c){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(c);
        }
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgress(){
        progressDialog.dismiss();
    }
}
