package hozorghiyab.listCityACT;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.hozorghiyab.R;
public class CityAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<Contacts> list =new ArrayList<Contacts>();
    RecyclerView rv;
    public CityAdapter(Context c, ArrayList<Contacts> list, RecyclerView recyclerView) {
        this.c = c;
        this.list = list;
        this.rv = recyclerView;
    }

    public Object getItem(int position) {
        return list.get(position);

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.row_city_list,parent,false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String onvan = list.get(position).getSchoolName();
                /*Intent intent = new Intent(c, CityDetailACT.class);
                intent.putExtra("city",onvan);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                c.startActivity(intent);*/


            }
        });

        Contacts contacts = (Contacts) this.getItem(position);
        holder.txSchoolName.setText(contacts.getSchoolName());
        holder.txClassName.setText(contacts.getClassName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}