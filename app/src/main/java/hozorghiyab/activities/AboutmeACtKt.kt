package hozorghiyab.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import kotlinx.android.synthetic.main.aboutme_act.*

class AboutmeACtKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutme_act)
                //git Test
        backActionBarAboutMe.setOnClickListener { finish() }
        val file_path = "file:///android_asset/about_me.html"
        webView_AboutMe.loadUrl(file_path)

    }
}