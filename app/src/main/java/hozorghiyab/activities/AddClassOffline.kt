package hozorghiyab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import android.view.View
import android.widget.Toast
import com.hozorghiyab.R
import hozorghiyab.listCityACT.*
import kotlinx.android.synthetic.main.activity_class.*
import java.util.ArrayList

class AddClassOffline : AppCompatActivity() {
    internal var citys = ArrayList<Contacts>()
    private var db: Database? = null
    internal var contacts: Contacts? = null
    internal lateinit var constraintSet: ConstraintSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        val actionBar = supportActionBar
        actionBar!!.hide()

        imgBackInActivityClass.setOnClickListener(){
            finish()
        }

        imgAfzodanClassText.setOnClickListener(){
            imgAddClass.setVisibility(View.VISIBLE)
            etClassName.setVisibility(View.VISIBLE)
            etSchoolName.setVisibility(View.VISIBLE)

            constraintSet = ConstraintSet()
            constraintSet.clone(clActivityClass)

            constraintSet.connect(R.id.rvAddClass, ConstraintSet.TOP, R.id.txSchoolName, ConstraintSet.BOTTOM, 60)
            constraintSet.applyTo(clActivityClass)
        }



        RecyclerviewDefine.define(this, rvAddClass)
        db = Database(this)
        db!!.useable()
        RefreshDb.refresh(db,contacts,citys);
        val adapter = CityAdapter(this, citys, rvAddClass)
        rvAddClass.setAdapter(adapter)

        imgAddClass.setOnClickListener(){

            var className = etClassName.text.toString()
            var schoolName = etSchoolName.text.toString()

            if(className.length <=0 || className.isEmpty() || schoolName.length <=0 || schoolName.isEmpty()){
                Toast.makeText(this,"همه موارد را تکمیل کنید",Toast.LENGTH_SHORT).show()
            }else {
                db!!.open()
                db!!.addClass("tbl_citys", schoolName, className)
                db!!.close()

                db = Database(this)
                db!!.useable()
                RefreshDb.refresh(db, contacts, citys);
                val adapter = CityAdapter(this, citys, rvAddClass)
                rvAddClass.setAdapter(adapter)

                etSchoolName.setText("")
                etClassName.setText("")
                Toast.makeText(this,"ثبت شد",Toast.LENGTH_SHORT).show()

                imgAddClass.setVisibility(View.GONE)
                etClassName.setVisibility(View.GONE)
                etSchoolName.setVisibility(View.GONE)

                constraintSet = ConstraintSet()
                constraintSet.clone(clActivityClass)

                constraintSet.connect(R.id.rvAddClass, ConstraintSet.TOP, R.id.imgAfzodanClassText, ConstraintSet.BOTTOM, 0)
                constraintSet.applyTo(clActivityClass)

            }
        }
    }
}
