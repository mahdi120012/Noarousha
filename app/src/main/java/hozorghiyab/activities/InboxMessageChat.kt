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
import kotlinx.android.synthetic.main.inbox_message_chat.*
import kotlinx.android.synthetic.main.inbox_message_chat.tabLayout

import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*


class InboxMessageChat : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inbox_message_chat)


        tabLayout.visibility = View.GONE

        var username = SharedPrefClass.getUserId(this,"user")

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("file", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("tedad_payam_khande_nashode", "0")
        editor.commit()

        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message_chat", this@InboxMessageChat, rAdapterYouHaveKnow, null,null,null,null,"")
        Recyclerview.define_recyclerviewAddStudent(this@InboxMessageChat, rvInInboxMessageTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow,null)

        LoadData.loadMainChat(this@InboxMessageChat, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                rvInInboxMessageTeacher, username,clWifiState)



        imgInboxMessage.setOnClickListener{
            startActivity(Intent(this, InboxMessage::class.java))
            finish()
        }

        imgWriteMessage.setOnClickListener{
            startActivity(Intent(this, ListPayamHayeErsali::class.java))
            finish()
        }

        imgHome.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        imgSearch.setOnClickListener{
            startActivity(Intent(this, RecentSearch::class.java))
        }

    }
    override fun onBackPressed() {
        finish()
    }
}
