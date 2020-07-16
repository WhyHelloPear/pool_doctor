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
import kotlin.reflect.typeOf


class ReadingsActivity : AppCompatActivity(){

    data class Element(val name: String, val card_body: TableRow, val card_entry: EditText, val full_range: List<Float>, val ideal_range: List<Float>) {}

    private  var vesselType: String = "pool"
    private var ph_reading: Float = 0f
    private var fc_reading: Float = 0f
    private var cc_reading: Float = 0f
    private var alk_reading: Int = 0
    private var ca_reading: Int = 0
    private var cya_reading: Int = 0
    private var phos_reading: Int = 0

    val ph_range = listOf(7f,8f)


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

    private fun showView(element: Element){
        element.card_body.visibility = View.VISIBLE
        active_body = element.card_body
        element.card_entry.requestFocus()
    }

    private fun toggleActiveBody(element: Element){
        if(element.card_body.visibility == View.VISIBLE) {
            hideView(element.card_body)
            showView(element)
            disableKeyboard(element.card_body)
        }
        else{
            if(active_body != page_header){
                hideView(active_body)

            }
            showView(element)
            enableKeyboard(element.card_entry)
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

        val ph_element:Element = Element("ph", ph_info_body, ph_entry, listOf(7f,8f), listOf(7.4f,7.6f))
        val chlor_element:Element = Element("chlor", chlor_info_body, chlor_entry, listOf(0f,10f), listOf(7.4f,7.6f))
        val alk_element:Element = Element("alk", alk_info_body, alk_entry, listOf(0f,1000f), listOf(80f,120f))
        val ca_element:Element = Element("ca", ca_info_body, ca_entry, listOf(0f,1000f), listOf(200f,400f))
        val cya_element:Element = Element("cya", cya_info_body, cya_entry, listOf(0f,100f), listOf(30f,80f))
        val phos_element:Element = Element("phos", phos_info_body, phos_entry, listOf(0f,1000f), listOf(0f,0f))



        active_body = page_header

        activatePool() //set page with pool profile selected by default
        deactivateSpa() //make sure spa profile is deactivated

        pool_button.setOnClickListener{ profileHandler("pool") }
        spa_button.setOnClickListener{ profileHandler("spa" ) }

        ph_card_click_area.setOnClickListener(){ toggleActiveBody(ph_element) }
        ph_info_button.setOnClickListener(){ toggleActiveBody(ph_element) }
        ph_info_body.setOnClickListener(){ toggleActiveBody(ph_element) }

        chlor_card_click_area.setOnClickListener(){ toggleActiveBody(chlor_element) }
        chlor_info_button.setOnClickListener(){ toggleActiveBody(chlor_element) }
        chlor_info_body.setOnClickListener(){ toggleActiveBody(chlor_element) }

        alk_card_click_area.setOnClickListener(){ toggleActiveBody(alk_element) }
        alk_info_button.setOnClickListener(){ toggleActiveBody(alk_element) }
        alk_info_body.setOnClickListener(){ toggleActiveBody(alk_element) }

        ca_card_click_area.setOnClickListener(){ toggleActiveBody(ca_element) }
        ca_info_button.setOnClickListener(){ toggleActiveBody(ca_element) }
        ca_info_body.setOnClickListener(){ toggleActiveBody(ca_element) }

        cya_card_click_area.setOnClickListener(){ toggleActiveBody(cya_element) }
        cya_info_button.setOnClickListener(){ toggleActiveBody(cya_element) }
        cya_info_body.setOnClickListener(){ toggleActiveBody(cya_element) }

        phos_card_click_area.setOnClickListener(){ toggleActiveBody(phos_element) }
        phos_info_button.setOnClickListener(){ toggleActiveBody(phos_element) }
        phos_info_body.setOnClickListener { toggleActiveBody(phos_element) }

//        ph_entry.addTextChangedListener(object: TextWatcher {
//            var orig:CharSequence = ""
//            override fun afterTextChanged(s: Editable) {
//                println(orig)
//                println(s)
//            }
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                orig = s
//                println("before $s")
//                println("orig: $orig")
//            }
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                print("start: $start, before: $before")
//                println("after $s"
//                entryHandler(s, ph_entry, ph_range)
//            }
//
//        })


    }

}

