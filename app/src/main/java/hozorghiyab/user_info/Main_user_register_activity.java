package hozorghiyab.user_info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hozorghiyab.R;

public class Main_user_register_activity extends AppCompatActivity {

    final static String urlAddress = "http://robika.ir/ultitled/safarkon/register.php";
    EditText            etName, etPhoneNumber, etPassword;
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        constraintLayout = findViewById(R.id.clRegister);






        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#31678a"));

        etName = findViewById(R.id.etNameInRegisterPage);
        etPhoneNumber = findViewById(R.id.etPhoneNumberInRegisterPage);
        etPassword = findViewById(R.id.etPassInRegisterPage);
        ImageView imgRegister =  findViewById(R.id.imgRegister);
        final TextView txState = findViewById(R.id.txStateUserRegister);
        ImageView imgZarbdar = findViewById(R.id.imgZarbdarInRegisterPage);

        imgZarbdar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    etPhoneNumber.setText("");
            }
        });
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etPhoneNumber.setBackgroundResource(R.xml.edittext_style);
                txState.setVisibility(View.GONE);

                constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(R.id.etPassInRegisterPage,ConstraintSet.TOP,R.id.etPhoneNumberInRegisterPage,ConstraintSet.TOP,155);
                constraintSet.applyTo(constraintLayout);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        imgRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String name = etName.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();

                if ((name.length() <= 0 || name == null) || (phoneNumber.length() <= 0 || phoneNumber == null) || (password.length() <= 0 || password == null)) {
                    Toast.makeText(Main_user_register_activity.this, "لطفا همه فیلدها را وارد کنید", Toast.LENGTH_SHORT).show();

                } else if (phoneNumber.length() < 11){
                    constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    constraintSet.connect(R.id.etPassInRegisterPage,ConstraintSet.TOP,R.id.etPhoneNumberInRegisterPage,ConstraintSet.TOP,200);
                    constraintSet.applyTo(constraintLayout);
                    
                    txState.setVisibility(View.VISIBLE);
                    txState.setText("شماره همراه خود را صحیح وارد نمایید.");
                }
                else {
                    new Registeration_helper(Main_user_register_activity.this, urlAddress, etName, etPhoneNumber, etPassword,txState,constraintLayout).execute();
                }

            }
        });


        TextView txLogin = findViewById(R.id.txLoginInRegisterPage);
        txLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_user_register_activity.this, Main_user_login_activity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView imgBack = findViewById(R.id.imgBackInRegisterPage);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Main_user_register_activity.this, CityDetailACT.classpic);
                startActivity(intent);*/
                finish();
            }
        });


    }


    public void onBackPressed() {
        finish();
    }
}
