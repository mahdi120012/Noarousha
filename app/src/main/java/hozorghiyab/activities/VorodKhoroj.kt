package hozorghiyab.activities

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.gozaresh_kar.imgBack
import kotlinx.android.synthetic.main.gozaresh_kar.imgListGozareshat
import kotlinx.android.synthetic.main.gozaresh_kar.txDate
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import kotlinx.android.synthetic.main.vorod_khoroj.*
import java.util.*

class VorodKhoroj : AppCompatActivity() {

    public var saatVorodGhabli = ""
    public var saatKhorojGhabli = ""
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null

    private  var rAdapterSpinner:RecyclerAdapterYouHaveKnow? = null
    private  var rModelsSpinner: ArrayList<RecyclerModel>? = null
    var selectedItem: String? = null
    var selectedItemAdminId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vorod_khoroj)

        var username = SharedPrefClass.getUserId(this,"user")
        val timeKononi = TimeKononi()

        var ezafe_kari = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ezafe_kari")}
        if (ezafe_kari.toString()!!.equals("ezafe_kari")){
            etElat.visibility = View.VISIBLE
            imgSabtEzafeKari.visibility = View.VISIBLE
            txSabtEzafeKari.visibility = View.VISIBLE
            txElat.visibility = View.VISIBLE
            imgSabtEzafeKari.setImageResource(R.drawable.gozaresh_selected);
            imgErsalGozaresh.setImageResource(R.drawable.gozaresh);
            txReciverFamilyInTeacher6.setText("ثبت اضافه کاری")

            imgErsalGozaresh.setOnClickListener{
                val intent = Intent(this, VorodKhoroj::class.java)
                startActivity(intent)
                finish()
            }


            LoadData.loadVorodKhorojGhabliEzafeKari(this,etSaatVorod,etSaatKhoroj,etElat,timeKononi.persianTime,username,clWifiState)

            etSaatVorod.setOnClickListener {

                //Toast.makeText(this,myclass.schoolName.toString(),Toast.LENGTH_SHORT).show()

                val mcurrentTime: Calendar = Calendar.getInstance()
                val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                if (etSaatVorod.text.equals("")){
                    etSaatVorod.setText(String.format("%02d:%02d", hour, minute))
                }/*else{
                    Toast.makeText(this,"ویرایش امکان پذیر نیست",Toast.LENGTH_SHORT).show()
                }*/


                 mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                         etSaatVorod.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                     }, hour, minute, true) //Yes 24 hour time
                     mTimePicker.setTitle("انتخاب زمان")
                     mTimePicker.show()


            }

            etSaatKhoroj.setOnClickListener{
                val saatVorod: String = etSaatVorod.getText().toString()
                if (saatVorod.length <= 0 || saatVorod == null){
                    Toast.makeText(this, "ابتدا ساعت ورود را انتخاب نمایید", Toast.LENGTH_SHORT).show()
                }else{
                    val mcurrentTime: Calendar = Calendar.getInstance()
                    val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                    val mTimePicker: TimePickerDialog

                    if (etSaatKhoroj.text.equals("")){
                        etSaatKhoroj.setText(String.format("%02d:%02d", hour, minute))
                    }/*else{
                        Toast.makeText(this,"ویرایش امکان پذیر نیست",Toast.LENGTH_SHORT).show()
                    }*/


                    mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                        //--select * from tavasi message WHERE id = ();

                        etSaatKhoroj.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                        //val s = etSaatKhoroj.text.toString().substring(0, 2)
                        //Toast.makeText(this,s.toString() ,Toast.LENGTH_SHORT).show()

                    }, hour, minute, true) //Yes 24 hour time
                    mTimePicker.setTitle("انتخاب زمان")
                    mTimePicker.show()
                }
            }


            txDate.setText(timeKononi.persianTime)

            imgSendSaatVorod.setOnClickListener{
                val saatVorod: String = etSaatVorod.getText().toString()
                val date: String = txDate.getText().toString()
                val elat: String = etElat.getText().toString()

                if (saatVorod.length <= 0 || saatVorod == null) {
                    Toast.makeText(this, "لطفا ساعت ورود را انتخاب نمایید", Toast.LENGTH_SHORT).show()
                } else if(saatVorod.equals(saatVorodGhabli)){
                    Toast.makeText(this, "تغییری ایجاد نشده", Toast.LENGTH_SHORT).show()
                }else {
                    LoadData.sendVorodKhorojEzafekari(this, username, saatVorod, "",date,elat,"saatVorod",clWifiState)
                }
            }

            imgSendSaatKhoroj.setOnClickListener{
                val saatVorod: String = etSaatVorod.getText().toString()
                val saatKhoroj: String = etSaatKhoroj.getText().toString()
                val date: String = txDate.getText().toString()
                val elat: String = etElat.getText().toString()

                if (saatVorod.length <= 0 || saatVorod == null){
                    Toast.makeText(this, "ابتدا ساعت ورود را انتخاب نمایید", Toast.LENGTH_SHORT).show()

                }else if(saatKhoroj.length <= 0 || saatKhoroj == null) {
                    Toast.makeText(this, "لطفا ساعت خروج را انتخاب نمایید", Toast.LENGTH_SHORT).show()

                } else if(saatVorod.equals(saatVorodGhabli) && saatKhoroj.equals(saatKhorojGhabli)){
                    Toast.makeText(this, "تغییری ایجاد نشده", Toast.LENGTH_SHORT).show()
                } else {
                    LoadData.sendVorodKhorojEzafekari(this, username, saatVorod, saatKhoroj,date,elat,"saatKhoroj",clWifiState)
                }
            }


        }else{

            LoadData.loadVorodKhorojGhabli(this,etSaatVorod,etSaatKhoroj,timeKononi.persianTime,username,clWifiState)

            etSaatVorod.setOnClickListener {

                //Toast.makeText(this,myclass.schoolName.toString(),Toast.LENGTH_SHORT).show()

                val mcurrentTime: Calendar = Calendar.getInstance()
                val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                if (etSaatVorod.text.equals("")){
                    etSaatVorod.setText(String.format("%02d:%02d", hour, minute))
                }else{
                    Toast.makeText(this,"ویرایش امکان پذیر نیست",Toast.LENGTH_SHORT).show()
                }


                /* mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                         etSaatVorod.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                     }, hour, minute, true) //Yes 24 hour time
                     mTimePicker.setTitle("انتخاب زمان")
                     mTimePicker.show()*/


            }

            etSaatKhoroj.setOnClickListener{
                val saatVorod: String = etSaatVorod.getText().toString()
                if (saatVorod.length <= 0 || saatVorod == null){
                    Toast.makeText(this, "ابتدا ساعت ورود را انتخاب نمایید", Toast.LENGTH_SHORT).show()
                }else{
                    val mcurrentTime: Calendar = Calendar.getInstance()
                    val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                    val mTimePicker: TimePickerDialog

                    if (etSaatKhoroj.text.equals("")){
                        etSaatKhoroj.setText(String.format("%02d:%02d", hour, minute))
                    }else{
                        Toast.makeText(this,"ویرایش امکان پذیر نیست",Toast.LENGTH_SHORT).show()
                    }


                    /*mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                        //--select * from tavasi message WHERE id = ();

                        etSaatKhoroj.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                        //val s = etSaatKhoroj.text.toString().substring(0, 2)
                        //Toast.makeText(this,s.toString() ,Toast.LENGTH_SHORT).show()

                    }, hour, minute, true) //Yes 24 hour time
                    mTimePicker.setTitle("انتخاب زمان")
                    mTimePicker.show()*/
                }
            }


            txDate.setText(timeKononi.persianTime)

            imgSendSaatVorod.setOnClickListener{
                val saatVorod: String = etSaatVorod.getText().toString()
                val date: String = txDate.getText().toString()

                if (saatVorod.length <= 0 || saatVorod == null) {
                    Toast.makeText(this, "لطفا ساعت ورود را انتخاب نمایید", Toast.LENGTH_SHORT).show()
                } else if(saatVorod.equals(saatVorodGhabli)){
                    Toast.makeText(this, "تغییری ایجاد نشده", Toast.LENGTH_SHORT).show()
                }else {
                    LoadData.sendVorodKhoroj(this, username, saatVorod, "",date,"saatVorod",clWifiState)
                }
            }

            imgSendSaatKhoroj.setOnClickListener{
                val saatVorod: String = etSaatVorod.getText().toString()
                val saatKhoroj: String = etSaatKhoroj.getText().toString()
                val date: String = txDate.getText().toString()

                if (saatVorod.length <= 0 || saatVorod == null){
                    Toast.makeText(this, "ابتدا ساعت ورود را انتخاب نمایید", Toast.LENGTH_SHORT).show()

                }else if(saatKhoroj.length <= 0 || saatKhoroj == null) {
                    Toast.makeText(this, "لطفا ساعت خروج را انتخاب نمایید", Toast.LENGTH_SHORT).show()

                } else if(saatVorod.equals(saatVorodGhabli) && saatKhoroj.equals(saatKhorojGhabli)){
                    Toast.makeText(this, "تغییری ایجاد نشده", Toast.LENGTH_SHORT).show()
                } else {
                    LoadData.sendVorodKhoroj(this, username, saatVorod, saatKhoroj,date,"saatKhoroj",clWifiState)
                }
            }


        }


        imgSabtEzafeKari.setOnClickListener{
            val intent = Intent(this, VorodKhoroj::class.java)
            intent.putExtra("ezafe_kari", "ezafe_kari")
            startActivity(intent)
            finish()
        }


        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("vorod_khoroj", "vorod_khoroj")
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








        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                LoadData.loadCountMessageNotRead(this@VorodKhoroj, txCountNotReadMessage, username)

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

    public class Myclass {
        public var schoolName: String? = null
        public var className: String? = null

        fun Myclass(schoolName: String?, className: String?) {
            this.schoolName = schoolName
            this.className = className
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
