package hozorghiyab.cityDetail;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.hozorghiyab.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<RecyclerModel> recyclerModels; // this data structure carries our title and description
    Context c;
    String rowLayoutType;
    int width;
    public RecyclerAdapter(ArrayList<RecyclerModel> recyclerModels,String rowLayoutType, Context c) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
    }


    public RecyclerAdapter(ArrayList<RecyclerModel> recyclerModels,String rowLayoutType, Context c,int width) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
        this.width = width;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        if (rowLayoutType.contains("place")){
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_place, parent, false));

        }else {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city_detail, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.MyViewHolder holder,final int position) {



        if (rowLayoutType.contains("place")){

            Picasso.get()
                    .load(recyclerModels.get(position).getPicture())
                    .resize(width,0)
                    .into(holder.imageView);

        }else {

            holder.tx_onvan.setText(recyclerModels.get(position).getOnvan());
            holder.tx_position.setText(recyclerModels.get(position).getPosition());
            //holder.ratingBar.setRating((float)recyclerModels.get(position).getRate());
            holder.txCountRateAndComment.setText("( "+recyclerModels.get(position).getCountRateAndComment() + " نظر" + " )");


            Picasso.get()
                    .load(recyclerModels.get(position).getPicture())
                    .fit()
                    .into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = recyclerModels.get(position).getId();
                    String onvan = recyclerModels.get(position).getOnvan();
                    String matn = recyclerModels.get(position).getMatn();
                    String picture = recyclerModels.get(position).getPicture();
                    //float rate = recyclerModels.get(position).getRate();
                    Intent intent = new Intent(c, PlaceACT.class);
                    intent.putExtra("id", id);
                    intent.putExtra("onvan", onvan);
                    intent.putExtra("matn", matn);
                    intent.putExtra("picture", picture);
                    intent.putExtra("rate", "");
                    c.startActivity(intent);

                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return recyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txCountRateAndComment;;
        TextView tx_onvan;
        TextView tx_position;
        ImageView imageView;
        SimpleRatingBar ratingBar;
        MyViewHolder(View view) {
            super(view);


            tx_onvan=  itemView.findViewById(R.id.nameTxt);
            tx_position=  itemView.findViewById(R.id.txPostion);
            imageView = itemView.findViewById(R.id.imageView2);
            ratingBar=  itemView.findViewById(R.id.simpleRatingBar);
            txCountRateAndComment = itemView.findViewById(R.id.txCountRateAndComment);

        }
    }
}