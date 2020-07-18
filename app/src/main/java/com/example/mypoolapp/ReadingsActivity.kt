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

//    val defaultElement:Element = Element("default", page_header, page_entry, listOf(0f,0f), listOf(0f,0f))



    private lateinit var defaultElement:Element
    private lateinit var activeElement:Element

    private  var vesselType: String = "pool"


    private fun getIMM(): InputMethodManager{ return getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager }

    private fun enableKeyboard(view: View) {
        val imm = getIMM()
        imm.showSoftInput(view,1)
    }

    private fun disableKeyboard(view: View) {
        val imm = getIMM()
        imm.hideSoftInputFromWindow(view.windowToken,0)
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

//    private fun entryHandler(input: CharSequence, entry: EditText, range: List<Float>){
//        val t = entry.text
//        println("input from parameter: $input")
//        println("Text currently in box: $t")
//    }

    private fun hideElement(element: Element){ element.card_body.visibility = View.GONE }

    private fun showElement(element: Element){
        element.card_body.visibility = View.VISIBLE
        activeElement = element
        element.card_entry.requestFocus()
    }



// When to check if a user has entered a number
    // 1) When card gets activated -----> Check the previous active element
        // If the previous active element is NOT our default value, check if the previous element's entry has changed

    // 2) When card get deactivated

    // 3) When "next" OR "done" button is pressed for the text entries







    // Function ensures that there is only a single card/profile active on the page at one time
    private fun toggleActiveBody(element: Element){
        if(element.card_body.visibility == View.VISIBLE) { // if active card body was selected, we need to reset the page
            hideElement(element) // deactivate the active card
            disableKeyboard(element.card_body) // disable the keyboard since no cards are active
            activeElement = defaultElement// reset the active body to the default value
        }
        else{ // if the selected card is NOT visible, we need to switch active card bodies
            if(activeElement != defaultElement){ //check if the active body is our default preset; if it's not, we need to hide the current active body
                hideElement(activeElement) //close the active card body so no card is open/selected
            }
            showElement(element) //activate the selected card body
            enableKeyboard(element.card_entry) //bring up the keyboard so user can type shit
        }
        if(activeElement == defaultElement){println("DEFAULT CARD IS ACTIVE; NO CARD SELECTED")} //sanity check
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phElement:Element = Element("ph", ph_info_body, ph_entry, listOf(7f,8f), listOf(7.4f,7.6f))
        val chlorElement:Element = Element("chlor", chlor_info_body, chlor_entry, listOf(0f,10f), listOf(7.4f,7.6f))
        val alkElement:Element = Element("alk", alk_info_body, alk_entry, listOf(0f,1000f), listOf(80f,120f))
        val caElement:Element = Element("ca", ca_info_body, ca_entry, listOf(0f,1000f), listOf(200f,400f))
        val cyaElement:Element = Element("cya", cya_info_body, cya_entry, listOf(0f,100f), listOf(30f,80f))
        val phosElement:Element = Element("phos", phos_info_body, phos_entry, listOf(0f,1000f), listOf(0f,0f))

        defaultElement = Element("default", page_header, page_entry, listOf(0f,0f), listOf(0f,0f))
        activeElement = defaultElement

        activatePool() //set page with pool profile selected by default
        deactivateSpa() //make sure spa profile is deactivated

        pool_button.setOnClickListener{ profileHandler("pool") }
        spa_button.setOnClickListener{ profileHandler("spa" ) }

        ph_card_click_area.setOnClickListener(){ toggleActiveBody(phElement) }
        ph_info_button.setOnClickListener(){ toggleActiveBody(phElement) }
        ph_info_body.setOnClickListener(){ toggleActiveBody(phElement) }
        ph_entry.setOnClickListener(){toggleActiveBody(phElement)}




        chlor_card_click_area.setOnClickListener(){ toggleActiveBody(chlorElement) }
        chlor_info_button.setOnClickListener(){ toggleActiveBody(chlorElement) }
        chlor_info_body.setOnClickListener(){ toggleActiveBody(chlorElement) }

        alk_card_click_area.setOnClickListener(){ toggleActiveBody(alkElement) }
        alk_info_button.setOnClickListener(){ toggleActiveBody(alkElement) }
        alk_info_body.setOnClickListener(){ toggleActiveBody(alkElement) }

        ca_card_click_area.setOnClickListener(){ toggleActiveBody(caElement) }
        ca_info_button.setOnClickListener(){ toggleActiveBody(caElement) }
        ca_info_body.setOnClickListener(){ toggleActiveBody(caElement) }

        cya_card_click_area.setOnClickListener(){ toggleActiveBody(cyaElement) }
        cya_info_button.setOnClickListener(){ toggleActiveBody(cyaElement) }
        cya_info_body.setOnClickListener(){ toggleActiveBody(cyaElement) }

        phos_card_click_area.setOnClickListener(){ toggleActiveBody(phosElement) }
        phos_info_button.setOnClickListener(){ toggleActiveBody(phosElement) }
        phos_info_body.setOnClickListener { toggleActiveBody(phosElement) }

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

