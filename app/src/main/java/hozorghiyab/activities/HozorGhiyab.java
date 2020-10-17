package hozorghiyab.activities;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
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

public class HozorGhiyab extends AppCompatActivity implements View.OnTouchListener {
    ImageView imgBack,imgSendTozihat;
    Timer timer;
    ImageView img_refresh;
    TextView tx_state,txNameJalase;
    WebView webView;
    ProgressBar progressBar;
    RecyclerView rvAddJalase;
    private RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,rAdapterSpinner;
    private ArrayList<RecyclerModel> rModelsYouHaveKnow,rModelsSpinner;
    String jalaseId, jalaseName, classId, className, selectedItem;
    EditText etTozihat,etTaklif;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hozor_ghiyab);


        imgBack = findViewById(R.id.imgBackInHozorGhiyabPage2);
        img_refresh = findViewById(R.id.img_refreshInHozoeGhiyabPage2);
        tx_state = findViewById(R.id.tx_stateInHozoeGhiyabPage2);
        webView = findViewById(R.id.webInHozorGhiyabPage2);
        progressBar = findViewById(R.id.progressBarInHozorGhiyabPage2);
        rvAddJalase = findViewById(R.id.rvHozorGhiyabPage2);
        txNameJalase = findViewById(R.id.txNameJalaseInHozorGhiyabPage2);
        imgSendTozihat = findViewById(R.id.imgSendTozihatHozorGhiyab);
        etTozihat = findViewById(R.id.etTozihatInHozorGhiyab);
        etTozihat.setOnTouchListener(HozorGhiyab.this);
        etTaklif = findViewById(R.id.etTaklifHafteAyandehInHozorGhiyab);
        etTaklif.setOnTouchListener(HozorGhiyab.this);


        jalaseId = getIntent().getExtras().getString("jalase_id");
        jalaseName = getIntent().getExtras().getString("jalase_name");
        classId = getIntent().getExtras().getString("class_id");
        className = getIntent().getExtras().getString("class_name");

        txNameJalase.setText(jalaseName + " - " + className);

  /*      //Adapter zir faghat baraye zakhireh ye class ide baraye spinner be kar mire:
        rModelsSpinner = new ArrayList<>();
        rAdapterSpinner = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_jalase",HozorGhiyab.this,rAdapterYouHaveKnow,
                selectedItem);

        List<String> list = new ArrayList<>();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinnter_dropdown_custom_for_takhir,list );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinnter_dropdown_custom_for_takhir);
        //spinner.setAdapter(spinnerArrayAdapter);
        //خط زیر برای لود کلاسهاست
        LoadData.loadTimeTakhirList(this,list,spinnerArrayAdapter,img_refresh,
                webView,timer,tx_state,rAdapterSpinner,rModelsSpinner);*/


   /*     List<String> list = new ArrayList<>();

        list.add("");
        list.add("۵ دقیقه");
        list.add("۱۰ دقیقه");
        list.add("۲۰ دقیقه");
        list.add("۳۰ دقیقه");
        list.add("۴۰ دقیقه");
        list.add("۵۰ دقیقه");
        list.add("۱ ساعت");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinnter_dropdown_custom_for_takhir,list );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinnter_dropdown_custom_for_takhir);*/
        imgSendTozihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadData.updateHozorGhiyabTozihat(HozorGhiyab.this, jalaseId, etTozihat.getText().toString(),etTaklif.getText().toString());
            }
        });

        rModelsYouHaveKnow = new ArrayList<>();

        rAdapterYouHaveKnow = new RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow,"add_hozorghiyab",HozorGhiyab.this,
                rAdapterYouHaveKnow,img_refresh,webView,timer,tx_state,rvAddJalase,className,jalaseId);

        Recyclerview.define_recyclerviewAddStudent(HozorGhiyab.this,rvAddJalase,rAdapterYouHaveKnow,
                rModelsYouHaveKnow,progressBar);

        SharedPreferences sharedPreferences = HozorGhiyab.this.getSharedPreferences("file", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        username = sharedPreferences.getString("user", "user");
        editor.commit();

        if (username.matches("100024") || username.matches("100025") ){
            username = "100010";
            //imgAfzodanjalaseText.setEnabled(false);
        }

        LoadData.firstLoadDataStudentForHozorGhiyab(HozorGhiyab.this,rAdapterYouHaveKnow,rModelsYouHaveKnow,img_refresh,
                webView,timer,tx_state,rvAddJalase,classId,jalaseId,username,etTozihat,etTaklif);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //below line baraye kar kardan scroll dar edittexti ke dakhele scrollview ee :
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.etTozihatInHozorGhiyab){
            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }else if (view.getId() == R.id.etTaklifHafteAyandehInHozorGhiyab){
            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }else {

        }

        return false;
    }
}

