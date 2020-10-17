package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.cityDetail.Recyclerview
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.gozaresh_kar.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*


class GozareshKar : AppCompatActivity(), View.OnTouchListener {

    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null

    private  var rAdapterSpinner:RecyclerAdapterYouHaveKnow? = null
    private  var rModelsSpinner: ArrayList<RecyclerModel>? = null
    var selectedItem: String? = null
    var selectedItemAdminId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gozaresh_kar)

        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("gozaresh_kar", "gozaresh_kar")
            startActivity(intent)
            finish()
        }

        etGozaresh.setOnTouchListener(this)
        etNatige.setOnTouchListener(this)

        val timeKononi = TimeKononi()
        txDate.setText(timeKononi.persianTime)

        var username = SharedPrefClass.getUserId(this,"user")


        //Adapter zir faghat baraye zakhireh ye class ide baraye spinner be kar mire:
        rModelsSpinner = ArrayList<RecyclerModel>()
        rAdapterSpinner = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "add_jalase", this, rAdapterYouHaveKnow,
                selectedItem, null, null, null, "")

        val list: List<String> = ArrayList()

        val spinnerArrayAdapter = ArrayAdapter(
                this, R.layout.spinnter_dropdown_custom, list)
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinnter_dropdown_custom)
        spinner.adapter = spinnerArrayAdapter


        //خط زیر برای لود ادمین هاست
        LoadData.loadAdminList(this, list, spinnerArrayAdapter, username, rAdapterSpinner, rModelsSpinner)

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedItem = adapterView.getItemAtPosition(i).toString()
                selectedItemAdminId = rModelsSpinner!![i].id
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }


        imgSend.setOnClickListener{
            val gozaresh: String = etGozaresh.getText().toString()
            //val natige: String = etNatige.getText().toString()
            val date: String = txDate.getText().toString()

            if (gozaresh.length <= 0 || gozaresh == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                LoadData.sendGozareshKar(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                        username, selectedItemAdminId, etGozaresh, etNatige,date,clWifiState)
            }
        }

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                LoadData.loadCountMessageNotRead(this@GozareshKar, txCountNotReadMessage, username)

                ha.postDelayed(this, 1000)
            }
        }, 1000)

        imgInboxMessage.setOnClickListener{
            startActivity(Intent(this, InboxMessage::class.java))
            finish()
        }

        imgHome.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        imgBack.setOnClickListener{
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
