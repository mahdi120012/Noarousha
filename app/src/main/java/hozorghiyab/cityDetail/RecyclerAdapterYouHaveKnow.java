package hozorghiyab.cityDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.util.DateInterval;
import android.os.Build;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import com.hozorghiyab.R;

import hozorghiyab.activities.ErsalVaziyatDarsiStudent;
import hozorghiyab.activities.HozorGhiyab;
import hozorghiyab.activities.NamayeshVaziyatDarsiStudent;
import hozorghiyab.activities.PvChat;
import hozorghiyab.activities.WriteNewMessage;
import hozorghiyab.activities.WriteSepordanKar;
import hozorghiyab.customClasses.CustomDialog;
import hozorghiyab.customClasses.EnglishNumberToPersian;
import hozorghiyab.customClasses.SharedPrefClass;
import hozorghiyab.customClasses.TimeKononi;

public class RecyclerAdapterYouHaveKnow extends RecyclerView.Adapter<RecyclerAdapterYouHaveKnow.MyViewHolder> {
    private String dateAsli = "";
    private String dateAsli2 = "";
    private String dateAsli3 = "";
    private String dateAsli4 = "";
    String staticTime = "00:00";

    private ArrayList<RecyclerModel> recyclerModels; // this data structure carries our title and description
    Context c;
    String rowLayoutType,className;
    RecyclerAdapterYouHaveKnow recyclerAdapterYouHaveKnow;
    ImageView img_refresh;
    WebView webView;
    Timer timer;
    TextView tx_state,txReciversList,txEntekhabHame;
    RecyclerView rvAddJalase;
    String jalaseId,sepordanKar, result;
    ConstraintLayout clShowErsal;
    final ArrayList<String> list_family = new ArrayList<String>();
    final ArrayList<String> list_id = new ArrayList<String>();

    public RecyclerAdapterYouHaveKnow(ArrayList<RecyclerModel> recyclerModels, String rowLayoutType, Context c,
                                      RecyclerAdapterYouHaveKnow recyclerAdapterYouHaveKnow,String className,TextView txReciversList,ConstraintLayout clShowErsal,TextView txEntekhabHame,String sepordanKar) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
        this.recyclerAdapterYouHaveKnow = recyclerAdapterYouHaveKnow;
        this.className = className;
        this.txReciversList = txReciversList;
        this.clShowErsal = clShowErsal;
        this.txEntekhabHame = txEntekhabHame;
        this.sepordanKar = sepordanKar;
    }

    public RecyclerAdapterYouHaveKnow(ArrayList<RecyclerModel> recyclerModels, String rowLayoutType, Context c,
                                      RecyclerAdapterYouHaveKnow recyclerAdapterYouHaveKnow,
                                      ImageView img_refresh,WebView webView,Timer timer,TextView tx_state,
                                      RecyclerView rvAddJalase,String className,String jalaseId) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
        this.recyclerAdapterYouHaveKnow = recyclerAdapterYouHaveKnow;
        this.className = className;
        this.img_refresh = img_refresh;
        this.webView = webView;
        this.timer = timer;
        this.tx_state = tx_state;
        this.rvAddJalase = rvAddJalase;
        this.jalaseId = jalaseId;

    }

    @Override
    public RecyclerAdapterYouHaveKnow.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        if (rowLayoutType.contains("add_class")){
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_class, parent, false));

        }else if (rowLayoutType.contains("add_student")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_student, parent, false));

        }else if (rowLayoutType.equals("recived_message")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_message, parent, false));

        }else if (rowLayoutType.equals("recived_message_sepordan_kar")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_message, parent, false));

        }else if (rowLayoutType.equals("recived_message_dakhel_sepordan_kar")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_message, parent, false));

        }else if (rowLayoutType.equals("pv_chat")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false));

        }else if (rowLayoutType.equals("search_recent")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_recent, parent, false));

        }else if (rowLayoutType.equals("recived_message_chat")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_message_chat, parent, false));

        }else if (rowLayoutType.contains("vorod_khoroj")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vorod_khoroj, parent, false));

        }else if (rowLayoutType.contains("sabt_makharej")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sabt_makharej, parent, false));

        }else if (rowLayoutType.contains("darkhast_morkhasi")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_darkhast_morkhasi, parent, false));

        }else if (rowLayoutType.contains("darkhast_jalase")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_darkhast_jalase, parent, false));

        }else if (rowLayoutType.contains("all_users_message")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_all_message, parent, false));

        }else if (rowLayoutType.equals("search")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search, parent, false));

        }else if (rowLayoutType.equals("search_chat")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_chat, parent, false));

        }else if (rowLayoutType.contains("dars_list")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dars_list, parent, false));

        }else if (rowLayoutType.contains("dars_student_to_teacher_list")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dars_list_for_send_student_message_to_teacher, parent, false));

        }else if (rowLayoutType.contains("add_jalase")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_jalase, parent, false));

        }else if (rowLayoutType.contains("add_dars_result_student")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_dars_result, parent, false));

        }else if (rowLayoutType.contains("add_hozorghiyab")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_hozorghiyab, parent, false));

        }else {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city_list, parent, false));

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"RecyclerView", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(final RecyclerAdapterYouHaveKnow.MyViewHolder holder,final int position) {



          if (rowLayoutType.contains("add_class")){

              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());
              holder.txTedadStudentInClass.setText("("+recyclerModels.get(position).getPosition()+" دانش آموز)");

              SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              String username = sharedPreferences.getString("user", "user");
              editor.commit();

              if (username.matches("100024") || username.matches("100025") ){
                  holder.imgRemoveClass.setVisibility(View.GONE);

              }

              holder.imgRemoveClass.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {


                      clickDialogItems(c,position,"remove_class",
                              null,"",recyclerModels,recyclerAdapterYouHaveKnow);



                      //holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
                      //holder.txClassName.setText(recyclerModels.get(position).getMatn());

                  }
              });

              Picasso.get()
                      .load(R.drawable.classimage)
                      .centerCrop()
                      .fit()
                      .into(holder.imageView);
          }else if (rowLayoutType.contains("add_student")){


              SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              String username = sharedPreferences.getString("user", "user");
              editor.commit();

              if (username.matches("100024") || username.matches("100025") ){
                  username = "100010";
                  holder.imgRemoveStudent.setVisibility(View.GONE);
              }


              if (recyclerModels.get(position).getPicture().isEmpty()) {

                  Picasso.get()
                          .load(R.drawable.usericon)
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgAddStudent);

              }else{
                  Picasso.get()
                          .load(recyclerModels.get(position).getPicture())
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgAddStudent);
              }



              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());

                holder.imgRemoveStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.removeStudent(c,recyclerModels.get(position).getId());

                        recyclerModels.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, recyclerModels.size());

                    }
                });



          }else if (rowLayoutType.contains("sabt_makharej")){
              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }


              holder.txDate2.setText(recyclerModels.get(position).getVaziyat());
              holder.txSaatVorod.setText(recyclerModels.get(position).getOnvan());
              holder.txSaatKhoroj.setText(recyclerModels.get(position).getPicture());
              holder.txNameFrestandeVorodKhoroj.setText(recyclerModels.get(position).getRate());
              holder.txOnvanRecevedMessage3.setText("مبلغ:");
              holder.txOnvanRecevedMessage5.setVisibility(View.GONE);
              holder.txSaatKhoroj.setVisibility(View.GONE);

              holder.txOnvanRecevedMessage7.setText("توضیحات:");
              holder.txMajmoKarkard.setText(recyclerModels.get(position).getWorkNumber());

              //Toast.makeText(c, formater.format(saatKhoroj-saatVorod) ,Toast.LENGTH_LONG).show();
              if (recyclerModels.get(position).getCity() != null){
                  if (recyclerModels.get(position).getCity().equals("تایید شده")){
                      holder.imgVaziyatTaeidVorodKhoroj.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getCity().equals("رد شده")){
                      holder.imgVaziyatTaeidVorodKhoroj.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }


              String noe = SharedPrefClass.getUserId(c,"noe");

              if (!noe.equals("admin")){

                  holder.txAzYaBeVorodKhoroj.setVisibility(View.GONE);
                  holder.txNameFrestandeVorodKhoroj.setVisibility(View.GONE);
              }


              if (dateAsli3.equals(recyclerModels.get(position).getOnvan())) {
                  holder.cardViewTxDate3.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli.setText(recyclerModels.get(position).getVaziyat());
                  dateAsli3 = recyclerModels.get(position).getOnvan();
              }
              String username = SharedPrefClass.getUserId(c,"user");


              if (recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain3.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txDate2.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain3.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txDate2.setTextColor(Color.parseColor("#a1aab3"));
              }


              if (noe.equals("admin")){
                  holder.clVorodKhoroj.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          allDialogButton(c,position,"taeid_gozaresh",
                                  holder.imgVaziyatTaeid,holder.txOnvanMessageInRecivedMessage,"saat_vorod_khoroj",
                                  list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                      }
                  });
              }else {
                  /*holder.clVorodKhoroj.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          CustomDialog.allDialogButton(c,position,"",holder.imgVaziyatTaeid,
                                  holder.txOnvanMessageInRecivedMessage,"saat_vorod_khoroj",
                                  list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow);
                      }
                  });*/

              }
          }else if (rowLayoutType.contains("vorod_khoroj")){
              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }


              holder.txDate2.setText(recyclerModels.get(position).getOnvan());
              holder.txSaatVorod.setText(recyclerModels.get(position).getMatn());
              holder.txSaatKhoroj.setText(recyclerModels.get(position).getPicture());
              holder.txNameFrestandeVorodKhoroj.setText(recyclerModels.get(position).getRate());
              holder.txEzafeKari.setText(recyclerModels.get(position).getWorkNumber());


              double saatVorod = 0.0;
              double saatKhoroj = 0.0;






              //saatVorod = Double.parseDouble(recyclerModels.get(position).getSaatVorodEnglish().replace(":","."));

              /*if (recyclerModels.get(position).getSaatKhorojEnglish().isEmpty() || recyclerModels.get(position).getSaatKhorojEnglish() == ""){

              }else {
                  //saatKhoroj = Double.parseDouble((recyclerModels.get(position).getSaatKhorojEnglish().replace(":",".")));


                  String faghatsaatVorodHoure = recyclerModels.get(position).getSaatVorodEnglish().substring(0,2);
                  String faghatsaatVorodMinute = recyclerModels.get(position).getSaatVorodEnglish().substring(3,5);

                  String faghatsaatKhorojHoure = recyclerModels.get(position).getSaatKhorojEnglish().substring(0,2);
                  String faghatsaatKhorojMinute = recyclerModels.get(position).getSaatKhorojEnglish ().substring(3,5);


                  //Toast.makeText(c, faghatsaatVorodHoure + "::"+ faghatsaatVorodMinute ,Toast.LENGTH_LONG).show();
                  long houseMilisecondSaatVorod = TimeUnit.HOURS.toMillis(Integer.parseInt(faghatsaatVorodHoure));
                  long minuteMilisecondSaatVorod = TimeUnit.MINUTES.toMillis(Integer.parseInt(faghatsaatVorodMinute));
                  long jamSaatVorod = (int) (houseMilisecondSaatVorod + minuteMilisecondSaatVorod);



                  long houseMilisecondSaatKhoroj = TimeUnit.HOURS.toMillis(Integer.parseInt(faghatsaatKhorojHoure));
                  long minuteMilisecondSaatKhoroj = TimeUnit.MINUTES.toMillis(Integer.parseInt(faghatsaatKhorojMinute));
                  long jamSaatKhoroj = (int) (houseMilisecondSaatKhoroj + minuteMilisecondSaatKhoroj);

                 // Toast.makeText(c, String.valueOf(jamSaatKhoroj-jamSaatVorod) ,Toast.LENGTH_LONG).show();
                  //Toast.makeText(c, String.valueOf(jamSaatKhoroj-jamSaatVorod) ,Toast.LENGTH_LONG).show();


                  LocalTime time = null;
                  LocalTime time2 = null;*/
                  holder.txMajmoKarkard.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getPosition()));

                  /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                      time = LocalTime.of(Integer.parseInt(faghatsaatKhorojHoure), Integer.parseInt(faghatsaatKhorojMinute), 00);

                      // Subtract hours, minutes, or seconds
                      LocalTime updatedTime = time.minusHours(Integer.parseInt(faghatsaatVorodHoure)).minusMinutes(Integer.parseInt(faghatsaatVorodMinute)).minusSeconds(00);
                      //LocalTime updatedTime = time.plusHours(Integer.parseInt(faghatsaatVorodHoure)).plusHours(Integer.parseInt(faghatsaatVorodMinute)).minusSeconds(00);
                      //updatedTimeStatic = updatedTime;

                      //holder.txMajmoKarkard.setText(new EnglishNumberToPersian().convert(updatedTime.toString()));
                      holder.txMajmoKarkard.setText(new EnglishNumberToPersian().convert(updatedTime.toString()));

                      //staticTime = holder.txMajmoKarkard.getText().toString();

                      String faghat_saatKol = holder.txMajmoKarkard.getText().toString().substring(0,2);
                      String faghat_daghigheKol = holder.txMajmoKarkard.getText().toString().substring(3,5);



                      *//*String a =
                      String faghatSaatStatic = staticTime.substring(0,2);
                      String faghatDaghigheStatic = staticTime.substring(3,5);



                      time2 = LocalTime.of(Integer.parseInt(faghatSaatStatic), Integer.parseInt(faghatDaghigheStatic), 00);
                      LocalTime updatedTime2 = time2.plusHours(Integer.parseInt(faghat_saatKol)).plusMinutes(Integer.parseInt(faghat_daghigheKol)).plusSeconds(00);
                      holder.txMajmoKarkard.setText(new EnglishNumberToPersian().convert(updatedTime2.toString()));

                      staticTime = holder.txMajmoKarkard.getText().toString();
