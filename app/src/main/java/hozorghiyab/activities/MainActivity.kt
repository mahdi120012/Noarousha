package hozorghiyab.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.customClasses.AppVersionName
import hozorghiyab.customClasses.CustomDialog
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import hozorghiyab.user_info.Main_user_login_activity
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.clAhkam
import kotlinx.android.synthetic.main.main_activity.clJalasat
import kotlinx.android.synthetic.main.main_activity.clMohasebeTashvighi
import kotlinx.android.synthetic.main.main_activity.clSabtmakharej
import kotlinx.android.synthetic.main.main_activity.clVorodKhoroj
import kotlinx.android.synthetic.main.main_activity.imgNavigationViewInToolbarTop
import kotlinx.android.synthetic.main.main_activity.imgTeacherPicture
import kotlinx.android.synthetic.main.main_activity.txTecherNameInMainActivity
import kotlinx.android.synthetic.main.main_activity_karkonan.*
import kotlinx.android.synthetic.main.navigation_main.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var mServiceIntent: Intent? = null
    private var mYourService: YourService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var noe = SharedPrefClass.getUserId(this,"noe")
        var username = SharedPrefClass.getUserId(this,"user")
        var name = SharedPrefClass.getUserId(this,"name")

        val timeKononi = TimeKononi()
        var nowTime = timeKononi.persianTimeWithoutDayName

        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)

        LoadData.sendLastSeen(this,username,nowTime + " " + String.format("%02d:%02d", hour, minute))

        if(noe == "admin"){
            setContentView(com.hozorghiyab.R.layout.navigation_main)
            imgInboxMessage.visibility = View.VISIBLE
            imgWriteMessage.visibility = View.VISIBLE
            txCountNotReadMessage.visibility = View.VISIBLE


            clEjadName.setOnClickListener {
                startActivity(Intent(this, TextOnImageJava::class.java))
            }

            clMohasebeTashvighi.setOnClickListener(){
                startActivity(Intent(this, MohasebeTashvighi::class.java))
            }

            clAhkam.setOnClickListener(){

                val intent = Intent(this, SearchForSendMessage::class.java)
                intent.putExtra("ahkam", "ahkam")
                startActivity(intent)
            }

            clSepordanKar.setOnClickListener(){

                val intent = Intent(this, SearchForSendMessage::class.java)
                intent.putExtra("sepordan_kar", "sepordan_kar")
                startActivity(intent)
            }

            clVorodKhoroj.setOnClickListener(){

                val intent = Intent(this, VorodKhoroj::class.java)
                startActivity(intent)
            }
            clHameyePayamHaClickInMainActivity.setOnClickListener(){
                startActivity(Intent(this, ListHameyePayamHaBarayeAdmin::class.java))
            }

            clClassClickInMainActivity.setOnClickListener(){
                startActivity(Intent(this, AddClass::class.java))
            }

            clStudentClickInMainActivity.setOnClickListener(){
                startActivity(Intent(this, AddStudent::class.java))
            }

            clJalasat.setOnClickListener(){
                startActivity(Intent(this, TanzimJalase::class.java))
            }





            clHozorGhiyabClickInMainActivity.setOnClickListener(){
                startActivity(Intent(this, HozorGhiyabMain::class.java))

            }

        }else{
            setContentView(com.hozorghiyab.R.layout.navigation_main_karkonan)
            clGozareshKar.setVisibility(View.VISIBLE);


            clAhkam.setOnClickListener(){

                val intent = Intent(this, InboxMessage::class.java)
                intent.putExtra("ahkam", "ahkam")
                startActivity(intent)
            }

            clVorodKhoroj.setOnClickListener(){

                val intent = Intent(this, VorodKhoroj::class.java)
                startActivity(intent)
            }

            clMorkhasi.setOnClickListener(){
                startActivity(Intent(this, DarkhastMorkhasi::class.java))
            }

            clMohasebeTashvighi.setOnClickListener(){
                startActivity(Intent(this, MohasebeTashvighi::class.java))
            }

            clGozareshKar.setOnClickListener(){
                startActivity(Intent(this, GozareshKar::class.java))
            }
        }



        clSabtmakharej.setOnClickListener(){
            startActivity(Intent(this, Makharej::class.java))
        }


        clJalasat.setOnClickListener{
            startActivity(Intent(this, TanzimJalase::class.java))

        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarColor2));
        }

        nav_footer_txVesionCodeInMainPageTeacher.setText("نسخه " + AppVersionName.getVersionName(this))

        val appUpdater = AppUpdater(this).setUpdateFrom(UpdateFrom.JSON).
                setUpdateJSON("http://robika.ir/ultitled/practice/tavasi_update_checker.json").
                setTitleOnUpdateAvailable("بروزرسانی جدید موجوده!").setButtonUpdate("بروزرسانی").
                setButtonDismiss("فعلا نه").setButtonDoNotShowAgain("")
        appUpdater.start()

       /* mYourService = YourService()
        mServiceIntent = Intent(this, mYourService!!.javaClass)
        if (!isMyServiceRunning(mYourService!!.javaClass)) {
            startService(mServiceIntent)
        }*/




        if(name.isEmpty()){

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    LoadData.loadTeacherNameAndCountMessageNotRead(this@MainActivity,
                            txTecherNameInMainActivity, txCountNotReadMessage, username,imgTeacherPicture,clWifiState)

                    ha.postDelayed(this, 2000)
                }
            }, 2000)
        }else{
            txTecherNameInMainActivity.setText(name)
        }


       /* if (noe == "teacher"){
            clMorkhasi.setVisibility(View.GONE);
            clVorodKhoroj.setVisibility(View.GONE);
            clAhkam.setVisibility(View.GONE);
            clSepordanKar.setVisibility(View.GONE);
            clGozareshKar.setVisibility(View.GONE);
            imgLine8.setVisibility(View.GONE);
            imgLine10.setVisibility(View.GONE);
            imgLine9.setVisibility(View.GONE);
            imgLine1.setVisibility(View.GONE);
            imgLine2.setVisibility(View.GONE);
            imgLine4.setVisibility(View.GONE);
            imgLine5.setVisibility(View.GONE);
            imgLine6.setVisibility(View.GONE);

        }*/


        imgInboxMessage.setOnClickListener(){
            startActivity(Intent(this, InboxMessage::class.java))
        }

        imgWriteMessage.setOnClickListener(){
            startActivity(Intent(this, ListPayamHayeErsali::class.java))
        }

        txMenuAboutme.setOnClickListener(){
            Toast.makeText(this,"درباره ما",Toast.LENGTH_SHORT).show()
        }

        txChangePassword.setOnClickListener{
            CustomDialog.changePassword(this@MainActivity,this@MainActivity)

        }
        imgMassenger.setOnClickListener{
            startActivity(Intent(this, InboxMessageChat::class.java))
        }

        txExit.setOnClickListener(){
            SharedPrefClass.clearData(this)
            Toast.makeText(this,"شما خارج شدید",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Main_user_login_activity::class.java))
            finish()
        }

        imgNavigationViewInToolbarTop.setOnClickListener(){
            if (drawer_layout.isDrawerOpen(Gravity.RIGHT)) {
                drawer_layout.closeDrawer(Gravity.RIGHT)
            } else {
                drawer_layout.openDrawer(Gravity.RIGHT)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.RIGHT)) {
            drawer_layout.closeDrawer(Gravity.RIGHT)
        } else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test"
            val descriptionText = "test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }


/*    override fun onDestroy() {
        stopService(mServiceIntent)
        super.onDestroy()
    }*/

/*    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(YourService.MY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
        //Start our own service
        val intent = Intent(this@MainActivity,
                YourService::class.java)
        startService(intent)
        super.onStart()
    }*/

/*    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val datapassed = intent.getIntExtra("DATAPASSED", 0)
            val s = intent.action.toString()
            val s1 = intent.getStringExtra("DATAPASSED")

            if(s1.toString().equals("")){

            }else{
                //Toast.makeText(context,s1.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }*/


}
