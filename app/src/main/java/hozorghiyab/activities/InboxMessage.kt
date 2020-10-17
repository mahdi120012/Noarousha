package hozorghiyab.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.cityDetail.Recyclerview
import hozorghiyab.customClasses.SharedPrefClass
import kotlinx.android.synthetic.main.inbox_message.*
import kotlinx.android.synthetic.main.inbox_message.tabLayout
import kotlinx.android.synthetic.main.list_payam_haye_ersali.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*


class InboxMessage : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inbox_message)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")
        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}

        if (ahkam!!.equals("ahkam")){
            tabLayout.setScrollPosition(4,0f,true);
        }

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("file", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("tedad_payam_khande_nashode", "0")
        editor.commit()

       /* if(!noe.equals("admin")){
            tabItemVorodKhoroj.visibility = View.GONE
        }*/

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        //همه

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@InboxMessage, rAdapterYouHaveKnow, "payam_haye_daryafti",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow,null)

                        LoadData.firstLoadDataRecivedMessageTeacher(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInInboxMessageTeacher, username,"all",clWifiState)
                    }
                    1 -> {
                        //خصوصی

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@InboxMessage, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow,null)

                        LoadData.firstLoadDataRecivedMessageTeacher(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInInboxMessageTeacher, username,"",clWifiState)
                    }
                    2 -> {
                        //گزارشات

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@InboxMessage, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow,null)

                        LoadData.firstLoadDataRecivedMessageTeacher(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInInboxMessageTeacher, username,"gozaresh_kar",clWifiState)
                    }
                    3 -> {
                        //کارها
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@InboxMessage, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow,null)

                        LoadData.firstLoadDataRecivedMessageTeacher(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInInboxMessageTeacher, username,"sepordan_kar",clWifiState)
                    }
                    4 -> {
                    //احکام
                    rModelsYouHaveKnow = ArrayList()
                    rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@InboxMessage, rAdapterYouHaveKnow, "",null,null,null,"")
                    Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                            rModelsYouHaveKnow,null)

                    LoadData.firstLoadDataRecivedMessageTeacher(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInInboxMessageTeacher, username,"ahkam",clWifiState)
                    }
                    5 -> {
                    //ورود خروج
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "vorod_khoroj", this@InboxMessage, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.ListVorodKhorojErsali(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInInboxMessageTeacher, username,clWifiState,noe)

                    }
                    6 -> {
                        //مرخصی
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "darkhast_morkhasi", this@InboxMessage, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@InboxMessage, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)
                        LoadData.ListDarkhastMorkhasi(this@InboxMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,clWifiState,noe)

                    }
                    else -> {
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this, rAdapterYouHaveKnow, "",null,null,null,"")
        Recyclerview.define_recyclerviewAddStudent(this, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow,null)

        if (noe.equals("student")){
            LoadData.firstLoadDataRecivedMessageStudent(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInInboxMessageTeacher, username, clWifiState)

        }else{

            LoadData.firstLoadDataRecivedMessageTeacher(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInInboxMessageTeacher, username,"all",clWifiState)
        }

        imgWriteMessage.setOnClickListener{
            startActivity(Intent(this, ListPayamHayeErsali::class.java))
            finish()
        }

        imgHome.setOnClickListener{
            finish()
        }

        imgBackRecevedMessageTeacher.setOnClickListener{
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
}
