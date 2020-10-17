package hozorghiyab.cityDetail.placeComment;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.hozorghiyab.R;
public class RecyclerAdapterPlaceComment extends RecyclerView.Adapter<RecyclerAdapterPlaceComment.MyViewHolder> {

    private ArrayList<RecyclerModelPlaceComment> recyclerModels; // this data structure carries our title and description
    Context c;
    String rowLayoutType;
    int width;
    public RecyclerAdapterPlaceComment(ArrayList<RecyclerModelPlaceComment> recyclerModels,String rowLayoutType, Context c) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
    }


    public RecyclerAdapterPlaceComment(ArrayList<RecyclerModelPlaceComment> recyclerModels,String rowLayoutType, Context c,int width) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
        this.width = width;
    }

    @Override
    public RecyclerAdapterPlaceComment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        if (rowLayoutType.contains("place")){
            return new RecyclerAdapterPlaceComment.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment_place_act, parent, false));

        }else {
            return new RecyclerAdapterPlaceComment.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city_detail, parent, false));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final RecyclerAdapterPlaceComment.MyViewHolder holder,final int position) {

            Picasso.get()
                    .load(recyclerModels.get(position).getPicture())
                    .resize(width,0)
                    .into(holder.imgUser);

            holder.txName.setText(recyclerModels.get(position).getName());
            holder.txComment.setText(recyclerModels.get(position).getComment());

            Picasso.get()
                    .load(recyclerModels.get(position).getPicture())
                    .fit()
                    .into(holder.imgUser, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }


                        public void onError() {
                            Picasso.get()
                                    .load(R.drawable.usericon)
                                    .fit()
                                    .into(holder.imgUser);
                        }
                    });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String username = recyclerModels.get(position).getUsername();
                    String name = recyclerModels.get(position).getName();
                    String picture = recyclerModels.get(position).getPicture();
                    String comment = recyclerModels.get(position).getComment();

                    /*Intent intent = new Intent(c, PlaceACT.classpic);
                    intent.putExtra("onvan", onvan);
                    intent.putExtra("matn", matn);
                    intent.putExtra("picture", picture);
                    c.startActivity(intent);*/

                }
            });




    }

    @Override
    public int getItemCount() {
        return recyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txName;
        TextView txComment;
        ImageView imgUser;
        MyViewHolder(View view) {
            super(view);


            txName=  itemView.findViewById(R.id.txNameUserInComment);
            txComment=  itemView.findViewById(R.id.txComment);
            imgUser = itemView.findViewById(R.id.imgUserInComment);

        }
    }
}