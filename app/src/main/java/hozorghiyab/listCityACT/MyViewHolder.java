package hozorghiyab.listCityACT;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hozorghiyab.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView txSchoolName,txClassName;


    public MyViewHolder(View itemView) {
        super(itemView);
        txSchoolName = itemView.findViewById(R.id.txSchoolName);
        txClassName = itemView.findViewById(R.id.txClassName);
    }


}