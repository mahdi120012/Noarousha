package hozorghiyab.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hozorghiyab.R;

import java.util.ArrayList;
import java.util.Timer;

import hozorghiyab.cityDetail.LoadData;
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow;
import hozorghiyab.cityDetail.RecyclerModel;
import hozorghiyab.cityDetail.UrlEncoderClass;
import hozorghiyab.customClasses.TimeKononi;

public class ErsalVaziyatDarsiStudent extends AppCompatActivity implements View.OnTouchListener {
    ImageView imgBackInActivityClass;
    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow;
    RecyclerView rvListYouHaveKnow;
    Timer timer;
    ImageView imgRecivedMessage,imgHome,imgSendMessage;
    TextView txCountNotReadMessage,txFamily;

    String stdId,stdFamily;
    EditText etOnvan,etMatn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_new_message_teacher);

        stdId = getIntent().getExtras().getString("teacher_id");
        stdFamily = getIntent().getExtras().getString("class_name");
        imgBackInActivityClass = findViewById(R.id.imgBackInSendMessageTeacher);
        /*img_refresh = findViewById(R.id.img_refreshInSendMessageTeacher);
        tx_state = findViewById(R.id.tx_stateInSendMessageTeacher);
        webView = findViewById(R.id.webInSendMessageTeacher);
        progressBar = findViewById(R.id.progressBarInSendMessageTeacher);*/
        imgRecivedMessage = findViewById(R.id.imgInboxMessage);
        imgHome = findViewById(R.id.imgHome);
        txCountNotReadMessage = findViewById(R.id.txCountNotReadMessage);
        imgSendMessage = findViewById(R.id.imgSendMessageTeacher);
        etOnvan = findViewById(R.id.etOnvanSendTeacherMessage);
        etMatn = findViewById(R.id.etMatnSendTeacherMessage);
        txFamily = findViewById(R.id.txReciverFamilyInTeacher);
        txFamily.setText(stdFamily);

        etMatn.setOnTouchListener(ErsalVaziyatDarsiStudent.this);
        etOnvan.setOnTouchListener(ErsalVaziyatDarsiStudent.this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("file", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String username = sharedPreferences.getString("user", "");

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                String urlAppend = "?action=load_student_count_not_read_message" +
                        "&user1=" + UrlEncoderClass.urlEncoder(username);
                LoadData.loadCountMessageNotRead(ErsalVaziyatDarsiStudent.this,txCountNotReadMessage,username);

                ha.postDelayed(this, 1000);
            }
        }, 1000);


        imgSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String onvan = etOnvan.getText().toString();
                String matn = etMatn.getText().toString();

                if ((onvan.length() <= 0 || onvan == null) || (matn.length() <= 0 || matn == null)) {
                    Toast.makeText(ErsalVaziyatDarsiStudent.this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    String nowTime = new TimeKononi().getPersianTime();
                    LoadData.sendMessageStudent(ErsalVaziyatDarsiStudent.this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                             rvListYouHaveKnow, username, stdId, onvan, matn,nowTime);
                }
            }
        });

        txCountNotReadMessage.setText("0");

        imgRecivedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ErsalVaziyatDarsiStudent.this,InboxMessage.class));
                finish();

            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ErsalVaziyatDarsiStudent.this,StudentPanelMainKt.class));
                finish();
            }
        });

        imgBackInActivityClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.etMatnSendTeacherMessage){
            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }else  if(view.getId() == R.id.etOnvanSendTeacherMessage){
            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
        return false;
    }
    public void onBackPressed() {
        finish();
    }
}

