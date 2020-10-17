package hozorghiyab.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hozorghiyab.R;

public class Setting extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_act);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //Change StatusBar color:
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#31678a"));

        ImageView imgBack = findViewById(R.id.imgBackInSettingPage);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedPreferences = Setting.this.getSharedPreferences("file", Setting.this.MODE_PRIVATE);
        final String phoneNumber = sharedPreferences.getString("user", "");
        final String password = sharedPreferences.getString("pass", "");
        final String name = sharedPreferences.getString("name", "");

        EditText etName = findViewById(R.id.etNameInSettingPage);
        EditText etPhoneNumber = findViewById(R.id.etPhoneNumberInSettingPage);
        EditText etPassword = findViewById(R.id.etPassInSettingPage);

        etName.setText(name);
        etPhoneNumber.setText(phoneNumber);
        etPassword.setText(password);

        TextView txExit= findViewById(R.id.txExitFromAccount);
        txExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = Setting.this.getSharedPreferences("file", Setting.this.MODE_PRIVATE);

                sharedPreferences.edit().remove("name").commit();
                sharedPreferences.edit().remove("user").commit();
                sharedPreferences.edit().remove("pass").commit();
                //Intent intent = new Intent(Setting.this, CityDetailACT.class);
                //startActivity(intent);
                finish();

            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}
