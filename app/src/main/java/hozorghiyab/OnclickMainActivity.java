package hozorghiyab;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hozorghiyab.R;

import hozorghiyab.activities.AboutmeACtKt;
import hozorghiyab.activities.Kharid_eshterakACT;
import hozorghiyab.listCityACT.ListcityACT;
import hozorghiyab.user_info.Main_user_login_activity;

public class OnclickMainActivity implements View.OnClickListener {
    Context c;
    public void click(Context c, Button btn_listcity, Button btn_5star, Button btn_aboutme,
                      Button btn_khari_eshterak){
        this.c = c;
        btn_listcity.setOnClickListener(this);
        btn_5star.setOnClickListener(this);
        btn_aboutme.setOnClickListener(this);
        btn_khari_eshterak.setOnClickListener(this);
    }

    public void clickCityDetail(Context c, ImageView imgNavigationMenu){
        this.c = c;
        imgNavigationMenu.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.txMenuSoalatMotadavel:
                Toast.makeText(c, "سوالات متداول", Toast.LENGTH_SHORT).show();
                break;

            /*case R.id.txMenuGhavanin:
                Toast.makeText(c, "قوانین", Toast.LENGTH_SHORT).show();
                break;*/

            case R.id.txMenuManabe:
                Toast.makeText(c, "منابع", Toast.LENGTH_SHORT).show();
                break;

            case R.id.txMenuAboutme:
                c.startActivity(new Intent(c,AboutmeACtKt.class));
                break;

        }}
}
