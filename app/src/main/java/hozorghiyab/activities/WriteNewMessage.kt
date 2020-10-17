package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.hozorghiyab.R
import hozorghiyab.cityDetail.*
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import kotlinx.android.synthetic.main.write_new_message_teacher.*
import kotlinx.android.synthetic.main.write_new_message_teacher.txReciverFamilyInTeacher
import java.util.*

class WriteNewMessage : AppCompatActivity(), View.OnTouchListener {

    private val rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private val rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    var rvListYouHaveKnow: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_new_message_teacher)

        val intent = intent
        val list_id = intent.getStringArrayListExtra("id")
        val list_family = intent.getStringArrayListExtra("family")
        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}

        var s = ""
        for (i in list_family.indices) {
            s += list_family[i].toString() + ", "
        }

        txReciverFamilyInTeacher.setText(s)

        etOnvanSendTeacherMessage.setOnTouchListener(this)
        etMatnSendTeacherMessage.setOnTouchListener(this)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        if (noe.equals("student")){

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    val urlAppend = "?action=load_student_count_not_read_message" +
                            "&user1=" + UrlEncoderClass.urlEncoder(username)
                    LoadData.loadCountMessageNotRead(this@WriteNewMessage, txCountNotReadMessage,username)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else{
            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {

                    LoadData.loadCountMessageNotRead(this@WriteNewMessage, txCountNotReadMessage, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)
        }



        imgSendMessageTeacher.setOnClickListener{
            var onvan: String = etOnvanSendTeacherMessage.getText().toString()
            val matn: String = etMatnSendTeacherMessage.getText().toString()
            if (onvan.length <= 0 || onvan == null || matn.length <= 0 || matn == null) {
                Toast.makeText(this@WriteNewMessage, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                val timeKononi = TimeKononi()
                var nowTime = timeKononi.persianTime

                for (i in list_id.indices) {

                    if (noe.equals("student")){
                        LoadData.sendMessageStudent(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvListYouHaveKnow, username, list_id[i], onvan, matn,nowTime)
                    }else{
                        if (ahkam.toString().equals("ahkam")){
                            LoadData.sendMessageTeacherInWriteNewMessage(this@WriteNewMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    username, list_id.get(i), etOnvanSendTeacherMessage, matn,clWifiState,nowTime,ahkam.toString(),null)
                        }else if(sepordanKar.toString().equals("sepordan_kar")){
                            LoadData.sendMessageTeacherInWriteNewMessage(this@WriteNewMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    username, list_id.get(i), etOnvanSendTeacherMessage, matn,clWifiState,nowTime,sepordanKar.toString(),null)
                        }else{
                            LoadData.sendMessageTeacherInWriteNewMessage(this@WriteNewMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    username, list_id.get(i), etOnvanSendTeacherMessage, matn,clWifiState,nowTime,"",null)
                        }

                    }
                }

            }
        }

        imgInboxMessage.setOnClickListener{
                startActivity(Intent(this, InboxMessage::class.java))
                finish()
        }

        imgHome.setOnClickListener{
            if (noe.equals("student")){
                startActivity(Intent(this, StudentPanelMainKt::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
        imgBackInSendMessageTeacher.setOnClickListener{
            finish()
        }

        imgMassenger.setOnClickListener{

            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }

    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (view.id == R.id.etMatnSendTeacherMessage) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        } else if (view.id == R.id.etOnvanSendTeacherMessage) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }
}
