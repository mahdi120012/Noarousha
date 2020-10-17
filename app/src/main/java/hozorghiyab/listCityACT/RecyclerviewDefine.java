package hozorghiyab.listCityACT;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

public class RecyclerviewDefine {

    public static void define(Context c,RecyclerView rv3){

        rv3.setHasFixedSize(true);
        rv3.setItemViewCacheSize(20);
        rv3.setDrawingCacheEnabled(true);
        rv3.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv3.setNestedScrollingEnabled(false);
        rv3.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //rv3.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, true));
    }
    //حط زیر برای درست کردن ریسایکلر ویوی افقیه
            //rv3.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, true));

}