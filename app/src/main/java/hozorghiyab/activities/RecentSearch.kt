package hozorghiyab.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.cityDetail.Recyclerview
import hozorghiyab.customClasses.SharedPrefClass
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.recent_search.*
import kotlinx.android.synthetic.main.search_for_send_message.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*


class RecentSearch : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.recent_search)

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarColor));
        }

            searchView.openSearch()

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("file", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("tedad_payam_khande_nashode", "0")
        editor.commit()

        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_recent", this, rAdapterYouHaveKnow, null,null,null,null,"")
        Recyclerview.defineRecyclerviewSearchRecent(this, rvSearch, rAdapterYouHaveKnow,
                rModelsYouHaveKnow,null)

        LoadData.loadMainChat(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                rvSearch, username,clWifiState)

        searchView.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if (searchView.text.equals("")){
                    imgZarbDar.visibility = View.INVISIBLE
                    clAfrad.visibility = View.VISIBLE
                    rModelsYouHaveKnow = ArrayList()
                    rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_recent", this@RecentSearch, rAdapterYouHaveKnow, null,null,null,null,"")
                    Recyclerview.defineRecyclerviewSearchRecent(this@RecentSearch, rvSearch, rAdapterYouHaveKnow,
                            rModelsYouHaveKnow,null)

                    LoadData.loadMainChat(this@RecentSearch, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvSearch, username,clWifiState)

                }else{
                    imgZarbDar.visibility = View.VISIBLE
                    clAfrad.visibility = View.GONE
                    rModelsYouHaveKnow = ArrayList()
                    rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_chat", this@RecentSearch, rAdapterYouHaveKnow, "",txReciversList,null,null,"")
                    Recyclerview.define_recyclerviewAddStudent(this@RecentSearch, rvSearch, rAdapterYouHaveKnow,
                            rModelsYouHaveKnow, null)

                    LoadData.LoadSearchResult(this@RecentSearch, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, charSequence.toString(),clWifiState,noe,"")

                    //Toast.makeText(this@RecentSearch,charSequence.toString(),Toast.LENGTH_SHORT).show()

                }
            }

            override fun afterTextChanged(editable: Editable) {



            }
        })





        /*var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}
*/
       /* rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search", this@RecentSearch, rAdapterYouHaveKnow, ahkam.toString(),txReciversList,clShowErsal,txEntekhabHame,sepordanKar.toString())
        Recyclerview.define_recyclerviewAddStudent(this@RecentSearch, rvInSearchInTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow, null)

        if (sepordanKar.toString().equals("sepordan_kar")){
            LoadData.LoadSearchResult(this@RecentSearch, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInSearchInTeacher, username, "",clWifiState,noe,sepordanKar.toString())
        }else{
            LoadData.LoadSearchResult(this@RecentSearch, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInSearchInTeacher, username, "",clWifiState,noe,"")
        }



        searchViewForSendMessageTeacher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { //Toast.makeText(SearchForSendMessageTeacher.this, query, Toast.LENGTH_SHORT).show();
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rModelsYouHaveKnow = ArrayList()
                rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search", this@RecentSearch, rAdapterYouHaveKnow, ahkam.toString(),txReciversList,clShowErsal,txEntekhabHame,sepordanKar.toString())
                Recyclerview.define_recyclerviewAddStudent(this@RecentSearch, rvInSearchInTeacher, rAdapterYouHaveKnow,
                        rModelsYouHaveKnow, null)

                if (sepordanKar.toString().equals("sepordan_kar")){
                    LoadData.LoadSearchResult(this@RecentSearch, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, newText,clWifiState,noe,sepordanKar.toString())
                }else{
                    LoadData.LoadSearchResult(this@RecentSearch, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, newText,clWifiState,noe,"")
                }
                return false
            }
        })

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.

                    LoadData.loadCountMessageNotRead(this@RecentSearch, txCountNotReadMessageInSearchInTeacher, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)
*/
        imgZarbDar.setOnClickListener {
            searchView.text = ""
        }
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

        imgMassenger.setOnClickListener{

            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }

        imgBack.setOnClickListener{
            finish()
        }



    }


    override fun onBackPressed() {
        finish()
    }
}
