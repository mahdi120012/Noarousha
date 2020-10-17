package hozorghiyab.activities;

import android.content.SharedPreferences;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hozorghiyab.R;

import java.util.ArrayList;
import java.util.Timer;

import hozorghiyab.cityDetail.LoadData;
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow;
import hozorghiyab.cityDetail.RecyclerModel;
import hozorghiyab.cityDetail.Recyclerview;

public class AddClass extends AppCompatActivity {
    ImageView imgBackInActivityClass,imgAfzodanClassText,imgAddClass;
    EditText etClassName,etSchoolName;
    ConstraintSet constraintSet;
    ConstraintLayout clActivityClass;

    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow;
    RecyclerView rvListYouHaveKnow;
    Timer timer;
    ImageView img_refresh;
    TextView tx_state;
    WebView webView;
    ProgressBar progressBar;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        imgBackInActivityClass = findViewById(R.id.imgBackInActivityClass);
        imgAfzodanClassText = findViewById(R.id.imgAfzodanClassText);
        imgAddClass = findViewById(R.id.imgAddClass);
        etClassName = findViewById(R.id.etClassName);
        etSchoolName = findViewById(R.id.etSchoolName);
        clActivityClass = findViewById(R.id.clActivityClass);
        img_refresh = findViewById(R.id.img_refreshInAddClass);
        tx_state = findViewById(R.id.tx_stateInAddClass);
        webView = findViewById(R.id.webInAddClass);
        rvListYouHaveKnow = findViewById(R.id.rvAddClass);
        progressBar = findViewById(R.id.progressBarInAddClass);

        imgBackInActivityClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        imgAfzodanClassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgAddClass.getVisibility() == View.GONE) {
                    imgAddClass.setVisibility(View.VISIBLE);
                    etClassName.setVisibility(View.VISIBLE);
                    etSchoolName.setVisibility(View.VISIBLE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityClass);

                    constraintSet.connect(R.id.rvAddClass, ConstraintSet.TOP, R.id.txSchoolName, ConstraintSet.BOTTOM, 120);
                    constraintSet.applyTo(clActivityClass);
                } else {
                    imgAddClass.setVisibility(View.GONE);
                    etClassName.setVisibility(View.GONE);
                    etSchoolName.setVisibility(View.GONE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityClass);

                    constraintSet.connect(R.id.rvAddClass, ConstraintSet.TOP, R.id.imgAfzodanClassText, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(clActivityClass);
                }


            }
        });

        //Line below baraye gereftan username va load class ha bar asas onee
        SharedPreferences sharedPreferences = AddClass.this.getSharedPreferences("file", this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                username = sharedPreferences.getString("user", "user");
                editor.commit();

        if (username.matches("100024") || username.matches("100025") ){
            username = "100010";
            imgAfzodanClassText.setEnabled(false);
        }


        rModelsYouHaveKnow = new ArrayList<>();
        rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_class",this,rAdapterYouHaveKnow,"",null,null,null,"");
        Recyclerview.define_recyclerviewYh(this,rvListYouHaveKnow,rAdapterYouHaveKnow,
                rModelsYouHaveKnow,progressBar);

        LoadData.firstLoadDataYH(this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                webView,timer,tx_state,rvListYouHaveKnow,username);

        imgAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String schoolName = etSchoolName.getText().toString();
                String className = etClassName.getText().toString();

                if(className.length() <=0 || className.isEmpty() || schoolName.length() <=0 || schoolName.isEmpty()){
                    Toast.makeText(AddClass.this,"همه موارد را تکمیل کنید",Toast.LENGTH_SHORT).show();
                }else {

                    LoadData.sendClass(AddClass.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                            webView,timer,tx_state,rvListYouHaveKnow,schoolName,className,username,progressBar);

                    etSchoolName.setText("");
                    etClassName.setText("");
                    Toast.makeText(AddClass.this,"ثبت شد",Toast.LENGTH_SHORT).show();

                    imgAddClass.setVisibility(View.GONE);
                    etClassName.setVisibility(View.GONE);
                    etSchoolName.setVisibility(View.GONE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityClass);

                    constraintSet.connect(R.id.rvAddClass, ConstraintSet.TOP, R.id.imgAfzodanClassText, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(clActivityClass);

                }
            }
        });


    }
}
