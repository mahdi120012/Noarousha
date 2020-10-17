package hozorghiyab.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
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

public class ListHameyePayamHaBarayeAdmin extends AppCompatActivity {
    ImageView imgBackInActivityClass;

    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow;
    RecyclerView rvListYouHaveKnow;
    Timer timer;
    ImageView img_refresh,imgRecivedMessage,imgHome,imgSendMessage;
    TextView tx_state,txCountNotReadMessage;
    WebView webView;
    ProgressBar progressBar;
    ConstraintLayout clWifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_payam_ha_baraye_admin);

        imgBackInActivityClass = findViewById(R.id.imgBackInPayamHayeErsaliStudent);
        rvListYouHaveKnow = findViewById(R.id.rvInPayamHayeErsaliStudent);
        progressBar = findViewById(R.id.progressBarInPayamHayeErsaliStudent);
        imgRecivedMessage = findViewById(R.id.imgInboxMessage);
        imgHome = findViewById(R.id.imgHome);
        txCountNotReadMessage = findViewById(R.id.txCountNotReadMessage);
        clWifi = findViewById(R.id.clWifiInStudentPanel);

        txCountNotReadMessage.setText("0");

        imgSendMessage = findViewById(R.id.imgWriteMessage);

        imgSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListHameyePayamHaBarayeAdmin.this, ListPayamHayeErsali.class));

            }
        });

        imgRecivedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListHameyePayamHaBarayeAdmin.this, InboxMessage.class));
                finish();

            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        imgBackInActivityClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = ListHameyePayamHaBarayeAdmin.this.getSharedPreferences("file", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String username = sharedPreferences.getString("user", "user");
        editor.commit();

        rModelsYouHaveKnow = new ArrayList<>();
        rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"all_users_message",this,rAdapterYouHaveKnow,"",null,null,null,"");
        Recyclerview.define_recyclerviewAddStudent(this,rvListYouHaveKnow,rAdapterYouHaveKnow,
                rModelsYouHaveKnow,progressBar);

        LoadData.firstLoadDataAllMessage(this,rAdapterYouHaveKnow,rModelsYouHaveKnow,rvListYouHaveKnow,username,clWifi);


        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {

                //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                LoadData.loadCountMessageNotRead(ListHameyePayamHaBarayeAdmin.this,txCountNotReadMessage,username);

                ha.postDelayed(this, 1000);
            }
        }, 1000);

    }
    public void onBackPressed() {
        finish();
    }
}
