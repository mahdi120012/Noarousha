package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import android.widget.Toast
import hozorghiyab.cityDetail.*
import hozorghiyab.customClasses.SharedPrefClass
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.search_for_send_message.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*

class SearchForSendMessage : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.search_for_send_message)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}

        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search", this@SearchForSendMessage, rAdapterYouHaveKnow, ahkam.toString(),txReciversList,clShowErsal,txEntekhabHame,sepordanKar.toString())
        Recyclerview.define_recyclerviewAddStudent(this@SearchForSendMessage, rvInSearchInTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow, null)

        if (sepordanKar.toString().equals("sepordan_kar")){
            LoadData.LoadSearchResultForSepordanKar(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInSearchInTeacher, username, "",clWifiState,noe,sepordanKar.toString())
        }else{
            LoadData.LoadSearchResult(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInSearchInTeacher, username, "",clWifiState,noe,"")
        }



        searchViewForSendMessageTeacher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { //Toast.makeText(SearchForSendMessageTeacher.this, query, Toast.LENGTH_SHORT).show();
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rModelsYouHaveKnow = ArrayList()
                rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search", this@SearchForSendMessage, rAdapterYouHaveKnow, ahkam.toString(),txReciversList,clShowErsal,txEntekhabHame,sepordanKar.toString())
                Recyclerview.define_recyclerviewAddStudent(this@SearchForSendMessage, rvInSearchInTeacher, rAdapterYouHaveKnow,
                        rModelsYouHaveKnow, null)

                if (sepordanKar.toString().equals("sepordan_kar")){
                    LoadData.LoadSearchResult(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, newText,clWifiState,noe,sepordanKar.toString())
                }else{
                    LoadData.LoadSearchResult(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, newText,clWifiState,noe,"")
                }
                return false
            }
        })

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.

                    LoadData.loadCountMessageNotRead(this@SearchForSendMessage, txCountNotReadMessage, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)


        imgWriteMessage.setOnClickListener{
             startActivity(Intent(this, ListPayamHayeErsali::class.java))
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

        imgBackInSearchInTeacher.setOnClickListener{
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
