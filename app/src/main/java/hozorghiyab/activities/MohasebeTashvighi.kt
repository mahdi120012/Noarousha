package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import hozorghiyab.customClasses.EnglishNumberToPersian
import kotlinx.android.synthetic.main.mohasebe_tashvighi.*
import kotlinx.android.synthetic.main.toolbar_button.*
import java.text.DecimalFormat
import java.util.*


class MohasebeTashvighi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mohasebe_tashvighi)
        Locale.setDefault(Locale("en", "US"))

        btnMohasebe.setOnClickListener {

            if (etMizanHoghogh.text.toString() == ""
                    || etTedadMah.text.toString() == ""
                    || etMizanHoghoghKarBeSaat.text.toString() == ""
                    || etSaatKarkardRozaneh.text.toString() == ""
                    || etTedadRozHayeMah.text.toString() == ""){

                Toast.makeText(this,"لطفا همه موارد را تکمیل کنید",Toast.LENGTH_SHORT).show()

            }else{


            var mizanHoghogh = EnglishNumberToPersian().persianToEnglish(etMizanHoghogh.text.toString().replace(",","")).toDouble()
            var tedadMah = EnglishNumberToPersian().persianToEnglish(etTedadMah.text.toString().replace(",","")).toDouble()
            var mizanHoghoghGhanonKar = EnglishNumberToPersian().persianToEnglish(etMizanHoghoghKarBeSaat.text.toString().replace(",","")).toDouble()
            var saatKarkardRozaneh = EnglishNumberToPersian().persianToEnglish(etSaatKarkardRozaneh.text.toString().replace(",","")).toInt()
            var tedadRozHayeMah = EnglishNumberToPersian().persianToEnglish(etTedadRozHayeMah.text.toString()).toInt()



            var d = (saatKarkardRozaneh*tedadRozHayeMah)
            var hoghoghSaati = mizanHoghoghGhanonKar/d


            var a = (mizanHoghogh*tedadMah)/hoghoghSaati/saatKarkardRozaneh/30
            var b= (30/10)*(String.format("%.1f",a).takeLast(1).toDouble())

            tcCalculate.setText(EnglishNumberToPersian().convert(String.format("%.2f",a).dropLast(3) + " ماه و " + String.format("%.0f",b) + " روز "))
            }
        }


        imgHome.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MohasebeTashvighi, MainActivity::class.java))
            finish()
        })

        imgBack.setOnClickListener {
            startActivity(Intent(this@MohasebeTashvighi, MainActivity::class.java))
            finish()
        }

        imgMassenger.setOnClickListener{
            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }


        etMizanHoghogh.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                etMizanHoghogh.removeTextChangedListener(this)
                try {
                    var givenstring = s.toString()
                    val longval: Long
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replace(",".toRegex(), "")
                    }
                    longval = givenstring.toLong()

                    val formatter = DecimalFormat("#,###,###")
                    val formattedString: String = formatter.format(longval)

                    etMizanHoghogh.setText(EnglishNumberToPersian().convert(formattedString))
                    etMizanHoghogh.setSelection(etMizanHoghogh.getText().length)
                    // to place the cursor at the end of text
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                etMizanHoghogh.addTextChangedListener(this)
            }
        })

        etTedadMah.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                etTedadMah.removeTextChangedListener(this)
                try {
                    var givenstring = s.toString()
                    val longval: Long
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replace(",".toRegex(), "")
                    }
                    longval = givenstring.toLong()
                    val formatter = DecimalFormat("#,###,###")
                    val formattedString: String = formatter.format(longval)
                    etTedadMah.setText(EnglishNumberToPersian().convert(formattedString))
                    etTedadMah.setSelection(etTedadMah.getText().length)
                    // to place the cursor at the end of text
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                etTedadMah.addTextChangedListener(this)
            }
        })


        etMizanHoghoghKarBeSaat.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                etMizanHoghoghKarBeSaat.removeTextChangedListener(this)
                try {
                    var givenstring = s.toString()
                    val longval: Long
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replace(",".toRegex(), "")
                    }
                    longval = givenstring.toLong()
                    val formatter = DecimalFormat("#,###,###")
                    val formattedString: String = formatter.format(longval)
                    etMizanHoghoghKarBeSaat.setText(EnglishNumberToPersian().convert(formattedString))
                    etMizanHoghoghKarBeSaat.setSelection(etMizanHoghoghKarBeSaat.getText().length)
                    // to place the cursor at the end of text
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                etMizanHoghoghKarBeSaat.addTextChangedListener(this)
            }
        })

        etSaatKarkardRozaneh.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                etSaatKarkardRozaneh.removeTextChangedListener(this)
                try {
                    var givenstring = s.toString()
                    val longval: Long
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replace(",".toRegex(), "")
                    }
                    longval = givenstring.toLong()
                    val formatter = DecimalFormat("#,###,###")
                    val formattedString: String = formatter.format(longval)
                    etSaatKarkardRozaneh.setText(EnglishNumberToPersian().convert(formattedString))
                    etSaatKarkardRozaneh.setSelection(etSaatKarkardRozaneh.getText().length)
                    // to place the cursor at the end of text
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                etSaatKarkardRozaneh.addTextChangedListener(this)
            }
        })


        etTedadRozHayeMah.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                etTedadRozHayeMah.removeTextChangedListener(this)
                try {
                    var givenstring = s.toString()
                    val longval: Long
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replace(",".toRegex(), "")
                    }
                    longval = givenstring.toLong()
                    val formatter = DecimalFormat("#,###,###")
                    val formattedString: String = formatter.format(longval)
                    etTedadRozHayeMah.setText(EnglishNumberToPersian().convert(formattedString))
                    etTedadRozHayeMah.setSelection(etTedadRozHayeMah.getText().length)
                    // to place the cursor at the end of text
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                etTedadRozHayeMah.addTextChangedListener(this)
            }
        })



    }

}