package com.example.mypoolapp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class ReadingsActivity : AppCompatActivity(){

    private  var vesselType: String = "pool"
    private var ph_reading: Float = 0f
    private var fc_reading: Float = 0f
    private var cc_reading: Float = 0f
    private var alk_reading: Int = 0
    private var ca_reading: Int = 0
    private var cya_reading: Int = 0
    private var phos_reading: Int = 0

    private lateinit var active_body: TableRow

    private fun getIMM(): InputMethodManager{ return getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager }

    private fun enableKeyboard(view: View) {
        val imm = getIMM()
        imm.showSoftInput(view,1)
    }

    private fun disableKeyboard(view: View) {
        val imm = getIMM()
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

    private fun hideView(view: View){ view.visibility = View.GONE }

    private fun showView(view: TableRow, entry: View){
        view.visibility = View.VISIBLE
        active_body = view
        entry.requestFocus()
    }

    private fun toggleActiveBody(body: TableRow, entry: EditText){
        if(body.visibility == View.VISIBLE) {
            hideView(body)
            showView(page_header, page_entry)
            disableKeyboard(entry)
        }
        else{
            if(active_body != page_header){
                hideView(active_body)
            }
            showView(body, entry)
            enableKeyboard(entry)
        }
    }

    private fun getVesselType(): String{ return vesselType }

    private fun setVesselType(ves: String){ vesselType = ves }

    private fun activatePool(){
        println("Activating pool profile!")
        setVesselType("pool")
        reading_page.setBackgroundColor(Color.argb(117,21,96,103))
        pool_button.setBackgroundColor(Color.TRANSPARENT)
        pool_button.setTextColor(Color.WHITE)
    }

    private fun deactivatePool(){
        println("Deactivating pool profile!")
        pool_button.toggle()
        pool_button.setBackgroundColor(Color.GRAY)
        pool_button.setTextColor(Color.BLACK)

    }

    private fun activateSpa(){
        println("Activating spa profile!")
        setVesselType("spa")
        reading_page.setBackgroundColor(Color.argb(117,129,0,0))
        spa_button.setBackgroundColor(Color.TRANSPARENT)
        spa_button.setTextColor(Color.WHITE)
    }

    private fun deactivateSpa(){
        println("Deactivating spa profile!")
        spa_button.toggle()
        spa_button.setBackgroundColor(Color.GRAY)
        spa_button.setTextColor(Color.BLACK)
    }

    private fun profileHandler(profile: String){
        val currentProfile = getVesselType()
        if(profile == "pool") {
            println("Pool button pressed!") //log that the pool button was pressed
            if(currentProfile == "pool"){
                println("Pool button already active!")
                pool_button.toggle() //cancel out the initial button press
            }
            else{
                activatePool()
                deactivateSpa()
            }
        }
        else {
            println("Spa button pressed!") //log that the spa button was pressed
            if (currentProfile == "spa") {
                println("Spa button already active!")
                spa_button.toggle() //cancel out the initial button press
            } else {
                activateSpa()
                deactivatePool()
            }
        }
    }

    private fun entryHandler(input: CharSequence, entry: EditText, range: List<Float>){
        val t = entry.text
        println("input from parameter: $input")
        println("Text currently in box: $t")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        active_body = page_header

        activatePool() //set page with pool profile selected by default
        deactivateSpa() //make sure spa profile is deactivated

        pool_button.setOnClickListener{ profileHandler("pool") }
        spa_button.setOnClickListener{ profileHandler("spa" ) }

        ph_card_click_area.setOnClickListener(){ toggleActiveBody(ph_info_body, ph_entry) }
        ph_info_button.setOnClickListener(){ toggleActiveBody(ph_info_body, ph_entry) }
        ph_info_body.setOnClickListener(){ toggleActiveBody(ph_info_body, ph_entry) }

        chlor_card_click_area.setOnClickListener(){ toggleActiveBody(chlor_info_body, chlor_entry) }
        chlor_info_button.setOnClickListener(){ toggleActiveBody(chlor_info_body, chlor_entry) }
        chlor_info_body.setOnClickListener(){ toggleActiveBody(chlor_info_body, chlor_entry) }

        alk_card_click_area.setOnClickListener(){ toggleActiveBody(alk_info_body, alk_entry) }
        alk_info_button.setOnClickListener(){ toggleActiveBody(alk_info_body, alk_entry) }
        alk_info_body.setOnClickListener(){ toggleActiveBody(alk_info_body, alk_entry) }

        ca_card_click_area.setOnClickListener(){ toggleActiveBody(ca_info_body, ca_entry) }
        ca_info_button.setOnClickListener(){ toggleActiveBody(ca_info_body, ca_entry) }
        ca_info_body.setOnClickListener(){ toggleActiveBody(ca_info_body, ca_entry) }

        cya_card_click_area.setOnClickListener(){ toggleActiveBody(cya_info_body, cya_entry) }
        cya_info_button.setOnClickListener(){ toggleActiveBody(cya_info_body, cya_entry) }
        cya_info_body.setOnClickListener(){ toggleActiveBody(cya_info_body, cya_entry) }

        phos_card_click_area.setOnClickListener(){ toggleActiveBody(phos_info_body, phos_entry) }
        phos_info_button.setOnClickListener(){ toggleActiveBody(phos_info_body, phos_entry) }
        phos_info_body.setOnClickListener { toggleActiveBody(phos_info_body, phos_entry) }

        ph_entry.addTextChangedListener(object: TextWatcher {
            var orig:CharSequence = ""
            override fun afterTextChanged(s: Editable) {
                println(orig)
                println(s)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                orig = s
                println("before $s")
                println("orig: $orig")
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                print("start: $start, before: $before")
                println("after $s")
                val ph_range = listOf(7f,8f)
                entryHandler(s, ph_entry, ph_range)
            }

        })


    }

}

