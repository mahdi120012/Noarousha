package hozorghiyab.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hozorghiyab.R;

import java.util.ArrayList;
import java.util.Timer;

import hozorghiyab.cityDetail.LoadData;
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow;
import hozorghiyab.cityDetail.RecyclerModel;
import hozorghiyab.cityDetail.Recyclerview;

public class NamayeshVaziyatDarsiStudent extends AppCompatActivity {
    ImageView imgBack;
    EditText etJalaseName;
    ConstraintSet constraintSet;
    ConstraintLayout clActivityMainJalase;

    Timer timer;
    ImageView img_refresh;
    TextView tx_state,txNameDars;
    WebView webView;
    ProgressBar progressBar;
    RecyclerView rvAddJalase;
    String selectedItem,selectedItemClassId;
    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,rAdapterSpinner;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow,rModelsSpinner;
    String darsId,className;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namayesh_vaziyat_darsi_student);

        darsId = getIntent().getExtras().getString("dars_id");
        className = getIntent().getExtras().getString("class_name");

        imgBack = findViewById(R.id.imgBackInHozorGhiyabmain);
        //imgAfzodanjalaseText = findViewById(R.id.imgAfzodanjalaseText);
        //imgAddJalase = findViewById(R.id.imgAddjalase);
        etJalaseName = findViewById(R.id.etNameJalase);
        //spinner = findViewById(R.id.spinnerInMainHozorghiyab);
        clActivityMainJalase = findViewById(R.id.clActivityMainJalase);
        img_refresh = findViewById(R.id.img_refreshInAddjalase);
        tx_state = findViewById(R.id.tx_stateInAddJalase);
        webView = findViewById(R.id.webInAddjalase);
        progressBar = findViewById(R.id.progressBarInAddJalase);
        rvAddJalase = findViewById(R.id.rvAddjalase);
        txNameDars = findViewById(R.id.txNameDars);
        txNameDars.setText(className);

        sharedPreferences = this.getSharedPreferences("file", this.MODE_PRIVATE);
        final String usernameStudent = sharedPreferences.getString("user", "");



        rModelsYouHaveKnow = new ArrayList<>();
        rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_dars_result_student", NamayeshVaziyatDarsiStudent.this,rAdapterYouHaveKnow,
                selectedItem,null,null,null,"");
        Recyclerview.define_recyclerviewAddStudent(NamayeshVaziyatDarsiStudent.this,rvAddJalase,rAdapterYouHaveKnow,
                rModelsYouHaveKnow,progressBar);

        LoadData.loadDarsResultForStudent(NamayeshVaziyatDarsiStudent.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                webView,timer,tx_state,rvAddJalase,darsId,usernameStudent);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
