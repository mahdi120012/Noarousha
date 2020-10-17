package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.cityDetail.Recyclerview
import hozorghiyab.customClasses.SharedPrefClass
import kotlinx.android.synthetic.main.gozaresh_kar.imgErsalGozaresh
import kotlinx.android.synthetic.main.inbox_message.imgTooTitlelbarMainAct
import kotlinx.android.synthetic.main.inbox_message.tabLayout
import kotlinx.android.synthetic.main.inbox_message.txTitle
import kotlinx.android.synthetic.main.list_payam_haye_ersali.*
import kotlinx.android.synthetic.main.list_payam_haye_ersali.imgListGozareshat
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import kotlinx.android.synthetic.main.vorod_khoroj.*

class ListPayamHayeErsali : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.list_payam_haye_ersali)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        var gozaresh_kar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("gozaresh_kar")}
        var vorod_khoroj = if (intent.getExtras() == null) {}else{intent.extras!!.getString("vorod_khoroj")}
        var darkhast_morkhasi = if (intent.getExtras() == null) {}else{intent.extras!!.getString("darkhast_morkhasi")}
        var darkhast_jalase = if (intent.getExtras() == null) {}else{intent.extras!!.getString("darkhast_jalase")}
        var sabt_makharej = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sabt_makharej")}

        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "payam_haye_ersali",null,null,null,"")
        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow, null)

        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                rvInPayamHayeErsaliTeacher, username,"all",clWifiState)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        //همه

                         rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "payam_haye_ersali",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"all",clWifiState)
                    }
                    1 -> {
                        //خصوصی

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "payam_haye_ersali",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"",clWifiState)
                    }
                    2 -> {
                        //گزارشات

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "payam_haye_ersali",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"gozaresh_kar",clWifiState)
                    }
                    3 -> {
                        //کارها
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "payam_haye_ersali",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"sepordan_kar",clWifiState)
                    }
                    4 -> {
                        //احکام
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "payam_haye_ersali",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"ahkam",clWifiState)
                    }
                    else -> {
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        if (sabt_makharej.toString()!!.equals("sabt_makharej")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            tabLayout.visibility = View.GONE
            txTitle.setText("ثبت مخارج")
            textView20.setText("ثبت مخارج")
            textView21.setText("لیست مخارج")
        }

        if (darkhast_morkhasi.toString()!!.equals("darkhast_morkhasi")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            tabLayout.visibility = View.GONE
            txTitle.setText("درخواست مرخصی")
            textView20.setText("ثبت مرخصی")
            textView21.setText("لیست مرخصی ها")
        }

        if (darkhast_jalase.toString()!!.equals("darkhast_jalase")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            tabLayout.visibility = View.GONE
            txTitle.setText("درخواست جلسه")
            textView20.setText("ثبت جلسه")
            textView21.setText("لیست جلسات")
        }


        if (vorod_khoroj.toString()!!.equals("vorod_khoroj")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            tabLayout.visibility = View.GONE
            txTitle.setText("لیست ورود خروج")
            textView20.setText("ثبت ورود خروج")
            textView21.setText("لیست ورود خروج")
        }

        if (gozaresh_kar.toString()!!.equals("gozaresh_kar")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            tabLayout.visibility = View.GONE
            txTitle.setText("لیست گزارشات")
        }

        if(noe.equals("student")){
            LoadData.firstLoadDataListPayamHayeErsaliStudent(this, rAdapterYouHaveKnow, rModelsYouHaveKnow, rvInPayamHayeErsaliTeacher, username, clWifiState)

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    LoadData.loadCountMessageNotRead(this@ListPayamHayeErsali,  txCountNotReadMessage,username)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else if (darkhast_morkhasi.toString()!!.equals("darkhast_morkhasi")){
            rModelsYouHaveKnow = ArrayList()
            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "darkhast_morkhasi", this, rAdapterYouHaveKnow, "",null,null,null,"")
            Recyclerview.define_recyclerviewAddStudent(this, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                    rModelsYouHaveKnow, null)
            LoadData.ListDarkhastMorkhasi(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInPayamHayeErsaliTeacher, username,clWifiState,"")

        }else if (darkhast_jalase.toString()!!.equals("darkhast_jalase")){
            rModelsYouHaveKnow = ArrayList()
            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "darkhast_jalase", this, rAdapterYouHaveKnow, "",null,null,null,"")
            Recyclerview.define_recyclerviewAddStudent(this, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                    rModelsYouHaveKnow, null)
            LoadData.ListDarkhastJalase(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInPayamHayeErsaliTeacher, username,clWifiState,noe)

        }else if (sabt_makharej.toString()!!.equals("sabt_makharej")){
            rModelsYouHaveKnow = ArrayList()
            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "sabt_makharej", this, rAdapterYouHaveKnow, "",null,null,null,"")
            Recyclerview.define_recyclerviewAddStudent(this, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                    rModelsYouHaveKnow, null)
            LoadData.ListMakharej(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInPayamHayeErsaliTeacher, username,clWifiState,noe)

        }else{


            if (vorod_khoroj.toString()!!.equals("vorod_khoroj")){
                rModelsYouHaveKnow = ArrayList()
                rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "vorod_khoroj", this, rAdapterYouHaveKnow, "",null,null,null,"")
                Recyclerview.define_recyclerviewAddStudent(this, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                        rModelsYouHaveKnow, null)
                LoadData.ListVorodKhorojErsali(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                        rvInPayamHayeErsaliTeacher, username,clWifiState,noe)

            }else{

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    LoadData.loadCountMessageNotRead(this@ListPayamHayeErsali,txCountNotReadMessage,username);

                    ha.postDelayed(this, 1000)
                }
            }, 1000)

            }
        }


        imgErsalGozaresh.setOnClickListener {
            if (vorod_khoroj.toString()!!.equals("vorod_khoroj")){
                startActivity(Intent(this, VorodKhoroj::class.java))
                finish()
            }else if(darkhast_morkhasi.toString()!!.equals("darkhast_morkhasi")){
                startActivity(Intent(this, DarkhastMorkhasi::class.java))
                finish()
            }else if(darkhast_jalase.toString()!!.equals("darkhast_jalase")){
                startActivity(Intent(this, TanzimJalase::class.java))
                finish()
            }else if(sabt_makharej.toString()!!.equals("sabt_makharej")){
                startActivity(Intent(this, Makharej::class.java))
                finish()
            }else{
                startActivity(Intent(this, GozareshKar::class.java))
                finish()
            }
        }

        imgSendNewMessageInTeacher.setOnClickListener {
                startActivity(Intent(this, SearchForSendMessage::class.java))
        }

        imgInboxMessage.setOnClickListener{
                startActivity(Intent(this, InboxMessage::class.java))
                finish()
        }

        imgHome.setOnClickListener{
            if(noe.equals("student")){
                val intent = Intent(applicationContext, StudentPanelMainKt::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }else{
                finish()
            }

        }

        imgMassenger.setOnClickListener{

            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }

        imgBackInPayamHayeErsaliTeacher.setOnClickListener{
            finish()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        //finish()
    }

}
