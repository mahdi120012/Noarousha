package hozorghiyab.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hozorghiyab.R
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.customClasses.EnglishNumberToPersian
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.darkhast_morkhasi.*
import kotlinx.android.synthetic.main.darkhast_morkhasi.spinner
import kotlinx.android.synthetic.main.gozaresh_kar.*
import kotlinx.android.synthetic.main.gozaresh_kar.imgBack
import kotlinx.android.synthetic.main.gozaresh_kar.imgListGozareshat
import kotlinx.android.synthetic.main.gozaresh_kar.imgSend
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.ArrayList


class DarkhastMorkhasi : AppCompatActivity(), DatePickerDialog.OnDateSetListener,com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null

    private  var rAdapterSpinner: RecyclerAdapterYouHaveKnow? = null
    private  var rModelsSpinner: ArrayList<RecyclerModel>? = null
    var selectedItem: String? = null
    var selectedItemAdminId: String? = null
    var saatShoroYaPayan = ""
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.darkhast_morkhasi)

        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("darkhast_morkhasi", "darkhast_morkhasi")
            startActivity(intent)
            finish()
        }
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedItem = adapterView.getItemAtPosition(i).toString()
                selectedItemAdminId = rModelsSpinner!![i].id
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }



        etTarikhShoro.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val persianCalendar = PersianCalendar()
                val datePickerDialog = DatePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay
                )
                datePickerDialog.show(fragmentManager, "TarikhShoro")
                return@OnTouchListener true
            }
            false
        })

        etTarikhPayan.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val persianCalendar = PersianCalendar()
                val datePickerDialog = DatePickerDialog.newInstance(this@DarkhastMorkhasi,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay)
                datePickerDialog.show(fragmentManager, "TarikhPayan")
                return@OnTouchListener true
            }
            false
        })


        etSaatShoro.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                saatShoroYaPayan = "saat_shoro"

                val persianCalendar = PersianCalendar()
                val timePickerDialog = com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                        persianCalendar.get(PersianCalendar.MINUTE), true)
                timePickerDialog.show(fragmentManager, "saat_shoro")


                return@OnTouchListener true
            }
            false
        })

        etSaatPayan.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {


                saatShoroYaPayan = "saat_payan"

                val persianCalendar = PersianCalendar()
                val timePickerDialog = com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                        persianCalendar.get(PersianCalendar.MINUTE), true)
                timePickerDialog.show(fragmentManager, "saat_payan")

                return@OnTouchListener true
            }
            false
        })


        imgSend.setOnClickListener{
            val saatVorod: String = etSaatShoro.getText().toString()
            val saatKhoroj: String = etSaatPayan.getText().toString()
            val tarikhShoro: String = etTarikhShoro.getText().toString()
            val tarikhPayan: String = etTarikhPayan.getText().toString()

            val elat: String = etElat.getText().toString()
            if (saatVorod.length <= 0 || saatVorod == null || saatKhoroj.length <= 0 || saatKhoroj == null
                    || tarikhShoro.length <= 0 || tarikhShoro == null || tarikhPayan.length <= 0 || tarikhPayan == null
                    || elat.length <= 0 || elat == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                val timeKononi = TimeKononi()

                LoadData.sendDarkhastMorkhasi(this, username, timeKononi.persianTime  ,tarikhPayan + " " + saatKhoroj,tarikhShoro + " " + saatVorod, elat,clWifiState)
            }
        }

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                LoadData.loadCountMessageNotRead(this@DarkhastMorkhasi, txCountNotReadMessage, username)

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


    override fun onBackPressed() {
        finish()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val month = monthOfYear + 1
        var fm = "" + month
        var fd = "" + dayOfMonth
        if (month < 10) {
            fm = "0$month"
        }
        if (dayOfMonth < 10) {
            fd = "0$dayOfMonth"
        }

        val date = "$year/$fm/$fd"

        if (view.toString().contains("TarikhShoro")){



            etTarikhShoro.setText(EnglishNumberToPersian().convert(date))
        }else{
            etTarikhPayan.setText(EnglishNumberToPersian().convert(date))
        }
    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        val hourString = if (hourOfDay < 10) "0$hourOfDay" else "" + hourOfDay
        val minuteString = if (minute < 10) "0$minute" else "" + minute
        val time = "$hourString:$minuteString"


        if (saatShoroYaPayan.contains("saat_shoro")){
            etSaatShoro.setText(EnglishNumberToPersian().convert(time))
        }else{
            etSaatPayan.setText(EnglishNumberToPersian().convert(time))
        }
    }

}
