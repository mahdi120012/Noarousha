package hozorghiyab.cityDetail;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.hozorghiyab.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import hozorghiyab.MySingleton;
import hozorghiyab.activities.HozorGhiyabMain;
import hozorghiyab.activities.ListPayamHayeErsali;
import hozorghiyab.cityDetail.placeComment.RecyclerAdapterPlaceComment;
import hozorghiyab.cityDetail.placeComment.RecyclerModelPlaceComment;
import hozorghiyab.customClasses.EnglishNumberToPersian;
import hozorghiyab.customClasses.Keyboard;
import hozorghiyab.customClasses.SharedPrefClass;
import hozorghiyab.listCityACT.CityAdapter;

public class LoadData {

    public static final int LOAD_LIMIT = 60;
    public static String lastId = "0";
    public static String lastId2 = "0";
    public static String lastId3 = "0";
    public static String lastId5 = "0";

    public static boolean itShouldLoadMore = true;
    public static String tedadPayamKhangeNashodeServise = "";

    public static void firstLoadData(final Context c, final CityAdapter recyclerAdapter,
                                     final ArrayList<RecyclerModel> recyclerModels,
                                     final ImageView img_refresh, final WebView webView,
                                     final Timer timer, final TextView net_state,
                                     final RecyclerView recyclerView, final String city,
                                     final String cat) {


        //String url = "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?limit=" + LOAD_LIMIT;
        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=first&limit=" + LOAD_LIMIT + "&city=" + city + "&cat=" + cat;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                //img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("matn");
                        String picture = jsonObject.getString("p1");
                        String city = jsonObject.getString("city");
                        String position = jsonObject.getString("position");
                        float rate = Float.valueOf(jsonObject.getString("rate"));
                        String countRateAndComment = jsonObject.getString("count_rate_comment");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,picture,city,"","",countRateAndComment,0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadData(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataPlaceAct(final Context c, final RecyclerAdapter recyclerAdapter,
                                             final ArrayList<RecyclerModel> recyclerModels,
                                             final ImageView img_refresh, final WebView webView,
                                             final Timer timer, final TextView net_state,
                                             final RecyclerView recyclerView, final String city,
                                             final String cat) {


        //String url = "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?limit=" + LOAD_LIMIT;
        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=first&limit=" + LOAD_LIMIT + "&city=" + city + "&cat=" + cat;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("matn");
                        String picture = jsonObject.getString("p1");
                        String city = jsonObject.getString("city");
                        String position = jsonObject.getString("position");
                        //float rate = Float.valueOf(jsonObject.getString("rate"));
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,picture,city,"","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataPlaceAct(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataRate(final Context c,
                                         final ImageView img_refresh, final WebView webView,
                                         final Timer timer, final TextView net_state, final String id,
                                         final SimpleRatingBar ratingBar, final TextView numberOfRate) {

        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=loadrate&limit=" + LOAD_LIMIT + "&id=" + id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                float rate;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        //line zir bara ine ke float null nmigire va age null begire error mide, pass avval baresi mikonim
                        //null ee ya na:
                        if (jsonObject.has("rate") && !jsonObject.isNull("rate")) {
                            rate = Float.valueOf(jsonObject.getString("rate"));
                            ratingBar.setRating(rate);
                        }

                        String numberOfAllRate = (jsonObject.getString("all_rate_count"));
                        numberOfRate.setText(numberOfAllRate + " نظر");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);




                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }
    public static void sendRate(final Context c, final RecyclerAdapterPlaceComment recyclerAdapter,
                                final ArrayList<RecyclerModelPlaceComment> recyclerModels,
                                final ImageView img_refresh, final WebView webView,
                                final Timer timer, final TextView net_state,
                                final RecyclerView recyclerView,String username,String post_id,String rate,
                                final SimpleRatingBar ratingBar, final TextView numberOfRate) {

        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=sendrate&limit=" + LOAD_LIMIT + "&username=" + username + "&post_id=" + post_id + "&rate=" + rate;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        float rate = Float.valueOf(jsonObject.getString("rate"));
                        String numberOfAllRate = (jsonObject.getString("all_rate_count"));
                        ratingBar.setRating(rate);
                        numberOfRate.setText(numberOfAllRate + " نظر");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //Toast.makeText(c, response, Toast.LENGTH_SHORT).show();





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void removeMessage(final Context c, String messageId) {

        String messageIdEncode="";
        try {
            messageIdEncode = URLEncoder.encode(messageId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_message&message_id=" + messageIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال انجام...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void removDarkhastJalase(final Context c, String id) {

        String darkhastJalaseIdEncode = UrlEncoderClass.urlEncoder(id);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_darkhast_jalase&darkhast_jalase_id=" + darkhastJalaseIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);;


    }


    public static void removeJalase(final Context c, String jalase_id,String classId) {

        String jalaseIdEncode="";
        String classIdEncode="";
        try {
            jalaseIdEncode = URLEncoder.encode(jalase_id,"UTF-8");
            classIdEncode = URLEncoder.encode(classId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_jalase&jalase_id=" + jalaseIdEncode + "&class_id=" + classIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);;


    }


    public static void removeClass(final Context c, String classId) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_class&class_id=" + classId;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void removeStudent(final Context c, String student_id) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_student&student_id=" + student_id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void sendStudent(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                   final ArrayList<RecyclerModel> recyclerModels,
                                   final ImageView img_refresh, final WebView webView,
                                   final Timer timer, final TextView net_state,
                                   final RecyclerView recyclerView, String studentName,
                                   final String className, final String username, final ProgressBar progressBar) {
        String classNameEncode="";
        String studentNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
            studentNameEncode = URLEncoder.encode(studentName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_student&limit=" + LOAD_LIMIT + "&student_name=" + studentNameEncode + "&class=" + classNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "دانش آموزی پیدا نشد", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username,className);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void sendJalase(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                  final ArrayList<RecyclerModel> recyclerModels,
                                  final ImageView img_refresh, final WebView webView,
                                  final Timer timer, final TextView net_state,
                                  final RecyclerView recyclerView, final ProgressBar progressBar,
                                  String jalaseName, final String className,
                                  final String username) {

        String classNameEncode="";
        String jalaseNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
            jalaseNameEncode = URLEncoder.encode(jalaseName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_jalase&limit=" + LOAD_LIMIT + "&name=" + jalaseNameEncode + "&class=" + classNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "جلسه از قبل ثبت شده", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            Toast.makeText(c,"ثبت شد",Toast.LENGTH_SHORT).show();

                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            LoadData.loadMorejalase(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username,className);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void loadMorejalase(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ProgressBar progressBar,String username,String className) {

        String classNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadmore_jalase&lastId=" + lastId + "&limit=" + LOAD_LIMIT + "&user1=" + username + "&class=" + classNameEncode ;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        lastId = jsonObject.getString("jalase_id");
                        String jalaseName = jsonObject.getString("name");
                        String className = jsonObject.getString("class_name");
                        String classId = jsonObject.getString("class_id");
                        String tedadHazerin = jsonObject.getString("tedad_hazerin");
                        String tedadGhayebin = jsonObject.getString("tedad_ghayebin");
                        recyclerModels.add(new RecyclerModel(lastId,jalaseName, className,"",classId,tedadHazerin,"",tedadGhayebin,0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }

    public static void loadMoreStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                     final ArrayList<RecyclerModel> recyclerModels,
                                     final ProgressBar progressBar,String username,String className) {

        String classNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadmore_student&lastId=" + lastId + "&limit=" + LOAD_LIMIT + "&user1=" + username + "&class=" + classNameEncode;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String std_id = jsonObject.getString("std_id");
                        String classId = jsonObject.getString("class_id");
                        String teacherId = jsonObject.getString("teacher_id");
                        String studentName = jsonObject.getString("std_name");
                        String className = jsonObject.getString("class_name");
                        String studentPicture = jsonObject.getString("std_picture");
                        recyclerModels.add(new RecyclerModel(lastId,studentName, className,studentPicture,"","","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }
    public static void sendMessageStudent(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final RecyclerView recyclerView,
                                          final String username, String stdId,
                                          String onvan, String matn,String nowTime) {
        String userNameEncode="";
        String stdIdEncode="";
        String onvanEncode="";
        String matnEncode="";
        String nowTimeEncode="";
        try {
            userNameEncode = URLEncoder.encode(username,"UTF-8");
            stdIdEncode = URLEncoder.encode(stdId,"UTF-8");
            onvanEncode = URLEncoder.encode(onvan,"UTF-8");
            matnEncode = URLEncoder.encode(matn,"UTF-8");
            nowTimeEncode = URLEncoder.encode(nowTime,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_message_student&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        /*webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");*/
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                /*net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });
*/

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void sendMablagh(final Context c, final String username, String mablagh,
                                          String tarikh,
                                          String tozihat, final ConstraintLayout clWifi, final EditText etMablagh,
                                          final EditText etTarikh, final EditText etTozihat) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String mablaghEncode= UrlEncoderClass.urlEncoder(mablagh);
        String tarikhEncode= UrlEncoderClass.urlEncoder(tarikh);
        String tozihatEncode= UrlEncoderClass.urlEncoder(tozihat);


        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_sabt_makharej&username1=" + userNameEncode + "&mablagh=" + mablaghEncode + "&tarikh=" + tarikhEncode + "&tozihat=" + tozihatEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            etMablagh.setText("");
                            etTarikh.setText("");
                            etTozihat.setText("");

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.putExtra("sabt_makharej", "sabt_makharej");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendDarkhastJalase(final Context c, final String username, String onvan,
                                          String tarikhDarkhast,
                                          String tarikhShoro, final ConstraintLayout clWifi, final EditText etOnvan,
                                          final EditText etTarikh, final EditText etSaat, final EditText etMokhatabin,
                                          final EditText etMakan, final EditText etTozihat) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String onvanEncode= UrlEncoderClass.urlEncoder(onvan);
        String tarikhDarkhastEncode= UrlEncoderClass.urlEncoder(tarikhDarkhast);
        String tarikhShoroEncode= UrlEncoderClass.urlEncoder(tarikhShoro);

        String mokhatabinEncode= UrlEncoderClass.urlEncoder(etMokhatabin.getText().toString());
        String makanEncode= UrlEncoderClass.urlEncoder(etMakan.getText().toString());
        String tozihatEncode= UrlEncoderClass.urlEncoder(etTozihat.getText().toString());

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_darkhast_jalase&username1=" + userNameEncode + "&tarikh_darkhast=" + tarikhDarkhastEncode + "&tarikh_shoro=" + tarikhShoroEncode + "&onvan=" + onvanEncode + "&mokhatabin=" + mokhatabinEncode + "&makan=" + makanEncode + "&tozihat=" + tozihatEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            etOnvan.setText("");
                            etTarikh.setText("");
                            etSaat.setText("");

                            etMokhatabin.setText("");
                            etMakan.setText("");
                            etTozihat.setText("");

                           Intent intent = new Intent(c, ListPayamHayeErsali.class);
                           intent.putExtra("darkhast_jalase", "darkhast_jalase");
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           c.startActivity(intent);

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendDarkhastMorkhasi(final Context c, final String username,String tarikh, String tarikhShoro,
                                       String tarikhPayan, String elat ,final ConstraintLayout clWifi) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String tarikhEncode= UrlEncoderClass.urlEncoder(tarikh);
        String tarikhPayanEncode= UrlEncoderClass.urlEncoder(tarikhShoro);
        String tarikhShoroEncode= UrlEncoderClass.urlEncoder(tarikhPayan);
        String elatEncode= UrlEncoderClass.urlEncoder(elat);


        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_darkhast_morkhasi&username1=" + userNameEncode + "&tarikh=" + tarikhEncode + "&tarikh_shoro=" + tarikhShoroEncode + "&tarikh_payan=" + tarikhPayanEncode + "&elat=" + elatEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                           Intent intent = new Intent(c, ListPayamHayeErsali.class);
                           intent.putExtra("darkhast_morkhasi", "darkhast_morkhasi");
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           c.startActivity(intent);

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendVorodKhorojEzafekari(final Context c, final String username,
                                       String saatVorod, String saatKhoroj, String date,String elat, final String method, final ConstraintLayout clWifi) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String saatVorodEncode= UrlEncoderClass.urlEncoder(new EnglishNumberToPersian().convertToEnglish(saatVorod));
        String saatKhorojEncode= UrlEncoderClass.urlEncoder(new EnglishNumberToPersian().convertToEnglish(saatKhoroj));
        String dateEncode= UrlEncoderClass.urlEncoder(date);
        String elatEncode= UrlEncoderClass.urlEncoder(elat);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_vorod_khoroj_ezafe_kari&username1=" + userNameEncode + "&saat_vorod=" + saatVorodEncode + "&saat_khoroj=" + saatKhorojEncode + "&date=" + dateEncode + "&elat=" + elatEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){

                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                            if (method.equals("saatKhoroj")){
                                Intent intent = new Intent(c, ListPayamHayeErsali.class);
                                intent.putExtra("vorod_khoroj", "vorod_khoroj");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                c.startActivity(intent);
                            }

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendVorodKhoroj(final Context c, final String username,
                                       String saatVorod, String saatKhoroj, String date, final String method, final ConstraintLayout clWifi) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String saatVorodEncode= UrlEncoderClass.urlEncoder(new EnglishNumberToPersian().convertToEnglish(saatVorod));
        String saatKhorojEncode= UrlEncoderClass.urlEncoder(new EnglishNumberToPersian().convertToEnglish(saatKhoroj));
        String dateEncode= UrlEncoderClass.urlEncoder(date);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_vorod_khoroj&username1=" + userNameEncode + "&saat_vorod=" + saatVorodEncode + "&saat_khoroj=" + saatKhorojEncode + "&date=" + dateEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){

                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                            if (method.equals("saatKhoroj")){
                                Intent intent = new Intent(c, ListPayamHayeErsali.class);
                                intent.putExtra("vorod_khoroj", "vorod_khoroj");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                c.startActivity(intent);
                            }

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void sendGozareshKar(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final String username, String idDaryaftKonande,
                                       final EditText etGozaresh, final EditText etNatige, String date, final ConstraintLayout clWifi) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String idDaryaftKonandeEncode= UrlEncoderClass.urlEncoder(idDaryaftKonande);
        String gozareshEncode= UrlEncoderClass.urlEncoder(etGozaresh.getText().toString());
        String natigeEncode= UrlEncoderClass.urlEncoder(etNatige.getText().toString());
        String dateEncode= UrlEncoderClass.urlEncoder(date);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_gozaresh_kar&username1=" + userNameEncode + "&id_daryaft_konande=" + idDaryaftKonandeEncode + "&gozaresh=" + gozareshEncode + "&natige=" + natigeEncode + "&date=" + dateEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            etGozaresh.setText("");
                            etNatige.setText("");
                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.putExtra("gozaresh_kar", "gozaresh_kar");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

/*    public static void sendFirstNullMessagePvChat(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final String username, String stdId,
                                          final EditText etOnvan, String matn, final ConstraintLayout clWifi, String nowTime,
                                          final String noe, final String conversationId, final RecyclerView rv) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String stdIdEncode= UrlEncoderClass.urlEncoder(stdId);
        String onvanEncode= UrlEncoderClass.urlEncoder(etOnvan.getText().toString());
        String matnEncode= UrlEncoderClass.urlEncoder(matn);
        String nowTimeEncode= UrlEncoderClass.urlEncoder(nowTime);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);
        String conversationIdEncode= UrlEncoderClass.urlEncoder(conversationId);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_first_null_message&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode + "&noe=" + noeEncode + "&conversation_id=" + conversationIdEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(final String response) {

                        clWifi.setVisibility(View.GONE);
                        //ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.length()>0){
                            etOnvan.setText("");

                            LoadData.loadPvChat(c, rAdapterYouHaveKnow, recyclerModels, rv, username,response.toString(),clWifi);
                            Toast.makeText(c, "ارسال شدددددد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }*/


    public static void sendLastSeen(final Context c, final String username,final String lastSeen) {

        String userNameEncode = UrlEncoderClass.urlEncoder(username);
        String lastSeenEncode = UrlEncoderClass.urlEncoder(lastSeen);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_last_seen&username1=" + userNameEncode + "&last_seen=" + lastSeenEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //clWifi.setVisibility(View.GONE);
                        //ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.contains("send_shod")){

                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                        }else {
                            //Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                //Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                //clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void sendSepordanKar(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                                           final ArrayList<RecyclerModel> recyclerModels,
                                                           final String username, String stdId,
                                                           final EditText etKar1,final EditText etKar2,final EditText etKar3,
                                                           final EditText etKar4,final EditText etKar5,
                                                           final ConstraintLayout clWifi, String nowTime,
                                                           final String noe, final RecyclerView rv) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String stdIdEncode= UrlEncoderClass.urlEncoder(stdId);
        String kar1Encode= UrlEncoderClass.urlEncoder(etKar1.getText().toString());
        String kar2Encode= UrlEncoderClass.urlEncoder(etKar2.getText().toString());
        String kar3Encode= UrlEncoderClass.urlEncoder(etKar3.getText().toString());
        String kar4Encode= UrlEncoderClass.urlEncoder(etKar4.getText().toString());
        String kar5Encode= UrlEncoderClass.urlEncoder(etKar5.getText().toString());

        String nowTimeEncode= UrlEncoderClass.urlEncoder(nowTime);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_sepordan_kar&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&kar1=" + kar1Encode + "&kar2=" + kar2Encode + "&kar3=" + kar3Encode + "&kar4=" + kar4Encode + "&kar5=" + kar5Encode + "&now_time=" + nowTimeEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        //ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.contains("send_shod")){

                            etKar1.setText("");
                            etKar2.setText("");
                            etKar3.setText("");
                            etKar4.setText("");
                            etKar5.setText("");
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void sendMessageTeacherInWriteNewMessage(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final String username, String stdId,
                                          final EditText etOnvan, String matn, final ConstraintLayout clWifi, String nowTime,
                                          final String noe, final RecyclerView rv) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String stdIdEncode= UrlEncoderClass.urlEncoder(stdId);
        String onvanEncode= UrlEncoderClass.urlEncoder(etOnvan.getText().toString());
        String matnEncode= UrlEncoderClass.urlEncoder(matn);
        String nowTimeEncode= UrlEncoderClass.urlEncoder(nowTime);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_message_teacher&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        //ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){

                            etOnvan.setText("");
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendMessageRoyeSepordanKar(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                                  final ArrayList<RecyclerModel> recyclerModels,
                                                  final String username, final String stdId,
                                                  final EditText etOnvan, String matn,
                                                  final ConstraintLayout clWifi, String nowTime,
                                                  final String noe, final RecyclerView rv, final String id_kar) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        final String stdIdEncode= UrlEncoderClass.urlEncoder(stdId);
        String onvanEncode= UrlEncoderClass.urlEncoder(etOnvan.getText().toString());
        String matnEncode= UrlEncoderClass.urlEncoder(matn);
        String nowTimeEncode= UrlEncoderClass.urlEncoder(nowTime);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);
        String id_karEncode= UrlEncoderClass.urlEncoder(id_kar);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_message_roye_sepordan_kar&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode + "&noe=" + noeEncode + "&id_kar=" + id_karEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        //ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){

                            etOnvan.setText("");
                            /*LoadData.loadGozareshatUnderSepordanKar(c, rAdapterYouHaveKnow, recyclerModels,
                                    rv, username,stdId,"sepordan_kar",noe,clWifi,id_kar);*/

                           /* LoadData.loadGozareshatUnderSepordanKarWidthLimit(c, rAdapterYouHaveKnow, recyclerModels,
                                    rv, username,stdId,"sepordan_kar",noe,clWifi,id_kar);*/
                            /*LoadData.loadPvChatWitchLastLoadAfterSendMessage(c, rAdapterYouHaveKnow, recyclerModels,
                                    rv, username,stdIdEncode,clWifi);*/

                            ArrayList<RecyclerModel> rModelsYouHaveKnow = null;
                            RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow = null;

                            LoadData.lastId2 = "0";
                            rModelsYouHaveKnow = new ArrayList();

                            rAdapterYouHaveKnow = new  RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message_dakhel_sepordan_kar", c, rAdapterYouHaveKnow, "",null,null,null,"");
                            Recyclerview.define_recyclerviewAddStudent(c, rv, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow,null);



                            LoadData.loadGozareshatUnderSepordanKar(c, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    rv, username,stdId,"sepordan_kar",noe,clWifi,id_kar);


                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendMessageTeacher(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final String username, String stdId,
                                          final EditText etOnvan, String matn,
                                          final ConstraintLayout clWifi, String nowTime,
                                          final String noe, final RecyclerView rv) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        final String stdIdEncode= UrlEncoderClass.urlEncoder(stdId);
        String onvanEncode= UrlEncoderClass.urlEncoder(etOnvan.getText().toString());
        String matnEncode= UrlEncoderClass.urlEncoder(matn);
        String nowTimeEncode= UrlEncoderClass.urlEncoder(nowTime);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_message_teacher&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        //ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){

                            etOnvan.setText("");
                            /*LoadData.loadPvChatWitchLastLoadAfterSendMessage(c, rAdapterYouHaveKnow, recyclerModels,
                                    rv, username,stdIdEncode,clWifi);*/
                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendClass(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                 final ArrayList<RecyclerModel> recyclerModels,
                                 final ImageView img_refresh, final WebView webView,
                                 final Timer timer, final TextView net_state,
                                 final RecyclerView recyclerView, String schoolName,
                                 String className, final String username, final ProgressBar progressBar) {
        String classNameEncode="";
        String schoolNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
            schoolNameEncode = URLEncoder.encode(schoolName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=sendclass&limit=" + LOAD_LIMIT + "&school=" + schoolNameEncode + "&class=" + classNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataPlaceComment(final Context c, final RecyclerAdapterPlaceComment recyclerAdapter,
                                                 final ArrayList<RecyclerModelPlaceComment> recyclerModels,
                                                 final ImageView img_refresh, final WebView webView,
                                                 final Timer timer, final TextView net_state,
                                                 final RecyclerView recyclerView, final String city,
                                                 final String cat) {

        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=comment&limit=" + LOAD_LIMIT + "&city=" + city + "&cat=" + cat;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String username = jsonObject.getString("username");
                        String name = jsonObject.getString("name");
                        String picture = jsonObject.getString("picture");
                        String comment = jsonObject.getString("comment");
                        recyclerModels.add(new RecyclerModelPlaceComment(username, name,picture,comment));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);

                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }



    public static void loadCountMessageNotRead(final Context c, final TextView txCountNotReadMessage,
                                                             String username) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_count_not_read_message&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }
                String tedadPayamKhangeNashode = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txCountNotReadMessage.setText(tedadPayamKhangeNashode);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void loadVorodKhorojGhabliEzafeKari(final Context c, final TextView etSaatVorod,
                                                      final TextView etSaatKhoroj, final EditText etElat,
                                                      String tarikh,
                                                      String username, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String tarikhEncode = UrlEncoderClass.urlEncoder(tarikh);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_saat_vorod_khoroj_ghabli_ezafe_kari&user1=" + usernameEncode + "&tarikhEncode=" + tarikhEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tarikh = null;
                String saatVorod = null;
                String saatKhoroj = null;
                String elat = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        tarikh = jsonObject.getString("tarikh");
                        saatVorod = jsonObject.getString("saat_vorod");
                        saatKhoroj = jsonObject.getString("saat_khoroj");
                        elat = jsonObject.getString("elat");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                etSaatVorod.setText(saatVorod);
                etSaatKhoroj.setText(saatKhoroj);
                etElat.setText(elat);
                //VorodKhoroj.Myclass myclass = new VorodKhoroj.Myclass();
                //myclass.Myclass("hi","by");




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void loadVorodKhorojGhabli(final Context c,final TextView etSaatVorod,
                                                             final TextView etSaatKhoroj,
                                                             String tarikh,
                                                             String username, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String tarikhEncode = UrlEncoderClass.urlEncoder(tarikh);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_saat_vorod_khoroj_ghabli&user1=" + usernameEncode + "&tarikhEncode=" + tarikhEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tarikh = null;
                String saatVorod = null;
                String saatKhoroj = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        tarikh = jsonObject.getString("tarikh");
                        saatVorod = jsonObject.getString("saat_vorod");
                        saatKhoroj = jsonObject.getString("saat_khoroj");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                etSaatVorod.setText(saatVorod);
                etSaatKhoroj.setText(saatKhoroj);

                //VorodKhoroj.Myclass myclass = new VorodKhoroj.Myclass();
                //myclass.Myclass("hi","by");




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static String loadTeacherNameAndCountMessageNotReadForServise(final Context c, String username) {


        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_teacher_name&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {


                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }

                String studentName = null;
                String teacherPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashodeServise = jsonObject.getString("tedad_payam_khande_nashode");
                        teacherPicture = jsonObject.getString("picture");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
        return tedadPayamKhangeNashodeServise;
    }


    public static void loadOnlyCountMessageNotReadForNotification(final Context c, final TextView txStudentName,
                                                             final TextView txCountNotReadMessage,
                                                             String username, final ImageView imgTeacherPicture,
                                                             final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_teacher_name&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                //clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }

                String studentName = null;
                String tedadPayamKhangeNashode = null;
                String teacherPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");
                        teacherPicture = jsonObject.getString("picture");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



                    String tedadPayamKhandeNashodeGhabli = SharedPrefClass.getUserId(c,"tedad_payam_khande_nashode");

                    if (tedadPayamKhandeNashodeGhabli.equals("")){
                        tedadPayamKhandeNashodeGhabli = "0";
                    }
                    int tedadPayamKhandeNashodeGhabliInt = Integer.parseInt(tedadPayamKhandeNashodeGhabli);
                    int tedadPayamKhangeNashodeInt = Integer.parseInt(tedadPayamKhangeNashode);

                    //Toast.makeText(c, String.valueOf(tedadPayamKhandeNashodeGhabliInt), Toast.LENGTH_SHORT).show();
                    if (tedadPayamKhandeNashodeGhabliInt == tedadPayamKhangeNashodeInt){

                    }else {


                    SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tedad_payam_khande_nashode", tedadPayamKhangeNashode);
                    editor.commit();

                    // Create the NotificationChannel, but only on API 26+ because
                    // the NotificationChannel class is new and not in the support library
                    LoadData.createNotificationChannel(c);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "1")
                            .setSmallIcon(R.drawable.hozorghiyabicon)
                            .setSound(Uri.parse("android.resource://" + c.getPackageName() + "/" + R.raw.sound))//*see note)
                            .setContentTitle("یک پیام جدید")
                            .setContentText("یک پیام جدید موجوده")
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());

                    //Toast.makeText(c, tedadPayamKhangeNashode, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void loadLastSeen(final Context c, final String userName, final TextView txLastSeen) {

        String usernameEncode = UrlEncoderClass.urlEncoder(userName);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_last_seen&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                //clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }

                String lastSeen = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        lastSeen = jsonObject.getString("last_seen");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txLastSeen.setText(lastSeen);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }



    public static void loadTeacherNameAndCountMessageNotRead(final Context c, final TextView txStudentName,
                                                             final TextView txCountNotReadMessage,
                                                             String username, final ImageView imgTeacherPicture,
                                                             final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_teacher_name&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }

                String studentName = null;
                String tedadPayamKhangeNashode = null;
                String teacherPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");
                        teacherPicture = jsonObject.getString("picture");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txStudentName.setText(studentName);
                txCountNotReadMessage.setText(tedadPayamKhangeNashode);

                if (teacherPicture.isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.usericon)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgTeacherPicture);

                }else{
                    Picasso.get()
                            .load(teacherPicture)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgTeacherPicture);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void createNotificationChannel(Context c) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test";
            String description = "test";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = c.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    public static String loadStudentNameAndCountMessageNotRead(final Context c, final String urlAppend,
                                                             final TextView net_state,
                                                             final TextView txStudentName, final TextView txCountNotReadMessage,
                                                             final ImageView imgStudentPicture, final ConstraintLayout clWifi) {
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php"+urlAppend;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                net_state.setVisibility(View.GONE);
                //ProgressDialogClass.dismissProgress();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String studentName = null;
                String tedadPayamKhangeNashode = null;
                String studentPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");
                        studentPicture = jsonObject.getString("picture");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txStudentName.setText(studentName);
                txCountNotReadMessage.setText(tedadPayamKhangeNashode);

                if (studentPicture.isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.usericon)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgStudentPicture);

                }else{
                    Picasso.get()
                            .load(studentPicture)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgStudentPicture);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                //Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                net_state.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.loadStudentNameAndCountMessageNotRead(c,urlAppend,
                                net_state,txStudentName,txCountNotReadMessage,imgStudentPicture,clWifi);

                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

         return txCountNotReadMessage.getText().toString();

    }

    public static void loadTimeTakhirList(final Context c, final List<String> list,
                                     final ArrayAdapter<String> spinnerArrayAdapter,
                                     final ImageView img_refresh, final WebView webView,
                                     final Timer timer, final TextView net_state,
                                     final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                     final ArrayList<RecyclerModel> rModelsYouHaveKnow) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_time_takhir&limit=" + LOAD_LIMIT;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String className = jsonObject.getString("time_takhir");

                        rModelsYouHaveKnow.add(new RecyclerModel(lastId,className, "","","","","","",0,null,null,null,null));
                        rAdapterYouHaveKnow.notifyDataSetChanged();

                        list.add(className);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerArrayAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadAdminList(final Context c, final List<String> list,
                                     final ArrayAdapter<String> spinnerArrayAdapter,
                                     String username,
                                     final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                     final ArrayList<RecyclerModel> rModelsYouHaveKnow) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_admin_list&limit=" + LOAD_LIMIT + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String family = jsonObject.getString("family");

                        rModelsYouHaveKnow.add(new RecyclerModel(lastId,family, "","","","","","",0,null,null,null,null));
                        rAdapterYouHaveKnow.notifyDataSetChanged();

                        list.add(family);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerArrayAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }








    public static void loadClassList(final Context c, final List<String> list,
                                     final ArrayAdapter<String> spinnerArrayAdapter,
                                     final ImageView img_refresh, final WebView webView,
                                     final Timer timer, final TextView net_state, String username,
                                     final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                     final ArrayList<RecyclerModel> rModelsYouHaveKnow) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadclass&limit=" + LOAD_LIMIT + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("class_id");
                        String className = jsonObject.getString("class_name");

                        rModelsYouHaveKnow.add(new RecyclerModel(lastId,className, "","","","","","",0,null,null,null,null));
                        rAdapterYouHaveKnow.notifyDataSetChanged();

                        list.add(className);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerArrayAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void updatePassword(final Context c, final String username, final String newPassword,
                                      final EditText etNewPassword, final EditText etTekrarNewPassword,final Dialog dialog) {

        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_password&username=" + UrlEncoderClass.urlEncoder(username) + "&new_password=" +  UrlEncoderClass.urlEncoder(newPassword);

        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.equals("send_shod")){
                            etNewPassword.setText("");
                            etTekrarNewPassword.setText("");
                            dialog.dismiss();
                            Toast.makeText(c, "رمز عبور با موفقیت تغییر یافت", Toast.LENGTH_SHORT).show();
                            Keyboard.hideKeyboard(c,(Activity) c);
                        }else {
                            Toast.makeText(c, "مشکلی در تغییر رمز عبور بوجود آمده", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void updateVaziyatGozaresh(final Context c, String idGozaresh, final ImageView imgVaziyatTaeid, final String vaziyat,
                                             final String noeGozaresh) {

        String url = null;

        if (noeGozaresh != null){

            if (noeGozaresh.equals("saat_vorod_khoroj")){
                url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_vaziyat_vorod_khoroj&gozaresh_id=" + UrlEncoderClass.urlEncoder(idGozaresh) + "&vaziyat=" +  UrlEncoderClass.urlEncoder(vaziyat);

            }else if(noeGozaresh.equals("darkhasti_morkhasi")){
                url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_vaziyat_morkhasi&gozaresh_id=" + UrlEncoderClass.urlEncoder(idGozaresh) + "&vaziyat=" +  UrlEncoderClass.urlEncoder(vaziyat);

            }else if (noeGozaresh.equals("darkhast_jalase")){
                Toast.makeText(c, idGozaresh, Toast.LENGTH_SHORT).show();
                url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_vaziyat_darkhast_jalase&gozaresh_id=" + UrlEncoderClass.urlEncoder(idGozaresh) + "&vaziyat=" +  UrlEncoderClass.urlEncoder(vaziyat);

            }

        }else {
            url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_vaziyat_gozaresh&gozaresh_id=" + UrlEncoderClass.urlEncoder(idGozaresh) + "&vaziyat=" +  UrlEncoderClass.urlEncoder(vaziyat);
        }
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);

                            if (vaziyat.equals("تایید شده")){
                                imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                                Toast.makeText(c, "تایید شد", Toast.LENGTH_SHORT).show();
                            }else {
                                imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                                Toast.makeText(c, "رد شد", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(c, "تایید نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void updateStudentInHozorGhiyabGhayebToHazer(final Context c, String student_id,final ImageView imgTik,
                                                               final ArrayList<RecyclerModel> recyclerModels, final int position) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_ghtoh&student_id=" + student_id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            recyclerModels.get(position).setMatn("1");
                            imgTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.hazertik));
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void updateHozorGhiyabTozihat(final Context c, String jalaseId,
                                                             String tozihat,String taklif) {
        String tozihatEncode="";
        String taklifEncode="";
        try {
            tozihatEncode = URLEncoder.encode(tozihat,"UTF-8");
            taklifEncode = URLEncoder.encode(taklif,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_jalase_tozihat&jalase_id=" + jalaseId + "&tozihat=" + tozihatEncode + "&taklif=" + taklifEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            //recyclerModels.get(position).setMatn("0");
                            //imgTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ghayebtik));
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void updateStudentInHozorGhiyab_timeTakhir(final Context c, String student_id,
                                                  final ArrayList<RecyclerModel> recyclerModels,
                                                  final int position,final String timeTakhir) {
        String timeTakhirEncode="";
        try {
            timeTakhirEncode = URLEncoder.encode(timeTakhir,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_time_takhir&student_id=" + student_id + "&time_takhir=" + timeTakhirEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }
    public static void updateStudentInHozorGhiyab_VaziyatDarsi(final Context c, String student_id,
                                                             final ArrayList<RecyclerModel> recyclerModels,
                                                             final int position,final String timeTakhir) {
        String timeTakhirEncode="";
        try {
            timeTakhirEncode = URLEncoder.encode(timeTakhir,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_vaziyat_darsi&student_id=" + student_id + "&time_takhir=" + timeTakhirEncode;
        itShouldLoadMore = false;
        /*final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }
    public static void updateStudentInHozorGhiyab_VaziyatAkhlaghi(final Context c, String student_id,
                                                             final ArrayList<RecyclerModel> recyclerModels,
                                                             final int position,final String timeTakhir) {
        String timeTakhirEncode="";
        try {
            timeTakhirEncode = URLEncoder.encode(timeTakhir,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_vaziyat_akhlaghi&student_id=" + student_id + "&time_takhir=" + timeTakhirEncode;
        itShouldLoadMore = false;
        /*final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void updateStudentInHozorGhiyab(final Context c, String student_id, final ImageView imgTik,
                                                  final ArrayList<RecyclerModel> recyclerModels, final int position) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student&student_id=" + student_id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            recyclerModels.get(position).setMatn("0");
                            imgTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ghayebtik));
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataStudentForHozorGhiyab(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final ImageView img_refresh, final WebView webView,
                                                          final Timer timer, final TextView net_state,
                                                          final RecyclerView recyclerView, final String selectedClass,
                                                          String jalaseName, String username, final EditText etTozihat,
                                                          final EditText etTaklif) {
        String classNameEncode="";
        String jalaseNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(selectedClass,"UTF-8");
            jalaseNameEncode = URLEncoder.encode(jalaseName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_student_hozorghiyab&limit=" + LOAD_LIMIT + "&class=" + classNameEncode + "&jalase=" + jalaseNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }
                String tozihatJalase = null;
                String taklifJalase = null;
                String vaziyatDarsi = null;
                String vaziyatAkhlaghi = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String schoolName = jsonObject.getString("std_name");
                        String className = jsonObject.getString("hazer_ya_na");
                        String timeTakhir= jsonObject.getString("time_takhir");
                        String idTimeTakhir= jsonObject.getString("id_time_takhir");
                        String studentPicture = jsonObject.getString("std_picture");
                        tozihatJalase = jsonObject.getString("tozihat");
                        taklifJalase = jsonObject.getString("taklif");
                        vaziyatDarsi = jsonObject.getString("vaziyat_darsi");
                        vaziyatAkhlaghi = jsonObject.getString("vaziyat_akhlaghi");
                        int idTimeTakhirConvert = Integer.parseInt(idTimeTakhir);

                        recyclerModels.add(new RecyclerModel(lastId,schoolName, className,studentPicture,tozihatJalase,timeTakhir,vaziyatDarsi,vaziyatAkhlaghi,idTimeTakhirConvert,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                etTozihat.setText(tozihatJalase);
                etTaklif.setText(taklifJalase);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ImageView img_refresh, final WebView webView,
                                       final Timer timer, final TextView net_state,
                                       final RecyclerView recyclerView, final String selectedClass,String username) {
        //below line for send persian sting to server dar android 4.4 bekar mire
        String selectedClassEncode="";
        try {
            selectedClassEncode = URLEncoder.encode(selectedClass,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_student&limit=" + LOAD_LIMIT + "&class=" + selectedClassEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String std_id = jsonObject.getString("std_id");
                        String classId = jsonObject.getString("class_id");
                        String teacherId = jsonObject.getString("teacher_id");
                        String studentName = jsonObject.getString("std_name");
                        String className = jsonObject.getString("class_name");
                        String studentPicture = jsonObject.getString("std_picture");

                        recyclerModels.add(new RecyclerModel(lastId,studentName, className,studentPicture,"","","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadDarsResultForStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final ImageView img_refresh, final WebView webView,
                                            final Timer timer, final TextView net_state,
                                            final RecyclerView recyclerView, final String selectedClass,String username) {
        String selectedClassEncode="";
        try {
            selectedClassEncode = URLEncoder.encode(selectedClass,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_dars_result_student&limit=" + LOAD_LIMIT + "&dars_id=" + selectedClassEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("jalase_id");
                        String jalaseName = jsonObject.getString("jalase_name");
                        //String className = jsonObject.getString("class_name");
                        String classId = jsonObject.getString("class_id");
                        String hazer_ya_na = jsonObject.getString("hazer_ya_na");
                        String vaziyat_darsi = jsonObject.getString("vaziyat_darsi");
                        String vaziyat_akhlaghi = jsonObject.getString("vaziyat_akhlaghi");
                        String taklif = jsonObject.getString("taklif");

                        recyclerModels.add(new RecyclerModel(lastId,jalaseName, classId,hazer_ya_na,vaziyat_darsi,vaziyat_akhlaghi,taklif,"",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }



    public static void firstLoadDataJalasat(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final ImageView img_refresh, final WebView webView,
                                            final Timer timer, final TextView net_state,
                                            final RecyclerView recyclerView, final String selectedClass,String username) {
        String selectedClassEncode="";
        try {
            selectedClassEncode = URLEncoder.encode(selectedClass,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_jalasat&limit=" + LOAD_LIMIT + "&class=" + selectedClassEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("jalase_id");
                        String jalaseName = jsonObject.getString("name");
                        String className = jsonObject.getString("class_name");
                        String classId = jsonObject.getString("class_id");
                        String tedadHazerin = jsonObject.getString("tedad_hazerin");
                        String tedadGhayebin = jsonObject.getString("tedad_ghayebin");
                        recyclerModels.add(new RecyclerModel(lastId,jalaseName, className,"",classId,tedadHazerin,"",tedadGhayebin,0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataDarsList(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ImageView img_refresh, final WebView webView,
                                       final Timer timer, final TextView net_state,
                                       final RecyclerView recyclerView,
                                       final String username) {
        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_dars_list&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String className = jsonObject.getString("class_name");
                        String teacherId = jsonObject.getString("teacher_id");
                        String teacherName = jsonObject.getString("teacher_name");
                        String tedadHazeri = jsonObject.getString("tedad_hazeri");
                        String tedadGheybat = jsonObject.getString("tedad_gheybat");
                        String classId = jsonObject.getString("class_id");
                        recyclerModels.add(new RecyclerModel(lastId, className,tedadHazeri,"",tedadGheybat,classId,teacherName,teacherId,0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,username);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadGozareshatUnderSepordanKarWidthLimit(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                      final ArrayList<RecyclerModel> recyclerModels,
                                                      final RecyclerView recyclerView,
                                                      final String username,final String mokhatabId, final String noe,
                                                      final String noeUser, final ConstraintLayout clWifi,String idKar) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String noeUserEncode = UrlEncoderClass.urlEncoder(noeUser);
        String idKarEncode = UrlEncoderClass.urlEncoder(idKar);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_gozareshat_under_sepordan_kar_with_limit&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode + "&noe_user=" + noeUserEncode + "&mokhatab_id=" + mokhatabIdEncode + "&id_kar=" + idKarEncode + "&last_id=" + lastId2;
        itShouldLoadMore = false;
       /* final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");
                        String workNumber = jsonObject.getString("num");

                        recyclerModels.add(new RecyclerModel(lastId2,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,workNumber,null,null));
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(c, lastId2.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataRecivedMessageTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadPvChatWitchLastLoadAfterSendMessage(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,final String username,
                                                               final String mokhatabId,
                                                               final ConstraintLayout clWifi) {


        final String usernameIdEncode = UrlEncoderClass.urlEncoder(username);
        final String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_pv_chat_witdh_last_load&limit=" + LOAD_LIMIT + "&username=" + usernameIdEncode + "&mokhatab_id=" + mokhatabIdEncode + "&lastId=" + lastId3;
        itShouldLoadMore = false;
        //final ProgressDialog progressDialog = new ProgressDialog(c);
        //progressDialog.setMessage("درحال بارگزاری...");
        //progressDialog.setCancelable(false);
        //progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId3 = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");

                        recyclerModels.add(new RecyclerModel(lastId3,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.loadPvChat(c, recyclerAdapter, recyclerModels,
                                recyclerView,username, mokhatabIdEncode,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void loadPvChat(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                    final ArrayList<RecyclerModel> recyclerModels,
                                    final RecyclerView recyclerView,final String username,
                                    final String mokhatabId, final ConstraintLayout clWifi) {

        final String usernameIdEncode = UrlEncoderClass.urlEncoder(username);
        final String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_pv_chat&limit=" + LOAD_LIMIT + "&username=" + usernameIdEncode + "&mokhatab_id=" + mokhatabIdEncode + "&lastId=" + lastId3;
        itShouldLoadMore = false;
        //final ProgressDialog progressDialog = new ProgressDialog(c);
        //progressDialog.setMessage("درحال بارگزاری...");
        //progressDialog.setCancelable(false);
        //progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId3 = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");

                        recyclerModels.add(new RecyclerModel(lastId3,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();
                        //recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.loadPvChat(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,mokhatabIdEncode,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void loadMainChat(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final RecyclerView recyclerView,
                                                          final String username, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_message&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode ;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");
                        String idMokhatab = jsonObject.getString("id_mokhatab");

                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,tarikh,nameFerestande,noe,idMokhatab,"",0,vaziyatTaeid,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.loadMainChat(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void loadGozareshatUnderSepordanKar(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                            final ArrayList<RecyclerModel> recyclerModels,
                                                            final RecyclerView recyclerView,
                                                            final String username,final String mokhatabId, final String noe,
                                                            final String noeUser, final ConstraintLayout clWifi,String idKar) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String noeUserEncode = UrlEncoderClass.urlEncoder(noeUser);
        String idKarEncode = UrlEncoderClass.urlEncoder(idKar);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_gozareshat_under_sepordan_kar&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode + "&noe_user=" + noeUserEncode + "&mokhatab_id=" + mokhatabIdEncode + "&id_kar=" + idKarEncode;
        itShouldLoadMore = false;
       /* final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");
                        String workNumber = jsonObject.getString("num");

                        recyclerModels.add(new RecyclerModel(lastId2,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,workNumber,null,null));
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataRecivedMessageTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataRecivedMessageChatWorks(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                       final ArrayList<RecyclerModel> recyclerModels,
                                                       final RecyclerView recyclerView,
                                                       final String username,final String mokhatabId, final String noe,
                                                       final String noeUser, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String noeUserEncode = UrlEncoderClass.urlEncoder(noeUser);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_recived_message_chat_tab&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode + "&noe_user=" + noeUserEncode + "&mokhatab_id=" + mokhatabIdEncode;
        itShouldLoadMore = false;
       /* final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");
                        String workNumber = jsonObject.getString("num");

                        String readOrNo = jsonObject.getString("read_or_no");

                        recyclerModels.add(new RecyclerModel(lastId2,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,workNumber,null,readOrNo));
                        recyclerAdapter.notifyDataSetChanged();
                        //recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataRecivedMessageTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataRecivedMessageChat(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final RecyclerView recyclerView,
                                                          final String username,final String mokhatabId, final String noe,
                                                          final String noeUser, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String noeUserEncode = UrlEncoderClass.urlEncoder(noeUser);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_recived_message_chat_tab&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode + "&noe_user=" + noeUserEncode + "&mokhatab_id=" + mokhatabIdEncode;
        itShouldLoadMore = false;
       /* final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");

                        recyclerModels.add(new RecyclerModel(lastId2,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataRecivedMessageTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataRecivedMessageTeacher(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final RecyclerView recyclerView,
                                                          final String username, final String noe, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_recived_message_teacher&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
       /* final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");

                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0,vaziyatTaeid,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataRecivedMessageTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void LoadSearchResultForSepordanKar(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                        final ArrayList<RecyclerModel> recyclerModels,
                                        final RecyclerView recyclerView,
                                        final String username, String query, final ConstraintLayout clWifi, final String noe
            , final String noeMessage) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String queryEncode= UrlEncoderClass.urlEncoder(query);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);
        String noeMessageEncode= UrlEncoderClass.urlEncoder(noeMessage);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_search_result_chat&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&query=" + queryEncode + "&noe=" + noeEncode + "&noe_message=" + noeMessageEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //ProgressDialogClass.dismissProgress();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String studentId = jsonObject.getString("id");
                        String nameStudent = jsonObject.getString("family");
                        String std_picture = jsonObject.getString("picture");

                        recyclerModels.add(new RecyclerModel(lastId,studentId, nameStudent,std_picture,"","","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noeMessage.equals("")){
                            LoadData.LoadSearchResult(c, recyclerAdapter, recyclerModels,
                                    recyclerView, username, "",clWifi,noe,noeMessage);
                        }else {
                            LoadData.LoadSearchResult(c, recyclerAdapter, recyclerModels,
                                    recyclerView, username, "",clWifi,noe,"");
                        }

                    }
                });

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }



    public static void LoadSearchResult(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                               final ArrayList<RecyclerModel> recyclerModels,
                                               final RecyclerView recyclerView,
                                               final String username, String query, final ConstraintLayout clWifi, final String noe
                                             , final String noeMessage) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String queryEncode= UrlEncoderClass.urlEncoder(query);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);
        String noeMessageEncode= UrlEncoderClass.urlEncoder(noeMessage);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_search_result_chat&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&query=" + queryEncode + "&noe=" + noeEncode + "&noe_message=" + noeMessageEncode;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //ProgressDialogClass.dismissProgress();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String studentId = jsonObject.getString("id");
                        String nameStudent = jsonObject.getString("family");
                        String std_picture = jsonObject.getString("picture");

                        recyclerModels.add(new RecyclerModel(lastId,studentId, nameStudent,std_picture,"","","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noeMessage.equals("")){
                            LoadData.LoadSearchResult(c, recyclerAdapter, recyclerModels,
                                    recyclerView, username, "",clWifi,noe,noeMessage);
                        }else {
                            LoadData.LoadSearchResult(c, recyclerAdapter, recyclerModels,
                                    recyclerView, username, "",clWifi,noe,"");
                        }

                    }
                });

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void ListDarkhastMorkhasiDarBakhshPv(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final RecyclerView recyclerView,
                                            final String username,final String mokhatabId, final ConstraintLayout clWifi) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode= UrlEncoderClass.urlEncoder(mokhatabId);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_darkhast_morkhasi_dar_bakhsh_pv&limit=" + LOAD_LIMIT + "&username=" + usernameEncode + "&mokhatab_id=" + mokhatabIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String tarikh = jsonObject.getString("tarikh");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String elat = jsonObject.getString("elat");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        String family = jsonObject.getString("family");
                        String user_id = jsonObject.getString("user_id");
                        recyclerModels.add(new RecyclerModel(lastId2,tarikh, saat_vorod,saat_khoroj,elat,vaziyat_taeid,family,user_id,0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                //clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, mokhatabId,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void ListMakharej(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                    final ArrayList<RecyclerModel> recyclerModels,
                                    final RecyclerView recyclerView,
                                    final String username, final ConstraintLayout clWifi,String noe) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_makharej&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        //String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String elat = jsonObject.getString("elat");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        String family = jsonObject.getString("family");
                        String num = jsonObject.getString("num");

                        String mokhatabin = jsonObject.getString("mokhatabin");

                        String tarikh = jsonObject.getString("tarikh");
                        String makan = jsonObject.getString("makan");
                        String tozihat = jsonObject.getString("tozihat");

                        recyclerModels.add(new RecyclerModel(lastId2,makan, saat_vorod,mokhatabin,elat,vaziyat_taeid,family,num,0,tarikh,tozihat,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }




    public static void ListDarkhastJalase(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final RecyclerView recyclerView,
                                            final String username, final ConstraintLayout clWifi,String noe) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_darkhast_jalase&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String tarikh = jsonObject.getString("tarikh");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        //String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String elat = jsonObject.getString("elat");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        String family = jsonObject.getString("family");
                        String num = jsonObject.getString("num");

                        String mokhatabin = jsonObject.getString("mokhatabin");
                        String makan = jsonObject.getString("makan");
                        String tozihat = jsonObject.getString("tozihat");

                        recyclerModels.add(new RecyclerModel(lastId2,tarikh, saat_vorod,mokhatabin,elat,vaziyat_taeid,family,num,0,makan,tozihat,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void ListDarkhastMorkhasi(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                             final ArrayList<RecyclerModel> recyclerModels,
                                             final RecyclerView recyclerView,
                                             final String username, final ConstraintLayout clWifi,String noe) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_darkhast_morkhasi&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String tarikh = jsonObject.getString("tarikh");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String elat = jsonObject.getString("elat");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        String family = jsonObject.getString("family");
                        recyclerModels.add(new RecyclerModel(lastId,tarikh, saat_vorod,saat_khoroj,elat,vaziyat_taeid,family,"",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void LoadMajmoeKolGozareshat(final Context c, final String username, final String mokhatabId,
                                         final TextView txMajmoeKolKarkard,final TextView txTedadKol,final String noe,
                                               final String noeUser, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode = UrlEncoderClass.urlEncoder(mokhatabId);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String noeUserEncode = UrlEncoderClass.urlEncoder(noeUser);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=majmoe_kol_gozareshat&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode + "&noe_user=" + noeUserEncode + "&mokhatab_id=" + mokhatabIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String tedadGozareshatTaeidShode = jsonObject.getString("tedad_gozareshat_taeid_shode");
                        String tedadKolGozareshat = jsonObject.getString("tedad_kol_gozareshat");

                        txMajmoeKolKarkard.setText(new EnglishNumberToPersian().convert(tedadGozareshatTaeidShode));
                        txTedadKol.setText(new EnglishNumberToPersian().convert(tedadKolGozareshat));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                //clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //LoadData.LoadMajmoeKolSaat(c, username,mokhatabId.toString(),txMajmoeKolKarkard,txTedadKol,clWifi);

                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void LoadMajmoeKolSaat(final Context c, final String username, final String mokhatabId,
                                         final TextView txMajmoeKolKarkard,final TextView txTedadKol,final TextView txMiyanginKarkardRozane, final ConstraintLayout clWifi) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode= UrlEncoderClass.urlEncoder(mokhatabId);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_majmoe_karkard&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&mokhatab_id=" + mokhatabIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String majmoeKolKarkard = jsonObject.getString("majmoe_kol_karkard");
                        String tedad_kol = jsonObject.getString("tedad_kol");
                        String miyanginKarkardRozane = jsonObject.getString("miyangin_karkard_rozane");
                        if (majmoeKolKarkard.matches("0")){

                        }else {
                            txMajmoeKolKarkard.setText(new EnglishNumberToPersian().convert(majmoeKolKarkard.substring(0,majmoeKolKarkard.length() - 3)));
                            txTedadKol.setText(new EnglishNumberToPersian().convert(tedad_kol));
                            txMiyanginKarkardRozane.setText(new EnglishNumberToPersian().convert( "۰" + miyanginKarkardRozane.substring(0,1) + ":" + miyanginKarkardRozane.substring(1,3)));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                //clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.LoadMajmoeKolSaat(c, username,mokhatabId.toString(),txMajmoeKolKarkard,txTedadKol,txMiyanginKarkardRozane,clWifi);

                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void ListVorodKhorojErsaliDarPv(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                             final ArrayList<RecyclerModel> recyclerModels,
                                             final RecyclerView recyclerView,
                                             final String username,final String mokhatabId, final ConstraintLayout clWifi) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String mokhatabIdEncode= UrlEncoderClass.urlEncoder(mokhatabId);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_vorod_khoroj_ersali_dar_pv&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&mokhatab_id=" + mokhatabIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String tarikh = jsonObject.getString("tarikh");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        String family = jsonObject.getString("family");
                        String user_id = jsonObject.getString("user_id");
                        String majmoeKarkard = jsonObject.getString("majmoe_karkard");
                        String ezafeKari = jsonObject.getString("noe");

                        recyclerModels.add(new RecyclerModel(lastId2,new EnglishNumberToPersian().convert(tarikh), new EnglishNumberToPersian().convert(saat_vorod),new EnglishNumberToPersian().convert(saat_khoroj),vaziyat_taeid,majmoeKarkard,family,user_id,0,null,ezafeKari,saat_vorod,saat_khoroj));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                //clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void ListVorodKhorojErsali(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final ConstraintLayout clWifi,String noe) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_vorod_khoroj_ersali&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode  +"&noe=" + noeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("درحال بارگزاری...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId2 = jsonObject.getString("id");
                        String tarikh = jsonObject.getString("tarikh");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        String family = jsonObject.getString("family");
                        String majmoeKarkard = jsonObject.getString("majmoe_karkard");
                        String noe = jsonObject.getString("noe");

                        recyclerModels.add(new RecyclerModel(lastId2,new EnglishNumberToPersian().convert(tarikh), new EnglishNumberToPersian().convert(saat_vorod),new EnglishNumberToPersian().convert(saat_khoroj),vaziyat_taeid,majmoeKarkard,family,saat_vorod,0,null,noe,saat_vorod,saat_khoroj));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void firstLoadDataListPayamHayeErsaliTeacher(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final String noe, final ConstraintLayout clWifi) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_list_payamhaye_ersali_teacher&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameFerestande = jsonObject.getString("name_daryaft_konande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String readOrNo = jsonObject.getString("read_or_no");
                        String vaziyatTaeid = jsonObject.getString("vaziyat_taeid");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,tarikh,nameFerestande,noe,"",readOrNo,0,vaziyatTaeid,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        //class mySingleton baes misheh ke yek tread bishtar az yek bar sakhte nashe v roye sorat
        //va performance barname tasir ziyadi dare makhsosan vaghti bekhahim yek code ro hamash
        //refresh konim
        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
        //Volley.newRequestQueue(c).add(jsonArrayRequest);


    }

    public static void firstLoadDataAllMessage(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final ConstraintLayout clWifi) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_list_hameye_payam_ha&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameDaryaftKonandeh = jsonObject.getString("name_daryaft_konande");
                        String nameErsalKonandeh = jsonObject.getString("name_ersal_konande");
                        String tarikh = jsonObject.getString("tarikh");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,"",nameErsalKonandeh,nameDaryaftKonandeh,tarikh,"",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                clWifi.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.firstLoadDataListPayamHayeErsaliStudent(c,recyclerAdapter,recyclerModels,recyclerView,username,clWifi);


                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }


    public static void firstLoadDataListPayamHayeErsaliStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final ConstraintLayout clWifi) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_list_payamhaye_ersali_student&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                //clWifi.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameFerestande = jsonObject.getString("name_daryaft_konande");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,"",nameFerestande,"","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                /*clWifi.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.firstLoadDataListPayamHayeErsaliStudent(c,recyclerAdapter,recyclerModels,recyclerView,username,clWifi);


                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }

    public static void firstLoadDataRecivedMessageStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final RecyclerView recyclerView,
                                                          final String username, final ConstraintLayout clWifi) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_recived_message_student&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,"",nameFerestande,"","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;

                clWifi.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.firstLoadDataRecivedMessageStudent(c,recyclerAdapter,recyclerModels,recyclerView,username,clWifi);


                    }
                });

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataYH(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ImageView img_refresh, final WebView webView,
                                       final Timer timer, final TextView net_state,
                                       final RecyclerView recyclerView,
                                       final String username) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadclass&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("class_id");
                        String className = jsonObject.getString("class_name");
                        String schoolName = jsonObject.getString("school_name");
                        String teacherId = jsonObject.getString("teacher_id");
                        String tedad_student = jsonObject.getString("tedad_student");
                        recyclerModels.add(new RecyclerModel(lastId,schoolName, className,"",teacherId,tedad_student,"","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,username);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadMoreClass(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final ProgressBar progressBar,String username) {


        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadmore_class&lastId=" + lastId + "&limit=" + LOAD_LIMIT + "&user1=" + username;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("class_id");
                        String schoolName = jsonObject.getString("school_name");
                        String className = jsonObject.getString("class_name");
                        String teacher_id = jsonObject.getString("teacher_id");
                        String tedad_student = jsonObject.getString("tedad_student");
                        recyclerModels.add(new RecyclerModel(lastId,schoolName, className,"",teacher_id,tedad_student,"","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }


    //Load more below :

    public static void loadMore(final Context c, final RecyclerAdapter recyclerAdapter, final ArrayList<RecyclerModel> recyclerModels, final ProgressBar progressBar) {


        String url = "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=loadmore&lastId=" + lastId + "&limit=" + LOAD_LIMIT;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("matn");
                        String picture = jsonObject.getString("p1");
                        String city = jsonObject.getString("city");
                        String position = jsonObject.getString("position");
                        float rate = jsonObject.getInt("rate");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,picture,city,"","","",0,null,null,null,null));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }




}
