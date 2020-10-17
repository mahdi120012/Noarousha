package hozorghiyab.activities;

import android.content.SharedPreferences;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hozorghiyab.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import hozorghiyab.cityDetail.LoadData;
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow;
import hozorghiyab.cityDetail.RecyclerModel;
import hozorghiyab.cityDetail.Recyclerview;
import hozorghiyab.customClasses.EnglishNumberToPersian;
import hozorghiyab.customClasses.TimeKononi;

public class HozorGhiyabMain extends AppCompatActivity {
    ImageView imgBack,imgAfzodanjalaseText,imgAddJalase;
    EditText etJalaseName;
    Spinner spinner;
    ConstraintSet constraintSet;
    ConstraintLayout clActivityMainJalase;

    Timer timer;
    ImageView img_refresh;
    TextView tx_state;
    WebView webView;
    ProgressBar progressBar;
    RecyclerView rvAddJalase;
    String username;
    String selectedItem,selectedItemClassId;
    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,rAdapterSpinner;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow,rModelsSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hozor_ghiyab_main);


        imgBack = findViewById(R.id.imgBackInHozorGhiyabmain);
        imgAfzodanjalaseText = findViewById(R.id.imgAfzodanjalaseText);
        imgAddJalase = findViewById(R.id.imgAddjalase);
        etJalaseName = findViewById(R.id.etNameJalase);
        spinner = findViewById(R.id.spinnerInMainHozorghiyab);
        clActivityMainJalase = findViewById(R.id.clActivityMainJalase);
        img_refresh = findViewById(R.id.img_refreshInAddjalase);
        tx_state = findViewById(R.id.tx_stateInAddJalase);
        webView = findViewById(R.id.webInAddjalase);
        progressBar = findViewById(R.id.progressBarInAddJalase);
        rvAddJalase = findViewById(R.id.rvAddjalase);

        TimeKononi timeKononi = new TimeKononi();
        final String nowTime = timeKononi.getPersianTimeWithoutDayName();

        imgAfzodanjalaseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etJalaseName.setText(new EnglishNumberToPersian().convert( "جلسه - " + nowTime));


                if (imgAddJalase.getVisibility() == View.GONE) {
                    imgAddJalase.setVisibility(View.VISIBLE);
                    etJalaseName.setVisibility(View.VISIBLE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityMainJalase);

                    constraintSet.connect(R.id.rvAddjalase, ConstraintSet.TOP, R.id.etNameJalase, ConstraintSet.BOTTOM, 60);
                    constraintSet.applyTo(clActivityMainJalase);
                }else {
                    imgAddJalase.setVisibility(View.GONE);
                    etJalaseName.setVisibility(View.GONE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityMainJalase);

                    constraintSet.connect(R.id.rvAddjalase, ConstraintSet.TOP, R.id.imgAfzodanjalaseText, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(clActivityMainJalase);
                }
            }
        });

        //Adapter zir faghat baraye zakhireh ye class ide baraye spinner be kar mire:
        rModelsSpinner = new ArrayList<>();
        rAdapterSpinner = new RecyclerAdapterYouHaveKnow(rModelsSpinner,"add_jalase",HozorGhiyabMain.this,rAdapterSpinner,
                selectedItem,null,null,null,"");

        List<String> list = new ArrayList<>();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinnter_dropdown_custom,list );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinnter_dropdown_custom);
        spinner.setAdapter(spinnerArrayAdapter);

        SharedPreferences sharedPreferences = HozorGhiyabMain.this.getSharedPreferences("file", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        username = sharedPreferences.getString("user", "user");
        editor.commit();


        if (username.matches("100024") || username.matches("100025") ){
            username = "100010";
        }

        //خط زیر برای لود کلاسهاست
        LoadData.loadClassList(this,list,spinnerArrayAdapter,img_refresh,
                webView,timer,tx_state,username,rAdapterSpinner,rModelsSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
                selectedItemClassId = rModelsSpinner.get(i).getId();

                rModelsYouHaveKnow = new ArrayList<>();
                rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_jalase",HozorGhiyabMain.this,rAdapterYouHaveKnow,
                        selectedItem,null,null,null,"");
                Recyclerview.define_recyclerviewAddStudent(HozorGhiyabMain.this,rvAddJalase,rAdapterYouHaveKnow,
                        rModelsYouHaveKnow,progressBar);

                LoadData.firstLoadDataJalasat(HozorGhiyabMain.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                        webView,timer,tx_state,rvAddJalase,selectedItemClassId,username);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        etJalaseName.setText(new EnglishNumberToPersian().convert( "جلسه - " + nowTime));
        imgAddJalase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jalaseName = new EnglishNumberToPersian().convert(etJalaseName.getText().toString());
                String className = selectedItemClassId;

                if(className.length() <=0 || className.isEmpty() || jalaseName.length() <=0 || jalaseName.isEmpty()){
                    Toast.makeText(HozorGhiyabMain.this,"همه موارد را تکمیل کنید",Toast.LENGTH_SHORT).show();
                }else {

                    LoadData.sendJalase(HozorGhiyabMain.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                            webView,timer,tx_state,rvAddJalase,progressBar,jalaseName,className,username);

                    //etJalaseName.setText("");

                    imgAddJalase.setVisibility(View.GONE);
                    etJalaseName.setVisibility(View.GONE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityMainJalase);

                    constraintSet.connect(R.id.rvAddjalase, ConstraintSet.TOP, R.id.imgAfzodanjalaseText, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(clActivityMainJalase);

                }
            }
        });




        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
