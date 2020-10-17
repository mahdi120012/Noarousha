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
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import hozorghiyab.customClasses.UrlEncoderClass
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import kotlinx.android.synthetic.main.write_new_message_teacher.*
import kotlinx.android.synthetic.main.write_sepordan_kar.*
import kotlinx.android.synthetic.main.write_sepordan_kar.imgBackInSendMessageTeacher
import java.util.*

class WriteSepordanKar : AppCompatActivity(), View.OnTouchListener {

    private val rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private val rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    var rvListYouHaveKnow: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_sepordan_kar)

        val intent = intent
        val list_id = intent.getStringArrayListExtra("id")
        val list_family = intent.getStringArrayListExtra("family")
        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}

        var s = ""
        for (i in list_family.indices) {
            s += list_family[i].toString() + ", "
        }

        txRecivers.setText(s)

        etKar1.setOnTouchListener(this)
        etKar2.setOnTouchListener(this)
        etKar3.setOnTouchListener(this)
        etKar4.setOnTouchListener(this)
        etKar5.setOnTouchListener(this)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        if (noe.equals("student")){

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    val urlAppend = "?action=load_student_count_not_read_message" +
                            "&user1=" + UrlEncoderClass.urlEncoder(username)
                    LoadData.loadCountMessageNotRead(this@WriteSepordanKar, txCountNotReadMessage,username)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else{
            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {

                    LoadData.loadCountMessageNotRead(this@WriteSepordanKar, txCountNotReadMessage, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)
        }



        imgSend.setOnClickListener{
            var onvan: String = etKar1.getText().toString()
            //val matn: String = etKar2.getText().toString()
            if (onvan.length <= 0 || onvan == null) {
                Toast.makeText(this@WriteSepordanKar, "لطفا یک کار را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                val timeKononi = TimeKononi()
                var nowTime = timeKononi.persianTime

                for (i in list_id.indices) {

                    if (noe.equals("student")){
                        LoadData.sendMessageStudent(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvListYouHaveKnow, username, list_id[i], onvan, "",nowTime)
                    }else{
                        if (ahkam.toString().equals("ahkam")){
                            LoadData.sendMessageTeacherInWriteNewMessage(this@WriteSepordanKar, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    username, list_id.get(i), etOnvanSendTeacherMessage, "",clWifiState,nowTime,ahkam.toString(),null)
                        }else if(sepordanKar.toString().equals("sepordan_kar")){
                            LoadData.sendSepordanKar(this@WriteSepordanKar, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    username, list_id.get(i), etKar1, etKar2, etKar3, etKar4, etKar5, clWifiState,nowTime,sepordanKar.toString(),null)
                        }else{
                            LoadData.sendMessageTeacherInWriteNewMessage(this@WriteSepordanKar, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    username, list_id.get(i), etOnvanSendTeacherMessage, "",clWifiState,nowTime,"",null)
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
        if (view.id == R.id.etKar1) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        } else if (view.id == R.id.etKar2) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        } else if (view.id == R.id.etKar3) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        } else if (view.id == R.id.etKar4) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        } else if (view.id == R.id.etKar5) {
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
