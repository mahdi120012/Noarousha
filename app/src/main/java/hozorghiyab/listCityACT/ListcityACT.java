package hozorghiyab.listCityACT;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.hozorghiyab.R;

public class ListcityACT extends AppCompatActivity {
    RecyclerView rv1;
    ArrayList<Contacts> citys = new ArrayList<Contacts>();
    private Database          db;
    Contacts contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcity_act);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView tx_actionbar = findViewById(R.id.tx_actionbar_storya);
        tx_actionbar.setText("انتخاب استان");
        ImageView img_back = findViewById(R.id.back_actionbar_storya);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        rv1 = (RecyclerView) findViewById(R.id.rv_listcity);
        RecyclerviewDefine.define(this,rv1);

        db = new Database(this);
        db.useable();
        RefreshDb.refresh(db,contacts,citys);

        CityAdapter adapter=new CityAdapter(this,citys,rv1);
        rv1.setAdapter(adapter);



    }

}