*//*

                  }*/








              //Toast.makeText(c, formater.format(saatKhoroj-saatVorod) ,Toast.LENGTH_LONG).show();
              if (recyclerModels.get(position).getCity() != null){
                  if (recyclerModels.get(position).getCity().equals("تایید شده")){
                      holder.imgVaziyatTaeidVorodKhoroj.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getCity().equals("رد شده")){
                      holder.imgVaziyatTaeidVorodKhoroj.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }


              String noe = SharedPrefClass.getUserId(c,"noe");

              if (!noe.equals("admin")){

                  holder.txAzYaBeVorodKhoroj.setVisibility(View.GONE);
                  holder.txNameFrestandeVorodKhoroj.setVisibility(View.GONE);
              }


              if (dateAsli3.equals(recyclerModels.get(position).getOnvan())) {
                  holder.cardViewTxDate3.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli.setText(recyclerModels.get(position).getOnvan());
                  dateAsli3 = recyclerModels.get(position).getOnvan();
              }
              String username = SharedPrefClass.getUserId(c,"user");


              if (recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain3.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txDate2.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain3.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txDate2.setTextColor(Color.parseColor("#a1aab3"));
              }


              if (noe.equals("admin")){
                  holder.clVorodKhoroj.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          allDialogButton(c,position,"taeid_gozaresh",
                                  holder.imgVaziyatTaeid,holder.txOnvanMessageInRecivedMessage,"saat_vorod_khoroj",
                                  list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                      }
                  });
              }else {
                  /*holder.clVorodKhoroj.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          CustomDialog.allDialogButton(c,position,"",holder.imgVaziyatTaeid,
                                  holder.txOnvanMessageInRecivedMessage,"saat_vorod_khoroj",
                                  list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow);
                      }
                  });*/
              }

          }else if (rowLayoutType.contains("darkhast_jalase")){

              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }

              final SpannableStringBuilder sb = new SpannableStringBuilder("جلسه " + new EnglishNumberToPersian().convert(recyclerModels.get(position).getCountRateAndComment()) + " موضوع:");

              final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
              sb.setSpan(bss, 0, 5 + recyclerModels.get(position).getCountRateAndComment().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold



              holder.txMatnMozo.setText(sb);
              holder.txTarikhDarkhast.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getOnvan()));
              holder.txDarTarikh.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getMatn().substring(0,11)));
              holder.txSaat.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getMatn().substring(11,16)));

              //holder.txTaTarikh.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getPicture()));
              holder.txMozo.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getCity()));
              holder.txMokhatabin.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getPicture()));

              holder.txMokhatabin.setText(recyclerModels.get(position).getPicture());
              holder.txMakan.setText(recyclerModels.get(position).getVaziyat());
              holder.txTozihat.setText(recyclerModels.get(position).getWorkNumber());

              if (recyclerModels.get(position).getPosition() != null){
                  if (recyclerModels.get(position).getPosition().equals("تایید شده")){
                      holder.imgVaziyatTaeidMorkhasi.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getPosition().equals("رد شده")){
                      holder.imgVaziyatTaeidMorkhasi.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }

              if (dateAsli4.equals(recyclerModels.get(position).getOnvan())) {
                  holder.cardViewTxDate4.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli4.setText(recyclerModels.get(position).getOnvan());
                  dateAsli4 = recyclerModels.get(position).getOnvan();
              }

              String username = SharedPrefClass.getUserId(c,"user");

              if (recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain4.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txTarikhDarkhast.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain4.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txTarikhDarkhast.setTextColor(Color.parseColor("#a1aab3"));
              }

              String noe = SharedPrefClass.getUserId(c,"noe");

              if (!noe.equals("admin")){
                  holder.txAzYaBeMorkhasi.setVisibility(View.GONE);
                  holder.txNameFerestandeMorkhasi.setVisibility(View.GONE);
              }
              holder.txNameFerestandeMorkhasi.setText(recyclerModels.get(position).getRate());

              if (noe.equals("admin")){
                  holder.clDarkhastMorkhasi.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          allDialogButton(c,position,"taeid_gozaresh",
                                  holder.imgVaziyatTaeidMorkhasi,holder.txOnvanMessageInRecivedMessage,
                                  "darkhast_jalase",list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                      }
                  });
              }else {
                  /*holder.clDarkhastMorkhasi.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          CustomDialog.allDialogButton(c,position,"",holder.imgVaziyatTaeidMorkhasi,
                                  holder.txOnvanMessageInRecivedMessage,"darkhasti_morkhasi",
                                  list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow);
                      }
                  });*/
              }



          }else if (rowLayoutType.contains("darkhast_morkhasi")){

              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }
              holder.txTarikhDarkhast.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getOnvan()));
              holder.txAzTarikh.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getMatn()));
              holder.txTaTarikh.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getPicture()));
              holder.txElat.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getCity()));

              if (recyclerModels.get(position).getPosition() != null){
                  if (recyclerModels.get(position).getPosition().equals("تایید شده")){
                      holder.imgVaziyatTaeidMorkhasi.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getPosition().equals("رد شده")){
                      holder.imgVaziyatTaeidMorkhasi.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }

              if (dateAsli4.equals(recyclerModels.get(position).getOnvan())) {
                  holder.cardViewTxDate4.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli4.setText(recyclerModels.get(position).getOnvan());
                  dateAsli4 = recyclerModels.get(position).getOnvan();
              }

              String username = SharedPrefClass.getUserId(c,"user");

              if (recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain4.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txTarikhDarkhast.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getCountRateAndComment().equals(username)){
                  holder.cardMain4.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txTarikhDarkhast.setTextColor(Color.parseColor("#a1aab3"));
              }

              String noe = SharedPrefClass.getUserId(c,"noe");

              if (!noe.equals("admin")){
                  holder.txAzYaBeMorkhasi.setVisibility(View.GONE);
                  holder.txNameFerestandeMorkhasi.setVisibility(View.GONE);
              }
              holder.txNameFerestandeMorkhasi.setText(recyclerModels.get(position).getRate());

              if (noe.equals("admin")){
                  holder.clDarkhastMorkhasi.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          allDialogButton(c,position,"taeid_gozaresh",
                                  holder.imgVaziyatTaeidMorkhasi,holder.txOnvanMessageInRecivedMessage,
                                  "darkhasti_morkhasi",list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                      }
                  });
              }else {
                  /*holder.clDarkhastMorkhasi.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          CustomDialog.allDialogButton(c,position,"",holder.imgVaziyatTaeidMorkhasi,
                                  holder.txOnvanMessageInRecivedMessage,"darkhasti_morkhasi",
                                  list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow);
                      }
                  });*/
              }


          }else if (rowLayoutType.equals("pv_chat")){
             clShowErsal.setVisibility(View.VISIBLE);
             holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              //holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              //holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txDate.setText(recyclerModels.get(position).getPicture());

              if (dateAsli.equals(recyclerModels.get(position).getPicture())) {
                  holder.cardViewTxDate.setVisibility(View.GONE);
                  dateAsli = recyclerModels.get(position).getPicture();
              }else if (!dateAsli.equals(recyclerModels.get(position).getPicture())){
                  holder.cardViewTxDate.setVisibility(View.VISIBLE);
                  holder.txDateAsli.setText(recyclerModels.get(position).getPicture());
                  dateAsli = recyclerModels.get(position).getPicture();
              }

              String username = SharedPrefClass.getUserId(c,"user");

              DisplayMetrics displaymetrics = new DisplayMetrics();
              ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
              int width = displaymetrics.widthPixels;
              holder.txOnvanMessageInRecivedMessage.setMaxWidth(width - (width/(5)));
              if (recyclerModels.get(position).getRate().equals(username)){
                  holder.cardViewChat.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.linerLayoutInRowChat.setGravity(Gravity.RIGHT);
                  holder.txDate.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getRate().equals(username)){
                  holder.cardViewChat.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.linerLayoutInRowChat.setGravity(Gravity.LEFT);
                  holder.txDate.setTextColor(Color.parseColor("#a1aab3"));
              }

              holder.cardViewChat.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      allDialogButtonForPvChat(c,position,recyclerModels,recyclerAdapterYouHaveKnow);
                  }
              });


              /*if (recyclerModels.get(position).getCountRateAndComment()!=""){
                  holder.imgReadOrNo.setVisibility(View.VISIBLE);

                  if (recyclerModels.get(position).getCountRateAndComment().equals("0")){
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.not_read));
                  }else {
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.read));
                  }
              }*/



              if(holder.txDate.getText().toString().isEmpty()){
                  holder.txDate.setVisibility(View.GONE);
              }

          }else if (rowLayoutType.equals("search_recent")){

              holder.txNameFerestande.setText(recyclerModels.get(position).getCity());
              holder.txMokhaffafName.setText(recyclerModels.get(position).getCity().substring(0,1));

              holder.cardViewUnderUserPicture.setVisibility(View.VISIBLE);
              int[] androidColors = c.getResources().getIntArray(R.array.androidcolors);
              int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
              holder.cardViewUnderUserPicture1.setBackgroundColor(randomAndroidColor);

              holder.clMainInChat.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(c, PvChat.class);
                      intent.putExtra("mokhatab_id", recyclerModels.get(position).getRate());
                      intent.putExtra("name_mokhatab", recyclerModels.get(position).getCity());
                      c.startActivity(intent);
                  }
              });

          }else if (rowLayoutType.equals("search_chat")){
             holder.txNameFerestandeInSearch.setText(recyclerModels.get(position).getMatn());
              //holder.txMatnPayam.setText(recyclerModels.get(position).getOnvan());

             holder.txMokhaffafName.setText(recyclerModels.get(position).getMatn().substring(0,1));

              holder.cardViewUnderUserPicture.setVisibility(View.VISIBLE);
              int[] androidColors = c.getResources().getIntArray(R.array.androidcolors);
              int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
              holder.cardViewUnderUserPicture1.setBackgroundColor(randomAndroidColor);

              holder.clMainInChat.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(c, PvChat.class);
                      intent.putExtra("mokhatab_id", recyclerModels.get(position).getId());
                      intent.putExtra("name_mokhatab", recyclerModels.get(position).getMatn());
                      c.startActivity(intent);
                  }
              });



          }else if (rowLayoutType.equals("recived_message_chat")){

              holder.txNameFerestande.setText(recyclerModels.get(position).getCity());
              holder.txMatnPayam.setText(recyclerModels.get(position).getOnvan());
              holder.txDateInChat.setText(recyclerModels.get(position).getPicture());


              holder.txMokhaffafName.setText(recyclerModels.get(position).getCity().substring(0,1));

              holder.cardViewUnderUserPicture.setVisibility(View.VISIBLE);
              int[] androidColors = c.getResources().getIntArray(R.array.androidcolors);
              int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
              holder.cardViewUnderUserPicture1.setBackgroundColor(randomAndroidColor);

              holder.clMainInChat.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(c, PvChat.class);
                      intent.putExtra("mokhatab_id", recyclerModels.get(position).getRate());
                      intent.putExtra("name_mokhatab", recyclerModels.get(position).getCity());
                      c.startActivity(intent);


                  }
              });



          }else if (rowLayoutType.equals("recived_message_dakhel_sepordan_kar")){
              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }
              String noe = SharedPrefClass.getUserId(c,"noe");
              if (noe.equals("student")){
                  holder.clVaziyatTaeid.setVisibility(View.GONE);
              }

              if (className.equals("payam_haye_ersali")){
                  holder.txAzYaBe.setText("به");
              }else {
                  holder.txAzYaBe.setText("از");
              }

              if (className.equals("pv")){
                  holder.txAzYaBe.setVisibility(View.GONE);
                  holder.txNameFerestandeInRecivedMessage.setVisibility(View.GONE);
              }

              holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txDate.setText(recyclerModels.get(position).getPicture());

              if (recyclerModels.get(position).getVaziyat() != null){
                  if (recyclerModels.get(position).getVaziyat().equals("تایید شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getVaziyat().equals("رد شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }

              String username = SharedPrefClass.getUserId(c,"user");

              if (recyclerModels.get(position).getRate().equals(username)){
                  holder.cardMain.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txDate.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getRate().equals(username)){
                  holder.cardMain.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txDate.setTextColor(Color.parseColor("#a1aab3"));
              }


              if (dateAsli2.equals(recyclerModels.get(position).getPicture())) {
                  holder.cardViewTxDate2.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli2.setText(recyclerModels.get(position).getPicture());
                  dateAsli2 = recyclerModels.get(position).getPicture();
              }

              if (recyclerModels.get(position).getCountRateAndComment()!=""){
                  holder.imgReadOrNo.setVisibility(View.VISIBLE);

                  if (recyclerModels.get(position).getCountRateAndComment().equals("0")){
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.not_read));
                  }else {
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.read));
                  }
              }

              if(holder.txDate.getText().toString().isEmpty()){
                  holder.txDate.setVisibility(View.GONE);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("gozaresh_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("گزارش کار: " + recyclerModels.get(position).getWorkNumber() + " :" +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
                  holder.clVaziyatTaeid.setVisibility(View.VISIBLE);
              }

             int a = Integer.parseInt(recyclerModels.get(position).getWorkNumber()) -1;

              final SpannableStringBuilder sb2 = new SpannableStringBuilder("گزارش کار " + String.valueOf(a) + " :" +recyclerModels.get(position).getOnvan());

              final StyleSpan bss2 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
              sb2.setSpan(bss2, 0, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

              holder.txOnvanMessageInRecivedMessage.setText(sb2);
              holder.clVaziyatTaeid.setVisibility(View.VISIBLE);


              if(recyclerModels.get(position).getPosition().toString().contains("ahkam")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("احکام: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("کار " + recyclerModels.get(position).getWorkNumber() + " :" +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);

              }

              if (noe.equals("admin")){
                  holder.clMain.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          allDialogButton(c,position,"taeid_gozaresh",
                                  holder.imgVaziyatTaeid,holder.txOnvanMessageInRecivedMessage,
                                  null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                      }
                  });
              }else {
                  holder.clMain.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          allDialogButton(c,position,"",holder.imgVaziyatTaeid,
                                  holder.txOnvanMessageInRecivedMessage,null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());
                      }
                  });
              }



          }else if (rowLayoutType.equals("recived_message_sepordan_kar")){
              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }
              String noe = SharedPrefClass.getUserId(c,"noe");
              if (noe.equals("student")){
                  holder.clVaziyatTaeid.setVisibility(View.GONE);
              }

              if (className.equals("payam_haye_ersali")){
                  holder.txAzYaBe.setText("به");
              }else {
                  holder.txAzYaBe.setText("از");
              }

              if (className.equals("pv")){
                  holder.txAzYaBe.setVisibility(View.GONE);
                  holder.txNameFerestandeInRecivedMessage.setVisibility(View.GONE);
              }

              //کد زیر برای تغییر بک گراند پیامهای خوانده نشده استفاده میشه ولی هنوز مشکل داره
              /*if (recyclerModels.get(position).getSaatKhorojEnglish().equals("0")){
                  holder.clMain.setBackgroundColor(Color.parseColor("#efefef"));
              }else if (recyclerModels.get(position).getSaatKhorojEnglish().equals("1")){
                  holder.clMain.setBackgroundColor(Color.parseColor("#ffffff"));
              }*/

              holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txDate.setText(recyclerModels.get(position).getPicture());

              if (recyclerModels.get(position).getVaziyat() != null){
                  if (recyclerModels.get(position).getVaziyat().equals("تایید شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getVaziyat().equals("رد شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }

              String username = SharedPrefClass.getUserId(c,"user");

              if (recyclerModels.get(position).getRate().equals(username)){
                  holder.cardMain.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txDate.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getRate().equals(username)){
                  holder.cardMain.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txDate.setTextColor(Color.parseColor("#a1aab3"));
              }


              if (dateAsli2.equals(recyclerModels.get(position).getPicture())) {
                  holder.cardViewTxDate2.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli2.setText(recyclerModels.get(position).getPicture());
                  dateAsli2 = recyclerModels.get(position).getPicture();
              }

              if (recyclerModels.get(position).getCountRateAndComment()!=""){
                  holder.imgReadOrNo.setVisibility(View.VISIBLE);

                  if (recyclerModels.get(position).getCountRateAndComment().equals("0")){
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.not_read));
                  }else {
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.read));
                  }
              }

              if(holder.txDate.getText().toString().isEmpty()){
                  holder.txDate.setVisibility(View.GONE);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("gozaresh_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("گزارش کار " + recyclerModels.get(position).getWorkNumber() + " :" +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
                  holder.clVaziyatTaeid.setVisibility(View.VISIBLE);
              }


              if(recyclerModels.get(position).getPosition().toString().contains("ahkam")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("احکام: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){

                  String persianWorkNumber = new EnglishNumberToPersian().convert(recyclerModels.get(position).getWorkNumber());
                  final SpannableStringBuilder sb = new SpannableStringBuilder("کار " + persianWorkNumber + ": " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, persianWorkNumber.length() + 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);


                      holder.clMain.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              allDialogButton(c,position,"gozaresh_kar",
                                      holder.imgVaziyatTaeid,holder.txOnvanMessageInRecivedMessage,
                                      null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                          }
                      });

              }else {
                  if (noe.equals("admin")){
                      holder.clMain.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              allDialogButton(c,position,"taeid_gozaresh",
                                      holder.imgVaziyatTaeid,holder.txOnvanMessageInRecivedMessage,
                                      null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                          }
                      });
                  }else {
                      holder.clMain.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              allDialogButton(c,position,"",holder.imgVaziyatTaeid,
                                      holder.txOnvanMessageInRecivedMessage,null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());
                          }
                      });
                  }
              }




          }else if (rowLayoutType.equals("recived_message")){
              if (clShowErsal != null) {
                  clShowErsal.setVisibility(View.GONE);
              }
              String noe = SharedPrefClass.getUserId(c,"noe");
              if (noe.equals("student")){
                  holder.clVaziyatTaeid.setVisibility(View.GONE);
              }

              if (className.equals("payam_haye_ersali")){
                  holder.txAzYaBe.setText("به");
              }else {
                  holder.txAzYaBe.setText("از");
              }

              if (className.equals("pv")){
                  holder.txAzYaBe.setVisibility(View.GONE);
                  holder.txNameFerestandeInRecivedMessage.setVisibility(View.GONE);
              }

              holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txDate.setText(recyclerModels.get(position).getPicture());

              if (recyclerModels.get(position).getVaziyat() != null){
                  if (recyclerModels.get(position).getVaziyat().equals("تایید شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getVaziyat().equals("رد شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }

              String username = SharedPrefClass.getUserId(c,"user");

              if (recyclerModels.get(position).getRate().equals(username)){
                  holder.cardMain.setCardBackgroundColor(Color.parseColor("#efffde"));
                  holder.txDate.setTextColor(Color.parseColor("#70b15c"));

              }else if (!recyclerModels.get(position).getRate().equals(username)){
                  holder.cardMain.setCardBackgroundColor(Color.parseColor("#ffffff"));
                  holder.txDate.setTextColor(Color.parseColor("#a1aab3"));
              }


              if (dateAsli2.equals(recyclerModels.get(position).getPicture())) {
                  holder.cardViewTxDate2.setVisibility(View.GONE);
              }else {
                  holder.txDateAsli2.setText(recyclerModels.get(position).getPicture());
                  dateAsli2 = recyclerModels.get(position).getPicture();
              }

              if (recyclerModels.get(position).getCountRateAndComment()!=""){
                  holder.imgReadOrNo.setVisibility(View.VISIBLE);

                  if (recyclerModels.get(position).getCountRateAndComment().equals("0")){
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.not_read));
                  }else {
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.read));
                  }
              }

              if(holder.txDate.getText().toString().isEmpty()){
                  holder.txDate.setVisibility(View.GONE);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("gozaresh_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("گزارش کار: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
                  holder.clVaziyatTaeid.setVisibility(View.VISIBLE);
              }



              if(recyclerModels.get(position).getPosition().toString().contains("ahkam")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("احکام: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("کار: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);

              }

              if (noe.equals("admin")){
                  holder.clMain.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          allDialogButton(c,position,"taeid_gozaresh",
                                  holder.imgVaziyatTaeid,holder.txOnvanMessageInRecivedMessage,
                                  null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());

                      }
                  });
              }else {
                  holder.clMain.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          allDialogButton(c,position,"",holder.imgVaziyatTaeid,
                                  holder.txOnvanMessageInRecivedMessage,null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,recyclerModels.get(position).getId());
                      }
                  });
              }




          }else if (rowLayoutType.contains("all_users_message")){

              holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txNameGirandehInRecivedMessage.setText(recyclerModels.get(position).getPosition());
              holder.txDate1.setText(recyclerModels.get(position).getRate());

          }else if (rowLayoutType.contains("search")){

              final String stdId = recyclerModels.get(position).getOnvan();
              final String stdFamily = recyclerModels.get(position).getMatn();

              if (recyclerModels.get(position).getPicture().isEmpty()) {

                  Picasso.get()
                          .load(R.drawable.usericon)
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgUserPictureForSendMessageInTeacher);

              }else{
                  Picasso.get()
                          .load(recyclerModels.get(position).getPicture())
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgUserPictureForSendMessageInTeacher);
              }


              holder.txUserNameInSearchInTeacher.setText(stdFamily);
              holder.cardMain2.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {


                      if (holder.imgChoiseReciverSendNewMessageInTeacher.getVisibility() == View.VISIBLE){
                          list_family.remove(stdFamily);
                          list_id.remove(stdId);

                          if (list_family.size() <= 0){
                              clShowErsal.setVisibility(View.GONE);

                          }

                          String s = "";
                          for (int i = 0; i < list_family.size(); i++) {
                              s += list_family.get(i) + ", ";
                          }
                          txReciversList.setText(s);

                          holder.imgChoiseReciverSendNewMessageInTeacher.setVisibility(View.GONE);
                          holder.imgChoiseUserInSearchInTeacher.setVisibility(View.VISIBLE);
                      }else {
                          list_family.add(stdFamily);
                          list_id.add(stdId);

                          String s = "";
                          for (int i = 0; i < list_family.size(); i++) {
                              s += list_family.get(i) + ", ";
                          }
                          txReciversList.setText(s);

                          clShowErsal.setVisibility(View.VISIBLE);
                          holder.imgChoiseReciverSendNewMessageInTeacher.setVisibility(View.VISIBLE);
                          holder.imgChoiseUserInSearchInTeacher.setVisibility(View.GONE);
                      }

                  }
              });

              holder.imgChoiseReciverSendNewMessageInTeacher.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      list_family.remove(stdFamily);
                      list_id.remove(stdId);

                      if (list_family.size() <= 0){
                          clShowErsal.setVisibility(View.GONE);

                      }

                      String s = "";
                      for (int i = 0; i < list_family.size(); i++) {
                          s += list_family.get(i) + ", ";
                      }
                      txReciversList.setText(s);

                      holder.imgChoiseReciverSendNewMessageInTeacher.setVisibility(View.GONE);
                      holder.imgChoiseUserInSearchInTeacher.setVisibility(View.VISIBLE);


                  }
              });

              clShowErsal.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      if (sepordanKar != null){
                          Intent intent = new Intent(c, WriteSepordanKar.class);
                          intent.putStringArrayListExtra("id",list_id);
                          intent.putStringArrayListExtra("family",list_family);
                          intent.putExtra("ahkam",className);
                          intent.putExtra("sepordan_kar",sepordanKar);
                          c.startActivity(intent);
                      }else {
                          Intent intent = new Intent(c, WriteNewMessage.class);
                          intent.putStringArrayListExtra("id",list_id);
                          intent.putStringArrayListExtra("family",list_family);
                          intent.putExtra("ahkam",className);
                          intent.putExtra("sepordan_kar",sepordanKar);
                          c.startActivity(intent);
                      }


                      String stdId = recyclerModels.get(position).getOnvan();

                  }
              });

          }else if (rowLayoutType.contains("dars_list")){

              holder.txNameDars.setText(recyclerModels.get(position).getOnvan());
              holder.txCountHazeri.setText(recyclerModels.get(position).getMatn()+" حضور");
              holder.txCountGheybat.setText(recyclerModels.get(position).getCity()+" غیبت");

              holder.txVaziyatDarsiVaAkhlaghi.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String darsiId = recyclerModels.get(position).getPosition();

                      Intent intent = new Intent(c, NamayeshVaziyatDarsiStudent.class);
                      intent.putExtra("dars_id", darsiId);
                      intent.putExtra("class_name", recyclerModels.get(position).getOnvan());
                      c.startActivity(intent);
                  }
              });

          }else if (rowLayoutType.contains("dars_student_to_teacher_list")){

              final String className = recyclerModels.get(position).getOnvan();
              final String teacherName = recyclerModels.get(position).getRate();
              final String teacherId = recyclerModels.get(position).getCountRateAndComment();
              holder.txNameDars.setText(className + " (" + teacherName +")");

              holder.clVaziyatDarsi.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(c, ErsalVaziyatDarsiStudent.class);
                      intent.putExtra("teacher_id", teacherId);
                      intent.putExtra("class_name", className + " (" + teacherName +")");
                      c.startActivity(intent);
                  }
              });

          }else if (rowLayoutType.contains("add_jalase")){


              SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              String username = sharedPreferences.getString("user", "user");
              editor.commit();

              if (username.matches("100024") || username.matches("100025") ){
                  holder.imgRemoveJalase.setVisibility(View.GONE);
              }

              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());
              holder.txTedadHazerinInLinsJalasat.setText("تعداد حاظران "+recyclerModels.get(position).getPosition()+" نفر");
              holder.txTedadGhayebinInListJalasat.setText("تعداد غایبان "+recyclerModels.get(position).getCountRateAndComment()+" نفر");

              holder.cl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String jalaseId = recyclerModels.get(position).getId();
                      String jalaseName = recyclerModels.get(position).getOnvan();
                      String classId = recyclerModels.get(position).getCity();
                      String className = recyclerModels.get(position).getMatn();

                      Intent intent = new Intent(c, HozorGhiyab.class);
                      intent.putExtra("jalase_id", jalaseId);
                      intent.putExtra("jalase_name", jalaseName);

                      intent.putExtra("class_id", classId);
                      intent.putExtra("class_name", className);
                      c.startActivity(intent);
                  }
              });

              holder.imgRemoveJalase.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      clickDialogItems(c,position,"remove_jalase",null,"",recyclerModels,recyclerAdapterYouHaveKnow);
                  }
              });

          }else if (rowLayoutType.contains("add_dars_result_student")){

              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              if (recyclerModels.get(position).getPicture().matches("1")  || recyclerModels.get(position).getPicture().matches("حاضر")){
                  holder.txClassName.setText("حاضر");
              }else {
                  holder.txClassName.setText("غایب");
              }
              holder.txTedadHazerinInLinsJalasat.setText("وضعیت درسی: "+recyclerModels.get(position).getCity());
              holder.txTedadGhayebinInListJalasat.setText("وضعیت اخلاقی: "+recyclerModels.get(position).getPosition());
              holder.txTaklif.setText("تکلیف: "+recyclerModels.get(position).getRate());


              holder.cl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String jalaseId = recyclerModels.get(position).getId();
                      String jalaseName = recyclerModels.get(position).getOnvan();
                      String classId = recyclerModels.get(position).getCity();
                      String className = recyclerModels.get(position).getMatn();

                      Intent intent = new Intent(c, HozorGhiyab.class);
                      intent.putExtra("jalase_id", jalaseId);
                      intent.putExtra("jalase_name", jalaseName);

                      intent.putExtra("class_id", classId);
                      intent.putExtra("class_name", className);
                      c.startActivity(intent);
                  }
              });


          }else if (rowLayoutType.contains("add_hozorghiyab")){


              holder.etTimeTakhirStudent.setText(recyclerModels.get(position).getPosition());
              holder.etVaziyatDarsiStudent.setText(recyclerModels.get(position).getRate());
              holder.etVaziyatAkhlaghiStudent.setText(recyclerModels.get(position).getCountRateAndComment());

              holder.etTimeTakhirStudent.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                      LoadData.updateStudentInHozorGhiyab_timeTakhir(c,
                              recyclerModels.get(position).getId(),
                              recyclerModels,position,s.toString());


                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });
              holder.etVaziyatDarsiStudent.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }
                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                      LoadData.updateStudentInHozorGhiyab_VaziyatDarsi(c,
                              recyclerModels.get(position).getId(),
                              recyclerModels,position,s.toString());
                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });

              holder.etVaziyatAkhlaghiStudent.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                      LoadData.updateStudentInHozorGhiyab_VaziyatAkhlaghi(c,
                              recyclerModels.get(position).getId(),
                              recyclerModels,position,s.toString());


                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });

              if (recyclerModels.get(position).getPicture().isEmpty()) {

                  Picasso.get()
                          .load(R.drawable.usericon)
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgHozorGhiyab);

              }else{
                  Picasso.get()
                          .load(recyclerModels.get(position).getPicture())
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgHozorGhiyab);
              }

              holder.txStudentNameInHozorGhiyab.setText(recyclerModels.get(position).getOnvan());

              if (recyclerModels.get(position).getMatn().contains("1")){
                  holder.imgHazerGhayebTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.hazertik));
              }else{
                  holder.imgHazerGhayebTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ghayebtik));
              }

              holder.clHazerGhayebTik.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                        if (recyclerModels.get(position).getMatn().contains("1")){
                            //update hazeri to ghayeb
                            LoadData.updateStudentInHozorGhiyab(c,recyclerModels.get(position).getId(),holder.imgHazerGhayebTik,recyclerModels,position);

                        }else {
                            //update ghayebbi to hazeri
                            LoadData.updateStudentInHozorGhiyabGhayebToHazer(c,recyclerModels.get(position).getId(),holder.imgHazerGhayebTik,recyclerModels,position);
                        }

                  }
              });

         /*     holder.spinnerTakhirStudent.setAdapter(spinnerArrayAdapter);
              holder.spinnerTakhirStudent.setSelection(recyclerModels.get(position).getIdTimeTakhir());
              holder.spinnerTakhirStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      String selectedItem = adapterView.getItemAtPosition(i).toString();
                          LoadData.updateStudentInHozorGhiyab_timeTakhir(c,
                                  recyclerModels.get(position).getId(),
                                  recyclerModels,position,selectedItem);
                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {
                  }
              });*/

          }else {
              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());

              Picasso.get()
                      .load(R.drawable.usericon)
                      .centerCrop()
                      .fit()
                      .into(holder.imageView);
          }

    }

    @Override
    public int getItemCount() {
        return recyclerModels.size();
    }



    public void onItemRemoved(ArrayList<RecyclerModel> arrObjects){
        recyclerModels = arrObjects;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txSchoolName,txClassName,txStudentNameInHozorGhiyab,
                 txNameDars,txCountHazeri,txCountGheybat,txTedadStudentInClass,
                 txTedadHazerinInLinsJalasat,txTedadGhayebinInListJalasat,
                 txOnvanMessageInRecivedMessage,txMatnMessageInRecivedMessage,
                 txNameFerestandeInRecivedMessage,txNameGirandehInRecivedMessage,
                 txUserNameInSearchInTeacher,txVaziyatDarsiVaAkhlaghi,txTaklif,txDate,
                 txDate2,txSaatVorod,txSaatKhoroj,txDate1,txTarikhDarkhast,txAzTarikh,txTaTarikh,txElat,
                 txAzYaBe,txAzYaBeVorodKhoroj,txNameFrestandeVorodKhoroj,txAzYaBeMorkhasi,
                 txNameFerestandeMorkhasi,txNameFerestande,txMatnPayam,txDateInChat,txMokhaffafName,
                 txNameFerestandeInSearch,txDateAsli,txDateAsli2,txDateAsli3,txDateAsli4,
                 txMozo,txDarTarikh,txMokhatabin,txMakan,txTozihat,txMatnMozo,txMajmoKarkard,txOnvanRecevedMessage3
                ,txOnvanRecevedMessage5,txOnvanRecevedMessage7,txSaat,txEzafeKari;

        ImageView imageView,imgRemoveStudent,imgRemoveJalase,imgRemoveClass,imgErsalNazarIcon,
        imgHazerGhayebTik,imgChoiseUserInSearchInTeacher,imgChoiseReciverSendNewMessageInTeacher,imgAddStudent,imgVaziyatTaeid,
        imgHozorGhiyab,imgUserPictureForSendMessageInTeacher,imgReadOrNo,imgVaziyatTaeidVorodKhoroj,
        imgVaziyatTaeidMorkhasi,imgUserPicture;
        ConstraintLayout cl,clHazerGhayebTik,clVaziyatDarsi,clVaziyatTaeid,clMain,clVorodKhoroj,clDarkhastMorkhasi,clMainInChat
                ,clMainInRowChat;
        Spinner spinnerTakhirStudent;
        EditText etTimeTakhirStudent,etVaziyatDarsiStudent,etVaziyatAkhlaghiStudent;
        CardView cardViewUnderUserPicture,cardViewUnderUserPicture1,cardViewChat,cardViewTxDate,cardViewTxDate2
                ,cardViewTxDate3,cardViewTxDate4,cardMain,cardMain2,cardMain3,cardMain4;
        LinearLayout linerLayoutInRowChat;
        MyViewHolder(View view) {
            super(view);
            txEzafeKari = itemView.findViewById(R.id.txEzafeKari);
            txSaat = itemView.findViewById(R.id.txSaat);

            txOnvanRecevedMessage7 = itemView.findViewById(R.id.txOnvanRecevedMessage7);
            txOnvanRecevedMessage5 = itemView.findViewById(R.id.txOnvanRecevedMessage5);

            txOnvanRecevedMessage3 =itemView.findViewById(R.id.txOnvanRecevedMessage3);
            txMajmoKarkard = itemView.findViewById(R.id.txMajmoKarkard);

            txMatnMozo = itemView.findViewById(R.id.txMatnMozo);
            txMozo = itemView.findViewById(R.id.txMozo);
            txDarTarikh = itemView.findViewById(R.id.txDarTarikh);
            txMokhatabin = itemView.findViewById(R.id.txMokhatabin);
            txMakan = itemView.findViewById(R.id.txMakan);
            txTozihat = itemView.findViewById(R.id.txTozihat);

            cardMain4 = itemView.findViewById(R.id.cardMain);
            cardMain3 = itemView.findViewById(R.id.cardMain);
            cardMain2 = itemView.findViewById(R.id.cardMain);
            cardMain = itemView.findViewById(R.id.cardMain);
            txDateAsli4 = itemView.findViewById(R.id.txDateAsli);
            cardViewTxDate4 = itemView.findViewById(R.id.cardViewTxDate);

            txDateAsli3 = itemView.findViewById(R.id.txDateAsli);
            cardViewTxDate3 = itemView.findViewById(R.id.cardViewTxDate);

            txDateAsli2 = itemView.findViewById(R.id.txDateAsli);
            cardViewTxDate2 = itemView.findViewById(R.id.cardViewTxDate);

            cardViewTxDate = itemView.findViewById(R.id.cardViewTxDate);
            txDateAsli = itemView.findViewById(R.id.txDateAsli);
            txNameFerestandeInSearch = itemView.findViewById(R.id.txNameFerestandeInSearch);
            linerLayoutInRowChat = itemView.findViewById(R.id.linerLayoutInRowChat);
            cardViewChat = itemView.findViewById(R.id.cardViewChat);

            clMainInRowChat = itemView.findViewById(R.id.cl_main);
            clMainInChat = itemView.findViewById(R.id.cl_main);

            imgUserPicture = itemView.findViewById(R.id.imgUserPicture);
            cardViewUnderUserPicture = itemView.findViewById(R.id.cardViewUnderProfilePicture);
            cardViewUnderUserPicture1 = itemView.findViewById(R.id.cardViewUnderProfilePicture1);

            txMokhaffafName = itemView.findViewById(R.id.txMokhaffafName);

            txNameFerestande = itemView.findViewById(R.id.txNameFerestande);
            txMatnPayam = itemView.findViewById(R.id.txMatnPayam);
            txDateInChat = itemView.findViewById(R.id.txDate);

            clDarkhastMorkhasi = itemView.findViewById(R.id.clDarkhastMorkhasi);

            txAzYaBeMorkhasi = itemView.findViewById(R.id.txAzYaBe);
            txNameFerestandeMorkhasi = itemView.findViewById(R.id.txNameFrestande);

            clVorodKhoroj = itemView.findViewById(R.id.clVorodKhoroj);
            txAzYaBeVorodKhoroj = itemView.findViewById(R.id.txAzYaBe);
            txNameFrestandeVorodKhoroj = itemView.findViewById(R.id.txNameFrestandeVorodKhoroj);

            txAzYaBe = itemView.findViewById(R.id.txAzYaBe);
            clMain = itemView.findViewById(R.id.cl_main);

            imgVaziyatTaeidVorodKhoroj = itemView.findViewById(R.id.imgVaziyatTaeid);

            clVaziyatTaeid = itemView.findViewById(R.id.clVaziyatTaeid);
            imgVaziyatTaeid = itemView.findViewById(R.id.imgVaziyatTaeid);

            txTarikhDarkhast = itemView.findViewById(R.id.txTarikhDarkhast);
            txAzTarikh = itemView.findViewById(R.id.txDateAz);
            txTaTarikh = itemView.findViewById(R.id.txDateTa);
            txElat = itemView.findViewById(R.id.txElat);
            imgVaziyatTaeidMorkhasi = itemView.findViewById(R.id.imgVaziyatTaeid);

            txDate1 = itemView.findViewById(R.id.txDate);
            txDate2 = itemView.findViewById(R.id.txDate);
            txSaatVorod = itemView.findViewById(R.id.txSaatVorod);
            txSaatKhoroj = itemView.findViewById(R.id.txSaatKhoroj);

            imgReadOrNo = itemView.findViewById(R.id.imgReadOrNotRead);

            imgUserPictureForSendMessageInTeacher = itemView.findViewById(R.id.imgUserPictureForSendMessageInTeacher);
            etTimeTakhirStudent = itemView.findViewById(R.id.etTimeTakhirStudent);
            etVaziyatDarsiStudent = itemView.findViewById(R.id.etVaziyatDarsiStudent);
            etVaziyatAkhlaghiStudent = itemView.findViewById(R.id.etVaziyatAkhlaghiStudent);

            imgHozorGhiyab = itemView.findViewById(R.id.imgHozorGhiyab);
            imgAddStudent = itemView.findViewById(R.id.imgAddStudent);

            txDate = itemView.findViewById(R.id.txDate);

            spinnerTakhirStudent= itemView.findViewById(R.id.spinnerInHozorGhiyab);
            imgChoiseReciverSendNewMessageInTeacher= itemView.findViewById(R.id.imgChoiseReciverSendNewMessageInTeacher);
            imgChoiseUserInSearchInTeacher= itemView.findViewById(R.id.imgChoiseUserForSendNewMessageInTeacher);
            txUserNameInSearchInTeacher= itemView.findViewById(R.id.txUserNameForSendMessageInTeacher);

            txOnvanMessageInRecivedMessage= itemView.findViewById(R.id.txOnvanRecevedMessage);
            txMatnMessageInRecivedMessage= itemView.findViewById(R.id.txMatnRecivedMessage);
            txNameFerestandeInRecivedMessage= itemView.findViewById(R.id.txNameFrestandeInRecivedMessage);
            txNameGirandehInRecivedMessage= itemView.findViewById(R.id.txNameGirandeInRecivedMessage);

            txNameDars= itemView.findViewById(R.id.txDarsNameInStudentDarsList);
            txVaziyatDarsiVaAkhlaghi= itemView.findViewById(R.id.txVaziyatDarsiVaAkhlaghiInStudentDarsList);
            txCountHazeri= itemView.findViewById(R.id.txTedadHozorInStudentDarsList);
            txCountGheybat= itemView.findViewById(R.id.txTedadGheybatInStudentDarsList);
            clVaziyatDarsi= itemView.findViewById(R.id.clVaziyatDarsi);

            txTaklif = itemView.findViewById(R.id.txTaklifInListJalasat2);
            txSchoolName = itemView.findViewById(R.id.txSchoolName);
            txClassName =  itemView.findViewById(R.id.txClassName);
            txTedadHazerinInLinsJalasat = itemView.findViewById(R.id.txTedadHazerinInListJalasat);
            txTedadGhayebinInListJalasat = itemView.findViewById(R.id.txTedadGhayebinInListJalasat);
            txTedadStudentInClass = itemView.findViewById(R.id.txTedadStudentInClass);
            txStudentNameInHozorGhiyab=  itemView.findViewById(R.id.txStudentNameInHozorGhiyab);
            imageView = itemView.findViewById(R.id.imgAddClass);
            imgRemoveStudent = itemView.findViewById(R.id.imgRemoveStudent);
            imgRemoveJalase = itemView.findViewById(R.id.imgRemoveJalase);
            imgRemoveClass = itemView.findViewById(R.id.imgRemoveClass);
            imgHazerGhayebTik = itemView.findViewById(R.id.imgHazerGhayebTik);

            cl = itemView.findViewById(R.id.clClickInRowAddJalase);
            clHazerGhayebTik = itemView.findViewById(R.id.clHazerGhayebTik);
        }
    }


    public void removeMessage(final Context context, final int position, final ArrayList<RecyclerModel> recyclerModels,
                              final RecyclerAdapterYouHaveKnow adapter) {
        final Dialog dialog = new Dialog(context, R.style.customDialog2);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog, null, false);
        final TextView txCancel = view.findViewById(R.id.txCancel);
        TextView txRemove = view.findViewById(R.id.txRemove);
        TextView txHazfPayam = view.findViewById(R.id.txHazfPayam);
        TextView txMatnHazfPayam = view.findViewById(R.id.txMatnHazfPayam);
        TextView txRad = view.findViewById(R.id.txRad);


        txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadData.removeMessage(context,recyclerModels.get(position).getId());
                recyclerModels.remove(position);
                onItemRemoved(recyclerModels);



                dialog.dismiss();
            }
        });


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //line zir baraye transparent kardan hashiye haye cardview ee:
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }



    public void allDialogButtonForPvChat(final Context context, final int position,
                                         final ArrayList<RecyclerModel> recyclerModels,
                                         final RecyclerAdapterYouHaveKnow adapter) {
        final Dialog dialog = new Dialog(context, R.style.customDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_inbox_message, null, false);

        ConstraintLayout clErsalNazar = view.findViewById(R.id.clErsalNazar);
        ConstraintLayout clCopy = view.findViewById(R.id.clCopy);
        ConstraintLayout clRemove = view.findViewById(R.id.clHazf);
        ConstraintLayout clTaeid = view.findViewById(R.id.clTaeid);
        ConstraintLayout clRad = view.findViewById(R.id.clRad);

        //clRemove.setVisibility(View.VISIBLE);
        clCopy.setVisibility(View.VISIBLE);
        clErsalNazar.setVisibility(View.GONE);

        clRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeMessage(context,position,recyclerModels,adapter);
                dialog.dismiss();
            }
        });

        clCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(recyclerModels.get(position).getOnvan());
                Toast.makeText(context, "کپی شد", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //line zir baraye transparent kardan hashiye haye cardview ee:
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    public void dialogGozareshKarRoyeKar(final Context context, final int position, final String method,
                                         final ImageView imgVaziyatTaeid, final TextView txOnvanMessageInRecivedMessage,
                                         final String noeGozaresh, final ArrayList<String> list_family, final ArrayList<String> list_id,
                                         final ArrayList<RecyclerModel> recyclerModels, final RecyclerAdapterYouHaveKnow adapter,
                                         final String idKar) {
        final Dialog dialog = new Dialog(context, R.style.customDialogKar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.gozaresh_roye_kar, null, false);
        final RecyclerView rvInInboxMessageTeacher = view.findViewById(R.id.rvInInboxMessageTeacher);
        final ConstraintLayout clWifiState = view.findViewById(R.id.clWifiState);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        final EditText etMatnChat = view.findViewById(R.id.etMatnChat);
        final ImageView imgSend = view.findViewById(R.id.imgSend);
        TextView txMatnAsli = view.findViewById(R.id.txMatnAsli);
        txMatnAsli.setText(recyclerModels.get(position).getOnvan());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final String username = SharedPrefClass.getUserId(c,"user");
        final String noe = SharedPrefClass.getUserId(c,"noe");

        etMatnChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    imgSend.setVisibility(View.VISIBLE);
                }else{
                    imgSend.setVisibility(View.GONE);
                }
            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeKononi timeKononi = new TimeKononi();
                String nowTime = timeKononi.getPersianTime();


                LoadData.sendMessageRoyeSepordanKar(c, recyclerAdapterYouHaveKnow, recyclerModels,
                        username, className, etMatnChat, "",clWifiState,nowTime,"",rvInInboxMessageTeacher,recyclerModels.get(position).getId());




            }
        });


        ArrayList<RecyclerModel> rModelsYouHaveKnow = null;
        RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow = null;

        LoadData.lastId2 = "0";
        rModelsYouHaveKnow = new ArrayList();

        rAdapterYouHaveKnow = new  RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message_dakhel_sepordan_kar", c, rAdapterYouHaveKnow, "",null,clShowErsal,null,"");
        Recyclerview.define_recyclerviewAddStudent(c, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow,null);



        LoadData.loadGozareshatUnderSepordanKar(c, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                rvInInboxMessageTeacher, username,className,"sepordan_kar",noe,clWifiState,idKar);




        /*ConstraintLayout clErsalNazar = view.findViewById(R.id.clErsalNazar);
        ConstraintLayout clCopy = view.findViewById(R.id.clCopy);
        ConstraintLayout clRemove = view.findViewById(R.id.clHazf);
        ConstraintLayout clTaeid = view.findViewById(R.id.clTaeid);
        ConstraintLayout clRad = view.findViewById(R.id.clRad);

        if (method.equals("taeid_gozaresh")){
            clRemove.setVisibility(View.VISIBLE);
            clTaeid.setVisibility(View.VISIBLE);
            clRad.setVisibility(View.VISIBLE);
        }



        if (recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){
            clTaeid.setVisibility(View.VISIBLE);
            clRad.setVisibility(View.VISIBLE);
        }

        if (noeGozaresh != null){
            if (noeGozaresh.equals("saat_vorod_khoroj")){
                clErsalNazar.setVisibility(View.GONE);
                clCopy.setVisibility(View.GONE);
            }else if (noeGozaresh.equals("darkhasti_morkhasi")){
                clErsalNazar.setVisibility(View.GONE);
                clCopy.setVisibility(View.GONE);
            }
        }

        clErsalNazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list_family.add(recyclerModels.get(position).getCity());
                list_id.add(recyclerModels.get(position).getRate());

                String s = "";
                for (int i = 0; i < list_family.size(); i++) {
                    s += list_family.get(i) + ", ";
                }

                Intent intent = new Intent(context, WriteNewMessage.class);
                intent.putStringArrayListExtra("id",list_id);
                intent.putStringArrayListExtra("family",list_family);
                context.startActivity(intent);

                dialog.dismiss();
            }
        });

        clTaeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialogItems(context,position,"taeid_gozaresh",imgVaziyatTaeid,noeGozaresh,recyclerModels,adapter);
                dialog.dismiss();
            }
        });

        clRad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialogItems(context,position,"rad_gozaresh",imgVaziyatTaeid,noeGozaresh,recyclerModels,adapter);
                dialog.dismiss();
            }
        });

        clRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickDialogItems(context,position,"delete",null,noeGozaresh,recyclerModels,adapter);

                dialog.dismiss();
            }
        });

        clCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noeGozaresh != null){
                    if (noeGozaresh.equals("darkhasti_morkhasi")){

                    }
                }else {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(txOnvanMessageInRecivedMessage.getText().toString() + " "  + recyclerModels.get(position).getMatn());
                    Toast.makeText(context, "کپی شد", Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });*/

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //line zir baraye transparent kardan hashiye haye cardview ee:
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }


    public void allDialogButton(final Context context, final int position, final String method,
                                final ImageView imgVaziyatTaeid, final TextView txOnvanMessageInRecivedMessage,
                                final String noeGozaresh, final ArrayList<String> list_family, final ArrayList<String> list_id,
                                final ArrayList<RecyclerModel> recyclerModels, final RecyclerAdapterYouHaveKnow adapter, final String id) {
        final Dialog dialog = new Dialog(context, R.style.customDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_inbox_message, null, false);

        ConstraintLayout clErsalNazar = view.findViewById(R.id.clErsalNazar);
        ConstraintLayout clCopy = view.findViewById(R.id.clCopy);
        ConstraintLayout clRemove = view.findViewById(R.id.clHazf);
        ConstraintLayout clTaeid = view.findViewById(R.id.clTaeid);
        ConstraintLayout clRad = view.findViewById(R.id.clRad);
        ConstraintLayout clErsalGozaresh = view.findViewById(R.id.clErsalGozaresh);
        TextView txErsalGozaresh = view.findViewById(R.id.txErsalGozaresh);

        if (method.equals("taeid_gozaresh")){
            //clRemove.setVisibility(View.VISIBLE);
            clTaeid.setVisibility(View.VISIBLE);
            clRad.setVisibility(View.VISIBLE);
        }
        String noe = SharedPrefClass.getUserId(c,"noe");
        if (method.equals("gozaresh_kar")){
            if (noe.equals("admin")){
                //clRemove.setVisibility(View.VISIBLE);
                clTaeid.setVisibility(View.VISIBLE);
                clRad.setVisibility(View.VISIBLE);
                clErsalGozaresh.setVisibility(View.VISIBLE);
                txErsalGozaresh.setText("گزارشات");
            }else {
                clRemove.setVisibility(View.GONE);
                clTaeid.setVisibility(View.VISIBLE);
                clRad.setVisibility(View.VISIBLE);
                clErsalGozaresh.setVisibility(View.VISIBLE);
            }


        }


        clErsalGozaresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogGozareshKarRoyeKar(c,position,"gozaresh_kar",
                        null,null,
                        null,list_family,list_id,recyclerModels,recyclerAdapterYouHaveKnow,id);
                dialog.dismiss();
            }
        });

        if (recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){
            clTaeid.setVisibility(View.VISIBLE);
            clRad.setVisibility(View.VISIBLE);
        }

        if (noeGozaresh != null){
            if (noeGozaresh.equals("saat_vorod_khoroj")){
                clErsalNazar.setVisibility(View.GONE);
                clCopy.setVisibility(View.GONE);
            }else if (noeGozaresh.equals("darkhasti_morkhasi")){
                clErsalNazar.setVisibility(View.GONE);
                clCopy.setVisibility(View.GONE);
            }else if (noeGozaresh.equals("darkhast_jalase")){
            clErsalNazar.setVisibility(View.GONE);
            clCopy.setVisibility(View.GONE);
        }
        }

        clErsalNazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list_family.add(recyclerModels.get(position).getCity());
                list_id.add(recyclerModels.get(position).getRate());

                String s = "";
                for (int i = 0; i < list_family.size(); i++) {
                    s += list_family.get(i) + ", ";
                }

                Intent intent = new Intent(context, WriteNewMessage.class);
                intent.putStringArrayListExtra("id",list_id);
                intent.putStringArrayListExtra("family",list_family);
                context.startActivity(intent);

                dialog.dismiss();
            }
        });

        clTaeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialogItems(context,position,"taeid_gozaresh",imgVaziyatTaeid,noeGozaresh,recyclerModels,adapter);
                dialog.dismiss();
            }
        });

        clRad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialogItems(context,position,"rad_gozaresh",imgVaziyatTaeid,noeGozaresh,recyclerModels,adapter);
                dialog.dismiss();
            }
        });

        clRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickDialogItems(context,position,"delete",null,noeGozaresh,recyclerModels,adapter);

                dialog.dismiss();
            }
        });

        clCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noeGozaresh != null){
                    if (noeGozaresh.equals("darkhasti_morkhasi") || noeGozaresh.equals("darkhast_jalase")){

                    }
                }else {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(txOnvanMessageInRecivedMessage.getText().toString() + " "  + recyclerModels.get(position).getMatn());
                    Toast.makeText(context, "کپی شد", Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //line zir baraye transparent kardan hashiye haye cardview ee:
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }


    public void clickDialogItems(final Context context, final int position, final String method,
                                 final ImageView imgVaziyatTaeid, final String noeGozaresh,
                                 final ArrayList<RecyclerModel> recyclerModels, final RecyclerAdapterYouHaveKnow adapter) {
        final Dialog dialog = new Dialog(context, R.style.customDialog2);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog, null, false);
        final TextView txCancel = view.findViewById(R.id.txCancel);
        TextView txRemove = view.findViewById(R.id.txRemove);
        TextView txHazfPayam = view.findViewById(R.id.txHazfPayam);
        TextView txMatnHazfPayam = view.findViewById(R.id.txMatnHazfPayam);
        TextView txRad = view.findViewById(R.id.txRad);

        if (method.equals("rad_gozaresh")){
            txHazfPayam.setText("رد گزارش");
            txMatnHazfPayam.setText("آیا گزارش رد شود؟");
            txRemove.setText("رد");
            txRemove.setTextColor(Color.parseColor("#008000"));
            txRad.setVisibility(View.VISIBLE);
            txRemove.setVisibility(View.GONE);
        }



        if (method.equals("taeid_gozaresh")){
            txHazfPayam.setText("تایید گزارش");
            txMatnHazfPayam.setText("آیا گزارش تایید شود؟");
            txRemove.setText("تایید");
            txRemove.setTextColor(Color.parseColor("#228f62"));
            txRemove.setVisibility(View.VISIBLE);
            txRad.setVisibility(View.GONE);
        }
        if (recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){
            txHazfPayam.setText("تایید کار");
            txMatnHazfPayam.setText("آیا کار تایید شود؟");
            txRemove.setText("تایید");
            txRemove.setTextColor(Color.parseColor("#228f62"));
            txRemove.setVisibility(View.VISIBLE);
            txRad.setVisibility(View.GONE);
        }

        if (method.equals("remove_class")){
            txMatnHazfPayam.setText("آیا از حذف این کلاس اطمینان دارید؟");
        }
        if (method.equals("remove_jalase")) {
            txMatnHazfPayam.setText("آیا از حذف این جلسه اطمینان دارید؟");
        }

        txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (method.equals("delete")){
                    if (noeGozaresh != null){
                         if (noeGozaresh.equals("darkhast_jalase")){

                             LoadData.removDarkhastJalase(context,recyclerModels.get(position).getId());
                             recyclerModels.remove(position);
                             onItemRemoved(recyclerModels);
                         }
                    }
                    LoadData.removeMessage(context,recyclerModels.get(position).getId());
                    recyclerModels.remove(position);
                    //adapter.notifyItemRemoved(position);
                    //adapter.notifyItemRangeChanged(position, recyclerModels.size());

                    //recyclerModels.remove(position);
                    onItemRemoved(recyclerModels);


                }else if (method.equals("remove_class")){

                    LoadData.removeClass(context,recyclerModels.get(position).getId());
                    recyclerModels.remove(position);
                    /*adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, recyclerModels.size());*/
                    onItemRemoved(recyclerModels);

                }else if (method.equals("remove_jalase")){
                    LoadData.removeJalase(context,recyclerModels.get(position).getId(),
                            recyclerModels.get(position).getCity());

                    recyclerModels.remove(position);
                    /*adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, recyclerModels.size());*/
                    onItemRemoved(recyclerModels);

                }else if (noeGozaresh != null){
                    if (noeGozaresh.equals("saat_vorod_khoroj")){
                        LoadData.updateVaziyatGozaresh(context,recyclerModels.get(position).getId(),imgVaziyatTaeid,"تایید شده",noeGozaresh.toString());
                    }else if (noeGozaresh.equals("darkhasti_morkhasi")){
                        LoadData.updateVaziyatGozaresh(context,recyclerModels.get(position).getId(),imgVaziyatTaeid,"تایید شده",noeGozaresh.toString());
                    }else if (noeGozaresh.equals("darkhast_jalase")){
                        LoadData.updateVaziyatGozaresh(context,recyclerModels.get(position).getId(),imgVaziyatTaeid,"تایید شده",noeGozaresh.toString());

                    }
                }else {
                    LoadData.updateVaziyatGozaresh(context,recyclerModels.get(position).getId(),imgVaziyatTaeid,"تایید شده",noeGozaresh);
                }
                dialog.dismiss();
            }
        });

        txRad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadData.updateVaziyatGozaresh(context,recyclerModels.get(position).getId(),imgVaziyatTaeid,"رد شده",noeGozaresh);
                dialog.dismiss();
            }
        });

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //line zir baraye transparent kardan hashiye haye cardview ee:
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }


}