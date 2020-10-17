package hozorghiyab.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hozorghiyab.R
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import hozorghiyab.cityDetail.*
import hozorghiyab.customClasses.EnglishNumberToPersian
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.makharej.*
import kotlinx.android.synthetic.main.makharej.imgBack
import kotlinx.android.synthetic.main.mohasebe_tashvighi.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.tanzim_jalase.*
import kotlinx.android.synthetic.main.tanzim_jalase.etTarikh
import kotlinx.android.synthetic.main.tanzim_jalase.etTozihat
import kotlinx.android.synthetic.main.tanzim_jalase.imgListJalasat
import kotlinx.android.synthetic.main.tanzim_jalase.imgSend
import kotlinx.android.synthetic.main.tanzim_jalase.spinner
import kotlinx.android.synthetic.main.toolbar_button.*
import java.text.DecimalFormat
import java.util.*

class Makharej : AppCompatActivity(), DatePickerDialog.OnDateSetListener,com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    private val rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private val rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    private  var rAdapterSpinner:RecyclerAdapterYouHaveKnow? = null
    private  var rModelsSpinner: ArrayList<RecyclerModel>? = null
    var selectedItem: String? = null
    var selectedItemAdminId: String? = null

    var rvListYouHaveKnow: RecyclerView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.makharej)
        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")
        /*val intent = intent
        val list_id = intent.getStringArrayListExtra("id")
        val list_family = intent.getStringArrayListExtra("family")
        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}

        var s = ""
        for (i in list_family.indices) {
            s += list_family[i].toString() + ", "
        }*/


        imgListJalasat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("sabt_makharej", "sabt_makharej")
            startActivity(intent)
            finish()
        }



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


        etTarikh.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val persianCalendar = PersianCalendar()
                val datePickerDialog = DatePickerDialog.newInstance(
                        this@Makharej,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay
                )
                datePickerDialog.show(fragmentManager, "Tarikh")
                return@OnTouchListener true
            }
            false
        })






        if (noe.equals("student")){

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    val urlAppend = "?action=load_student_count_not_read_message" +
                            "&user1=" + UrlEncoderClass.urlEncoder(username)
                    LoadData.loadCountMessageNotRead(this@Makharej, txCountNotReadMessage,username)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else{
            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {

                    LoadData.loadCountMessageNotRead(this@Makharej, txCountNotReadMessage, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)
        }



        imgSend.setOnClickListener{
            var mablagh: String = etMablagh.getText().toString()
            val tarikh: String = etTarikh.getText().toString()
            val tozihat: String = etTozihat.getText().toString()

            if (mablagh.length <= 0 || mablagh == null || tarikh.length <= 0 || tarikh == null ||
                    tozihat.length <= 0 || tozihat == null) {
                Toast.makeText(this@Makharej, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                val timeKononi = TimeKononi()
                var nowTime = timeKononi.persianTime

                LoadData.sendMablagh(this, username,mablagh, tarikh ,tozihat,clWifiState,etMablagh,etTarikh,etTozihat)


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
        imgBack.setOnClickListener{
            finish()
        }

        imgMassenger.setOnClickListener{

            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }


        etMablagh.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                etMablagh.removeTextChangedListener(this)
                try {
                    var givenstring = s.toString()
                    val longval: Long
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replace(",".toRegex(), "")
                    }
                    longval = givenstring.toLong()
                    val formatter = DecimalFormat("#,###,###")
                    val formattedString: String = formatter.format(longval)
                    etMablagh.setText(EnglishNumberToPersian().convert(formattedString))
                    etMablagh.setSelection(etMablagh.getText().length)
                    // to place the cursor at the end of text
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                etMablagh.addTextChangedListener(this)
            }
        })


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

        etTarikh.setText(EnglishNumberToPersian().convert(date))

    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        val hourString = if (hourOfDay < 10) "0$hourOfDay" else "" + hourOfDay
        val minuteString = if (minute < 10) "0$minute" else "" + minute
        val time = "$hourString:$minuteString"

        etSaat.setText(EnglishNumberToPersian().convert(time))

    }
}
