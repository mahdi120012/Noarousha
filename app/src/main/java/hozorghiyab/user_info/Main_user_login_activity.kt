package hozorghiyab.user_info

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.hozorghiyab.R
import hozorghiyab.activities.MainActivity
import hozorghiyab.activities.StudentPanelMainKt
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.cityDetail.UrlEncoderClass
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.navigation_userlogin.*

class Main_user_login_activity : AppCompatActivity() {

    var drawerLayout: DrawerLayout? = null

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_userlogin)

        var pInfo: PackageInfo? = null
        try {
            pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val app_version_name = pInfo!!.versionName
        //String app_version_code = String.valueOf(pInfo.versionCode);
        nav_footer_txVersionNameInUserLogin.setText("نسخه $app_version_name")

        //line zir baraye update automatic ee.
        val appUpdater = AppUpdater(this).setUpdateFrom(UpdateFrom.JSON).setUpdateJSON("http://robika.ir/ultitled/practice/tavasi_update_checker.json").setTitleOnUpdateAvailable("بروزرسانی جدید موجوده!").setButtonUpdate("بروزرسانی").setButtonDismiss("فعلا نه").setButtonDoNotShowAgain("")
        appUpdater.start()


        txMenuAboutmeInMainUserLogin.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "درباره ما", Toast.LENGTH_SHORT).show()
        })

        txHowToUseInMainUserLogin.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "راهنما", Toast.LENGTH_SHORT).show()
        })


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#44bba0")
            // KITKAT+
        } else {
            //below KITKAT
        }

        imgMohsenin.setOnClickListener {
            if (imgLoginStudentText.getVisibility() == View.GONE) {
                imgLoginStudentText.visibility = View.VISIBLE
                imgLoginTeacherText.visibility = View.VISIBLE
                etUsernameStudent.visibility = View.GONE
                etPasswordStudent.visibility = View.GONE
                imgLoginStudent.visibility = View.GONE
                etUsernameTeacher.visibility = View.GONE
                etPasswordTeacher.visibility = View.GONE
                imgLoginTeacher.visibility = View.GONE
            }else{
                imgLoginStudentText.visibility = View.GONE
                imgLoginTeacherText.visibility = View.GONE
                etUsernameStudent.visibility = View.GONE
                etPasswordStudent.visibility = View.GONE
                imgLoginStudent.visibility = View.GONE
                etUsernameTeacher.visibility = View.GONE
                etPasswordTeacher.visibility = View.GONE
                imgLoginTeacher.visibility = View.GONE
            }

        }
        imgSetad.setOnClickListener {
            if (imgLoginKarmandText.getVisibility() == View.GONE) {
                imgLoginStudentText.visibility = View.GONE
                imgLoginTeacherText.visibility = View.GONE
                imgLoginKarmandText.visibility = View.VISIBLE
            }else{
                imgLoginStudentText.visibility = View.GONE
                imgLoginTeacherText.visibility = View.GONE
                imgLoginKarmandText.visibility = View.GONE
                etUsernameTeacher.visibility = View.GONE
                etPasswordTeacher.visibility = View.GONE
                imgLoginTeacher.visibility = View.GONE
                etUsernameKarkonan.visibility = View.GONE
                etPasswordKarkonan.visibility = View.GONE
                imgLoginKarkonan.visibility = View.GONE
            }

        }

        imgLoginStudentText.setOnClickListener(View.OnClickListener {
            if (etUsernameStudent.getVisibility() == View.GONE) {
                etUsernameTeacher.setVisibility(View.GONE)
                etPasswordTeacher.setVisibility(View.GONE)
                imgLoginTeacher.setVisibility(View.GONE)
                etUsernameKarkonan.setVisibility(View.GONE)
                etPasswordKarkonan.setVisibility(View.GONE)
                imgLoginKarkonan.setVisibility(View.GONE)
                etUsernameStudent.setVisibility(View.VISIBLE)
                etPasswordStudent.setVisibility(View.VISIBLE)
                imgLoginStudent.setVisibility(View.VISIBLE)
                txForgetPasswordStudent.setVisibility(View.VISIBLE)
            } else {
                etUsernameTeacher.setVisibility(View.GONE)
                etPasswordTeacher.setVisibility(View.GONE)
                imgLoginTeacher.setVisibility(View.GONE)
                etUsernameKarkonan.setVisibility(View.GONE)
                etPasswordKarkonan.setVisibility(View.GONE)
                imgLoginKarkonan.setVisibility(View.GONE)
                etUsernameStudent.setVisibility(View.GONE)
                etPasswordStudent.setVisibility(View.GONE)
                imgLoginStudent.setVisibility(View.GONE)
                txForgetPasswordStudent.setVisibility(View.GONE)
            }
        })

        imgLoginTeacherText.setOnClickListener(View.OnClickListener {
            if (etUsernameTeacher.getVisibility() == View.GONE) {
                etUsernameStudent.setVisibility(View.GONE)
                etPasswordStudent.setVisibility(View.GONE)
                imgLoginStudent.setVisibility(View.GONE)
                txForgetPasswordStudent.setVisibility(View.GONE)
                etUsernameKarkonan.setVisibility(View.GONE)
                etPasswordKarkonan.setVisibility(View.GONE)
                imgLoginKarkonan.setVisibility(View.GONE)
                etUsernameTeacher.setVisibility(View.VISIBLE)
                etPasswordTeacher.setVisibility(View.VISIBLE)
                imgLoginTeacher.setVisibility(View.VISIBLE)
            } else {
                etUsernameStudent.setVisibility(View.GONE)
                etPasswordStudent.setVisibility(View.GONE)
                imgLoginStudent.setVisibility(View.GONE)
                txForgetPasswordStudent.setVisibility(View.GONE)
                etUsernameKarkonan.setVisibility(View.GONE)
                etPasswordKarkonan.setVisibility(View.GONE)
                imgLoginKarkonan.setVisibility(View.GONE)
                etUsernameTeacher.setVisibility(View.GONE)
                etPasswordTeacher.setVisibility(View.GONE)
                imgLoginTeacher.setVisibility(View.GONE)
            }
        })


        imgLoginKarmandText.setOnClickListener(View.OnClickListener {
            if (etUsernameKarkonan.getVisibility() == View.GONE) {
                etUsernameStudent.setVisibility(View.GONE)
                etPasswordStudent.setVisibility(View.GONE)
                imgLoginStudent.setVisibility(View.GONE)
                txForgetPasswordStudent.setVisibility(View.GONE)
                etUsernameTeacher.setVisibility(View.GONE)
                etPasswordTeacher.setVisibility(View.GONE)
                imgLoginTeacher.setVisibility(View.GONE)
                etUsernameKarkonan.setVisibility(View.VISIBLE)
                etPasswordKarkonan.setVisibility(View.VISIBLE)
                imgLoginKarkonan.setVisibility(View.VISIBLE)
            } else {
                etUsernameStudent.setVisibility(View.GONE)
                etPasswordStudent.setVisibility(View.GONE)
                imgLoginStudent.setVisibility(View.GONE)
                txForgetPasswordStudent.setVisibility(View.GONE)
                etUsernameTeacher.setVisibility(View.GONE)
                etPasswordTeacher.setVisibility(View.GONE)
                imgLoginTeacher.setVisibility(View.GONE)
                etUsernameKarkonan.setVisibility(View.GONE)
                etPasswordKarkonan.setVisibility(View.GONE)
                imgLoginKarkonan.setVisibility(View.GONE)
            }
        })


        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        if (username!!.length <= 0) {
            //tx_user_state.setText("ابتدا در برنامه وارد شوید");
        } else {
            if (noe.equals("student")) {
                val intent = Intent(this, StudentPanelMainKt::class.java)
                startActivity(intent)
                this.finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            //tx_user_state.setText(username + " خوش آمدید ");
        }


        imgLoginStudent.setOnClickListener(View.OnClickListener {
            val usernameOrEmail = etUsernameStudent.getText().toString()
            val password: String = etPasswordStudent.getText().toString()
            if (usernameOrEmail.length <= 0 || usernameOrEmail == null || password.length <= 0 || password == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {

                var userNameEncode = UrlEncoderClass.urlEncoder(usernameOrEmail)
                var passwordEncode = UrlEncoderClass.urlEncoder(password)

                val url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=login_student&std_id=$userNameEncode&pass1=$passwordEncode"
                Login_helper(this, url, etUsernameStudent, etPasswordStudent).execute()
            }
        })

        imgLoginTeacher.setOnClickListener(View.OnClickListener {
            val usernameOrEmail: String = etUsernameTeacher.getText().toString()
            val password: String = etPasswordTeacher.getText().toString()
            if (usernameOrEmail.length <= 0 || usernameOrEmail == null || password.length <= 0 || password == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {

                var userNameEncode = UrlEncoderClass.urlEncoder(usernameOrEmail)
                var passwordEncode = UrlEncoderClass.urlEncoder(password)

                val url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=login_teacher&teacher_id=$userNameEncode&pass1=$passwordEncode"
                Login_helper(this, url, etUsernameTeacher, etPasswordTeacher).execute()
            }
        })

        imgLoginKarkonan.setOnClickListener(View.OnClickListener {
            val usernameOrEmail = etUsernameKarkonan.getText().toString()
            val password: String = etPasswordKarkonan.getText().toString()
            if (usernameOrEmail.length <= 0 || usernameOrEmail == null || password.length <= 0 || password == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {

                var userNameEncode = UrlEncoderClass.urlEncoder(usernameOrEmail)
                var passwordEncode = UrlEncoderClass.urlEncoder(password)

                val url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=login_karkonan&karkon_id=$userNameEncode&pass1=$passwordEncode"
                Login_helper(this, url, etUsernameKarkonan, etPasswordKarkonan).execute()
            }
        })


        imgNavigationInLoginPage.setOnClickListener(View.OnClickListener {
            if (drawer_layoutInLoginPage.isDrawerOpen(Gravity.RIGHT)) {
                drawer_layoutInLoginPage.closeDrawer(Gravity.RIGHT)
            } else {
                drawer_layoutInLoginPage.openDrawer(Gravity.RIGHT)
            }
        })

    }

    fun userReg(view: View?) {
        val intent = Intent(this, Main_user_register_activity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout!!.closeDrawer(Gravity.RIGHT)
        } else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}