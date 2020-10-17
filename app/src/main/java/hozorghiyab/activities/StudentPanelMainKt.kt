package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import hozorghiyab.cityDetail.AppVersionName
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.cityDetail.UrlEncoderClass
import hozorghiyab.user_info.Main_user_login_activity
import kotlinx.android.synthetic.main.navigation_studentpanel.*
import kotlinx.android.synthetic.main.student_panel_main.*
import kotlinx.android.synthetic.main.toolbar_button.*

class StudentPanelMainKt : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.navigation_studentpanel)

        nav_footer_txVersionNameInStudentPanel.setText("نسخه " + AppVersionName.getVersionName(this))

        //line zir baraye update automatic ee.
        val appUpdater = AppUpdater(this).
                setUpdateFrom(UpdateFrom.JSON).
                setUpdateJSON("http://robika.ir/ultitled/practice/tavasi_update_checker.json").
                setTitleOnUpdateAvailable("بروزرسانی جدید موجوده!").
                setButtonUpdate("بروزرسانی").setButtonDismiss("فعلا نه").
                setButtonDoNotShowAgain("")
        appUpdater.start()

        val username = SharedPrefClass.getUserId(this,"user")

        //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
        val urlAppend = "?action=load_student_name" +
                "&user1=" + UrlEncoderClass.urlEncoder(username)

        LoadData.loadStudentNameAndCountMessageNotRead(this, urlAppend,
                tx_stateInStudentPanel, txStudentNameInStudentPanel,
                txCountNotReadMessage, imgUserPicture, clWifiInStudentPanel)


        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                //call function
                //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                val urlAppend = "?action=load_student_name" +
                        "&user1=" + UrlEncoderClass.urlEncoder(username)

                var tedadKhandeNashode =LoadData.loadStudentNameAndCountMessageNotRead(this@StudentPanelMainKt, urlAppend,
                        tx_stateInStudentPanel, txStudentNameInStudentPanel,
                        txCountNotReadMessage, imgUserPicture, clWifiInStudentPanel)

                /*if (tedadKhandeNashode != "0"){
                    CreateNotification().createNotificationChannel(this@StudentPanelMainKt)
                    CreateNotification().showNotification(this@StudentPanelMainKt,StudentPanelMainKt::class.java,"شما پیام خوانده نشده دارید",tedadKhandeNashode,tedadKhandeNashode)
                }*/

                ha.postDelayed(this, 2000)
            }
        }, 2000)



        clVaziyatDarsiInStudentPanel.setOnClickListener(){
            startActivity(Intent(this, VaziyatDarsiAkhlaghi::class.java))
        }

        //Below Live baraye Click roye view hast:
        txMenuAboutmeInStudentPanel.setOnClickListener {
            Toast.makeText(this, "درباره ما", Toast.LENGTH_SHORT).show()
        }

        imgWriteMessage.setOnClickListener {
            startActivity(Intent(this, ListPayamHayeErsali::class.java))
        }

        imgInboxMessage.setOnClickListener {
            startActivity(Intent(this, InboxMessage::class.java))
        }

        clHozorGhiyabInStudentPanel.setOnClickListener {
            startActivity(Intent(this, HozorGhiyabInStudentPanel::class.java))
        }

        imgNavigationViewInStudentPanel.setOnClickListener {
            if (drawer_layoutInStudent.isDrawerOpen(Gravity.RIGHT)) {
                drawer_layoutInStudent.closeDrawer(Gravity.RIGHT)
            } else {
                drawer_layoutInStudent.openDrawer(Gravity.RIGHT)
            }
        }

        imgMassenger.setOnClickListener{

            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }

        txExitInStudentPanel.setOnClickListener {
            SharedPrefClass.clearData(this)
            startActivity(Intent(this, Main_user_login_activity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if (drawer_layoutInStudent.isDrawerOpen(Gravity.RIGHT)) {
            drawer_layoutInStudent.closeDrawer(Gravity.RIGHT)
        } else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


}
