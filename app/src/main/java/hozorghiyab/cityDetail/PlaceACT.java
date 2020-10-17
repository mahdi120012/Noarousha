package hozorghiyab.cityDetail;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.Timer;

import hozorghiyab.cityDetail.placeComment.RecyclerAdapterPlaceComment;
import hozorghiyab.cityDetail.placeComment.RecyclerModelPlaceComment;
import com.hozorghiyab.R;

public class PlaceACT extends AppCompatActivity {
    private RecyclerAdapter rAdapter_place;
    private ArrayList<RecyclerModel> rModels_place;
    private RecyclerAdapterPlaceComment rAdapterPlaceComment;
    private ArrayList<RecyclerModelPlaceComment> rModelsPlaceComment;
    String id,onvan,matn,picture;
    float rate;
    TextView tx_actionbar,txState,tx_onvan,tx_matn,txSendComment,txNumberOfRate;
    EditText etComment;
    Timer timer;
    ImageView img_back,img_refresh;
    WebView webView;
    RecyclerView rvPlacePicture,rvPlaceComment;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SimpleRatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_act);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        rvPlacePicture =  findViewById(R.id.rv_place);
        rvPlaceComment = findViewById(R.id.rvPlaceComment);
        progressBar = findViewById(R.id.progressBarPlace);
        txState = findViewById(R.id.tx_statePlace);
        img_refresh = findViewById(R.id.img_refreshPlace);
        webView =  findViewById(R.id.webPlace);
        tx_onvan =  findViewById(R.id.tx_onvan);
        tx_matn = findViewById(R.id.tx_matn);
        etComment = findViewById(R.id.etComment);
        txSendComment = findViewById(R.id.txSendComment);
        tx_actionbar = findViewById(R.id.tx_actionbar_storya);
        img_back = findViewById(R.id.backActionBarPlaceAct);
        ratingBar = findViewById(R.id.ratingBarinPlaceAct);
        tx_onvan =  findViewById(R.id.tx_onvan);
        tx_matn = findViewById(R.id.tx_matn);
        txNumberOfRate = findViewById(R.id.numberOfRate);
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);

        sharedPreferences = this.getSharedPreferences("file", this.MODE_PRIVATE);
        final String name = sharedPreferences.getString("name", "");
        final String username = sharedPreferences.getString("user", "");

        id = getIntent().getExtras().getString("id");
        onvan = getIntent().getExtras().getString("onvan");
        tx_actionbar.setText(onvan);
        matn = getIntent().getExtras().getString("matn");
        picture = getIntent().getExtras().getString("picture");
        tx_onvan.setText(onvan);
        tx_matn.setText(matn);


        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating=String.valueOf(ratingBar.getRating());

                if (name.length() <= 0) {
                    Toast.makeText(PlaceACT.this, "برای ارسال نظر باید وارد شوید", Toast.LENGTH_SHORT).show();
                }else {
                    LoadData.sendRate(PlaceACT.this,rAdapterPlaceComment,rModelsPlaceComment,img_refresh,
                            webView,timer,txState,rvPlacePicture,username,id,rating,ratingBar,txNumberOfRate);
                }

            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        rModelsPlaceComment = new ArrayList<>();
        rModels_place = new ArrayList<>();

        rAdapterPlaceComment = new RecyclerAdapterPlaceComment(rModelsPlaceComment,"place",this,width);
        rAdapter_place = new RecyclerAdapter(rModels_place,"place",this,width);
/*
        Recyclerview.defineRecyclerviewPC(this,rvPlaceComment,rAdapterPlaceComment,
                rModelsPlaceComment,progressBar,nestedScrollView,id);*/
        Recyclerview.define_recyclerview(this,rvPlacePicture,rAdapter_place,
                rModels_place,progressBar);


        LoadData.firstLoadDataPlaceComment(this,rAdapterPlaceComment,rModelsPlaceComment,img_refresh,
                webView,timer,txState,rvPlacePicture,"قم",id);

        LoadData.firstLoadDataPlaceAct(this,rAdapter_place,rModels_place,img_refresh,
                webView,timer,txState,rvPlacePicture,"قم",onvan);

        LoadData.firstLoadDataRate(this,img_refresh,
                webView,timer,txState,id,ratingBar,txNumberOfRate);


        txSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.length() <= 0) {
                    Toast.makeText(PlaceACT.this, "برای ارسال کامنت باید وارد شوید", Toast.LENGTH_SHORT).show();
                }else {

                    //rModelsPlaceComment.add(new RecyclerModelPlaceComment(username, name,"https://www.chetor.com/wp-content/uploads/2016/08/give-hope.jpg",etComment.getText().toString()));
                    //rAdapterPlaceComment.notifyDataSetChanged();

                    //LoadData.sendComment(PlaceACT.this,rAdapterPlaceComment,rModelsPlaceComment,img_refresh,
                            //webView,timer,txState,rvPlacePicture,"قم",id,etComment,username,name,etComment.getText().toString(),progressBar);

                }

            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etComment.getText().toString().length() > 0){
                    txSendComment.setTextColor(Color.parseColor("#1287c2"));
                    txSendComment.setEnabled(true);
                }else {
                    txSendComment.setTextColor(Color.parseColor("#b1c6e5"));
                    txSendComment.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if (name.length() <= 0) {
            etComment.setHint("برای ارسال کامنت باید وارد شوید");
        } else {
            etComment.setHint(name + " کامنت با");
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
