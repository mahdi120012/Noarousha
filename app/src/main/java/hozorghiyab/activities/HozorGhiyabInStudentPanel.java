package hozorghiyab.activities;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
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

public class HozorGhiyabInStudentPanel extends AppCompatActivity {
    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow;
    Timer timer;
    ImageView img_refresh,imgBack;
    TextView tx_state;
    WebView webView;
    ProgressBar progressBar;
    RecyclerView rvDarsList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hozor_ghiyab_in_student_panel);

        rvDarsList = findViewById(R.id.rvDarsList);
        imgBack = findViewById(R.id.imgBackInDarsList);
        img_refresh = findViewById(R.id.img_refreshInDarsList);
        tx_state = findViewById(R.id.tx_stateInDarsList);
        webView = findViewById(R.id.webInDarsList);
        progressBar = findViewById(R.id.progressBarInDarsList);

        sharedPreferences = HozorGhiyabInStudentPanel.this.getSharedPreferences("file", HozorGhiyabInStudentPanel.this.MODE_PRIVATE);
        final String usernameStudent = sharedPreferences.getString("user", "");


        rModelsYouHaveKnow = new ArrayList<>();
        rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"dars_list",this,rAdapterYouHaveKnow,"",null,null,null,"");
        Recyclerview.define_recyclerviewYh(this,rvDarsList,rAdapterYouHaveKnow,
                rModelsYouHaveKnow,progressBar);

        LoadData.firstLoadDataDarsList(this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                webView,timer,tx_state,rvDarsList,usernameStudent);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
