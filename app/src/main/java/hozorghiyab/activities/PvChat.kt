package hozorghiyab.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.hozorghiyab.R
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.cityDetail.Recyclerview
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.inbox_message_chat.rvInInboxMessageTeacher
import kotlinx.android.synthetic.main.inbox_message_pv_chat.*
import kotlinx.android.synthetic.main.list_payam_haye_ersali.*
import kotlinx.android.synthetic.main.list_payam_haye_ersali.tabLayout
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*


class PvChat : AppCompatActivity(), View.OnTouchListener {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    val ha = Handler()
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inbox_message_pv_chat)
        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarColor));
        }


        val emojIcon = EmojIconActions(this, clMain, etMatnChat, imgSelectEmoji, "#495C66", "#DCE1E2", "#E6EBEF")
        emojIcon.setIconsIds(R.drawable.keyboad_icon,R.drawable.emoji_select)
        emojIcon.setUseSystemEmoji(true)
        emojIcon.setKeyboardListener(object : EmojIconActions.KeyboardListener {
            override fun onKeyboardOpen() {

                emojIcon.closeEmojIcon()
            }

            override fun onKeyboardClose() {

            }
        })


        etMatnChat.setOnTouchListener(this)

        etMatnChat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length>0){
                    imgSend.visibility = View.VISIBLE
                }else{
                    imgSend.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        imgSelectEmoji.setOnClickListener {
            emojIcon.ShowEmojIcon()
        }


        var mokhatabId = if (intent.getExtras() == null) {}else{intent.extras!!.getString("mokhatab_id")}
        var nameMokhatab = if (intent.getExtras() == null) {}else{intent.extras!!.getString("name_mokhatab")}
        txNameMokhatab.setText(nameMokhatab.toString())

        LoadData.loadLastSeen(this,mokhatabId.toString(),txLastSeen)

        //txMokhaffafName.setText(nameMokhatab.toString().substring(0,1))

        cardViewUnderProfilePicture.setVisibility(View.VISIBLE)
        val androidColors: IntArray = this.getResources().getIntArray(R.array.androidcolors)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        cardViewUnderProfilePicture1.setBackgroundColor(randomAndroidColor)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences("file", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("tedad_payam_khande_nashode", "0")
        editor.commit()


        LoadData.lastId2 = "0"
        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@PvChat, rAdapterYouHaveKnow, "payam_haye_daryafti",null,clMain,null,"")
        Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow,null)

        LoadData.firstLoadDataRecivedMessageChat(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                rvInInboxMessageTeacher, username,mokhatabId.toString(),"all",noe,clWifiState)



            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            //همه
                            clMajmoeKarkard.visibility = View.GONE
                            clMore.visibility = View.GONE
                            LoadData.lastId2 = "0"
                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@PvChat, rAdapterYouHaveKnow, "payam_haye_daryafti",null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow,null)

                            LoadData.firstLoadDataRecivedMessageChat(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    rvInInboxMessageTeacher, username,mokhatabId.toString(),"all",noe,clWifiState)

                        }
                        1 -> {
                            //خصوصی
                            clMajmoeKarkard.visibility = View.GONE
                            clMore.visibility = View.GONE
                            clMain.setVisibility(View.VISIBLE)

                            LoadData.lastId3 = "0"


                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "pv_chat", this@PvChat, rAdapterYouHaveKnow, null,null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow,null)
                            LoadData.loadPvChat(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    rvInInboxMessageTeacher, username,mokhatabId.toString(),clWifiState)

                            //هندرلر زیر برای اینه که اگه چت جدید موجود بود بیاره
                            ha.postDelayed(object : Runnable {
                                override fun run() {

                                    LoadData.loadPvChat(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                            rvInInboxMessageTeacher,username,mokhatabId.toString(),clWifiState)

                                    ha.postDelayed(this, 1000)
                                }
                            }, 1000)

                        }
                        2 -> {
                            //گزارشات
                            clMajmoeKarkard.visibility = View.GONE
                            clMore.visibility = View.GONE
                            LoadData.lastId2 = "0"
                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@PvChat, rAdapterYouHaveKnow, "pv",null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow,null)

                            LoadData.firstLoadDataRecivedMessageChat(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    rvInInboxMessageTeacher, username,mokhatabId.toString(),"gozaresh_kar",noe,clWifiState)

                            /*LoadData.LoadMajmoeKolGozareshat(this@PvChat, username,mokhatabId.toString(),txMajmoeKolSaatKarkard,txTedadKol,
                                    "gozaresh_kar",noe,clWifiState)*/


                        }
                        3 -> {
                            //کارها
                            clMajmoeKarkard.visibility = View.GONE
                            clMore.visibility = View.GONE
                            LoadData.lastId2 = "0"
                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message_sepordan_kar", this@PvChat, rAdapterYouHaveKnow, mokhatabId.toString(),null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow,null)

                            LoadData.firstLoadDataRecivedMessageChatWorks(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    rvInInboxMessageTeacher, username,mokhatabId.toString(),"sepordan_kar",noe,clWifiState)
                        }
                        4 -> {
                            //احکام
                            clMajmoeKarkard.visibility = View.GONE
                            clMore.visibility = View.GONE

                            LoadData.lastId2 = "0"
                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@PvChat, rAdapterYouHaveKnow, "",null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow,null)

                            LoadData.firstLoadDataRecivedMessageChat(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                    rvInInboxMessageTeacher, username,mokhatabId.toString(),"ahkam",noe,clWifiState)
                        }
                        5 -> {
                            //ورود خروج
                            clMajmoeKarkard.visibility = View.VISIBLE
                            clMore.visibility = View.GONE
                            LoadData.lastId2 = "0"
                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "vorod_khoroj", this@PvChat, rAdapterYouHaveKnow, "",null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow, null)


                            LoadData.ListVorodKhorojErsaliDarPv(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                     rvInInboxMessageTeacher, username,mokhatabId.toString(),clWifiState)

                            LoadData.LoadMajmoeKolSaat(this@PvChat, username,mokhatabId.toString(),txMajmoeKolSaatKarkard,txTedadKol,miyanginKarkardRozane,clWifiState)

                        }
                        6 -> {
                            //مرخصی
                            clMajmoeKarkard.visibility = View.GONE
                            clMore.visibility = View.GONE
                            LoadData.lastId2 = "0"
                            rModelsYouHaveKnow = ArrayList()
                            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "darkhast_morkhasi", this@PvChat, rAdapterYouHaveKnow, "",null,clMain,null,"")
                            Recyclerview.define_recyclerviewAddStudent(this@PvChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                                    rModelsYouHaveKnow, null)

                            LoadData.ListDarkhastMorkhasiDarBakhshPv(this@PvChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                     rvInPayamHayeErsaliTeacher, username,mokhatabId.toString(),clWifiState)



                        }
                        else -> {
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        imgMore.setOnClickListener {
            if (clMore.visibility == View.VISIBLE){
                clMore.visibility = View.GONE

            }else if (clMore.visibility == View.GONE){
                clMore.visibility = View.VISIBLE
            }
        }

     /*   Handler().postDelayed(
                {
                    tabLayout.getTabAt(0)?.select();
    }, 100)*/

        imgSend.setOnClickListener {

            val timeKononi = TimeKononi()
            var nowTime = timeKononi.persianTime


            LoadData.sendMessageTeacher(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    username, mokhatabId.toString(), etMatnChat, "",clWifiState,nowTime,"",rvInInboxMessageTeacher)

            /*rModelsYouHaveKnow!!.add(RecyclerModel(LoadData.lastId, etMatnChat.text.toString(), "matn", nowTime, "", "", username, "", 0, ""))
            rAdapterYouHaveKnow!!.notifyDataSetChanged()*/
            rvInInboxMessageTeacher.scrollToPosition(rvInInboxMessageTeacher.getAdapter()!!.getItemCount() - 1)
            //}
        }


        imgBackRecevedMessageTeacher.setOnClickListener{
            LoadData.lastId = "0"
            LoadData.lastId2 = "0"
            LoadData.lastId3 = "0"
            ha.removeCallbacksAndMessages(null);
            val i = Intent(this, InboxMessageChat::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }

    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (view.id == R.id.etMatnChat) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return false
    }
    override fun onBackPressed() {
        ha.removeCallbacksAndMessages(null);
        LoadData.lastId = "0"
        LoadData.lastId2 = "0"
        LoadData.lastId3 = "0"
        val i = Intent(this, InboxMessageChat::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadData.lastId = "0"
        LoadData.lastId2 = "0"
        LoadData.lastId3 = "0"
        ha.removeCallbacksAndMessages(null);
    }


}
