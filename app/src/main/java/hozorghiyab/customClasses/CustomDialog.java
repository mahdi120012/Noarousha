package hozorghiyab.customClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hozorghiyab.R;

import java.util.ArrayList;

import hozorghiyab.activities.WriteNewMessage;
import hozorghiyab.cityDetail.LoadData;
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow;
import hozorghiyab.cityDetail.RecyclerModel;

public class CustomDialog {

    public static void changePassword(final Context context, final Activity activity) {
        final Dialog dialog = new Dialog(context, R.style.customDialog2);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_change_password, null, false);
        final EditText etNewPassword = view.findViewById(R.id.etNewPassword);
        final EditText etTekrarNewPassword = view.findViewById(R.id.etTekrarNewPassword);
        ConstraintLayout clChangePassword = view.findViewById(R.id.clChangePassword);
        ImageView imgBack = view.findViewById(R.id.imgBack);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyboard.hideKeyboard(context, activity);
                dialog.dismiss();

            }
        });

        clChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = SharedPrefClass.getUserId(context,"user");

                String newPassword = etNewPassword.getText().toString();
                String tekrarNewPassword = etTekrarNewPassword.getText().toString();

                if (newPassword.length() < 6){
                    Toast.makeText(context, "رمز عبور کوتاه است", Toast.LENGTH_SHORT).show();
                }else if (newPassword.equals(tekrarNewPassword)){
                    LoadData.updatePassword(context,username,newPassword,etNewPassword,etTekrarNewPassword,dialog);
                }else {
                    Toast.makeText(context, "رمز های وارد شده با هم برابر نیستند", Toast.LENGTH_SHORT).show();
                }
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




/*
    public static void allDialogButton (final Context context, final int position, final String method,
                     final ImageView imgVaziyatTaeid,final TextView txOnvanMessageInRecivedMessage,
                     final String noeGozaresh, final ArrayList<String>list_family,final ArrayList<String> list_id,
                     final ArrayList<RecyclerModel> recyclerModels,final RecyclerAdapterYouHaveKnow adapter) {
        final Dialog dialog = new Dialog(context, R.style.customDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_inbox_message, null, false);

        ConstraintLayout clErsalNazar = view.findViewById(R.id.clErsalNazar);
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




    public static void clickDialogItems(final Context context, final int position, final String method,
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
                    LoadData.removeMessage(context,recyclerModels.get(position).getId());
                    recyclerModels.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, recyclerModels.size());
                }else if (method.equals("remove_class")){

                    LoadData.removeClass(context,recyclerModels.get(position).getId());
                    recyclerModels.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, recyclerModels.size());

                }else if (method.equals("remove_jalase")){
                    LoadData.removeJalase(context,recyclerModels.get(position).getId(),
                            recyclerModels.get(position).getCity());

                    recyclerModels.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, recyclerModels.size());

                }else if (noeGozaresh != null){
                         if (noeGozaresh.equals("saat_vorod_khoroj")){
                             LoadData.updateVaziyatGozaresh(context,recyclerModels.get(position).getId(),imgVaziyatTaeid,"تایید شده",noeGozaresh.toString());
                         }else {
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
    }*/
}
