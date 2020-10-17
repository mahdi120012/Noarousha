package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import kotlinx.android.synthetic.main.vaziyat_darsi_akhlaghi.*

class VaziyatDarsiAkhlaghi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vaziyat_darsi_akhlaghi)



        imgVaziyatDarsi.setOnClickListener{
            startActivity(Intent(this, DarsListInStudentPanel::class.java))
        }

        imgVaziyatAkhlaghi.setOnClickListener{
            startActivity(Intent(this, SearchForSendMessage::class.java))
        }

        imgBackInDarsList.setOnClickListener{
            finish()
        }
    }
}
