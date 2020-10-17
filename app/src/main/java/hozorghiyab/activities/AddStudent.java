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

public class AddStudent extends AppCompatActivity {
    ImageView imgBack,imgAfzodanStudentText,imgAddStudent;
    EditText etStudentName;
    Spinner spinner;
    ConstraintSet constraintSet;
    ConstraintLayout clActivityStudent;

    Timer timer;
    ImageView img_refresh;
    TextView tx_state;
    WebView webView;
    ProgressBar progressBar;
    RecyclerView rvAddStudent;
    String selectedItem,selectedItemClassId;
    String username;
    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,rAdapterSpinner;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow,rModelsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        imgBack = findViewById(R.id.imgBackInAddStudent);
        imgAfzodanStudentText = findViewById(R.id.imgAfzodanStudentText);
        imgAddStudent = findViewById(R.id.imgAddStudent);
        etStudentName = findViewById(R.id.etStudentName);
        spinner = findViewById(R.id.spinnerInAddStudent);
        clActivityStudent = findViewById(R.id.clActivityStudent);
        img_refresh = findViewById(R.id.img_refreshInAddStudent);
        tx_state = findViewById(R.id.tx_stateInAddStudent);
        webView = findViewById(R.id.webInAddStudent);
        progressBar = findViewById(R.id.progressBarInAddStudent);
        rvAddStudent = findViewById(R.id.rvAddStudent);

        imgAfzodanStudentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgAddStudent.getVisibility() == View.GONE) {
                    imgAddStudent.setVisibility(View.VISIBLE);
                    etStudentName.setVisibility(View.VISIBLE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityStudent);

                    constraintSet.connect(R.id.rvAddStudent, ConstraintSet.TOP, R.id.etStudentName, ConstraintSet.BOTTOM, 60);
                    constraintSet.applyTo(clActivityStudent);
                }else {
                    imgAddStudent.setVisibility(View.GONE);
                    etStudentName.setVisibility(View.GONE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityStudent);

                    constraintSet.connect(R.id.rvAddStudent, ConstraintSet.TOP, R.id.imgAfzodanStudentText, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(clActivityStudent);
                }


            }
        });

        //Adapter zir faghat baraye zakhireh ye class ide baraye spinner be kar mire:
        rModelsSpinner = new ArrayList<>();
        rAdapterSpinner = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_jalase",AddStudent.this,rAdapterYouHaveKnow,
                selectedItem,null,null,null,"");

        List<String> list = new ArrayList<>();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinnter_dropdown_custom,list );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinnter_dropdown_custom);
        spinner.setAdapter(spinnerArrayAdapter);

        SharedPreferences sharedPreferences = AddStudent.this.getSharedPreferences("file", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        username = sharedPreferences.getString("user", "user");
        editor.commit();

        if (username.matches("100024") || username.matches("100025") ){
            username = "100010";
            imgAfzodanStudentText.setEnabled(false);
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
                rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_student",AddStudent.this,rAdapterYouHaveKnow,"",null,null,null,"");
                Recyclerview.define_recyclerviewAddStudent(AddStudent.this,rvAddStudent,rAdapterYouHaveKnow,
                        rModelsYouHaveKnow,progressBar);

                LoadData.firstLoadDataStudent(AddStudent.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                        webView,timer,tx_state,rvAddStudent,selectedItemClassId,username);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentName = etStudentName.getText().toString();
                String className = selectedItem;

                if(className.length() <=0 || className.isEmpty() || studentName.length() <=0 || studentName.isEmpty()){
                    Toast.makeText(AddStudent.this,"همه موارد را تکمیل کنید",Toast.LENGTH_SHORT).show();
                }else {

                    LoadData.sendStudent(AddStudent.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                            webView,timer,tx_state,rvAddStudent,studentName,selectedItemClassId,username,progressBar);

                    etStudentName.setText("");
                    Toast.makeText(AddStudent.this,"ثبت شد",Toast.LENGTH_SHORT).show();

                    imgAddStudent.setVisibility(View.GONE);
                    etStudentName.setVisibility(View.GONE);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(clActivityStudent);

                    constraintSet.connect(R.id.rvAddStudent, ConstraintSet.TOP, R.id.imgAfzodanStudentText, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(clActivityStudent);

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
