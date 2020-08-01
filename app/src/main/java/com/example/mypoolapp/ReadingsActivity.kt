package com.example.mypoolapp

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class ReadingsActivity : AppCompatActivity(){

    data class Element(val name: String,val card: View, val card_body: View, val card_entry: EditText, val full_range: List<Float>, val ideal_range: List<Float>, val level_icon: ImageView) {}

    private lateinit var vesselType:String
    private lateinit var activeElement:Element
    private lateinit var defaultElement:Element

    private lateinit var phAnim: AnimatedVectorDrawable
    private lateinit var chlorAnim: AnimatedVectorDrawable
    private lateinit var alkAnim: AnimatedVectorDrawable
    private lateinit var caAnim: AnimatedVectorDrawable
    private lateinit var cyaAnim: AnimatedVectorDrawable
    private lateinit var phosAnim: AnimatedVectorDrawable

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
        reading_page.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.poolBackground))
        pool_button.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.transparent))
        pool_button.setTextColor(ContextCompat.getColor(applicationContext,R.color.darkText))
    }

    private fun deactivatePool(){
        println("Deactivating pool profile!")
        pool_button.toggle()
        pool_button.setBackgroundColor(Color.GRAY)
        pool_button.setTextColor(Color.WHITE)

    }

    private fun activateSpa(){
        println("Activating spa profile!")
        setVesselType("spa")
        reading_page.setBackgroundColor(Color.argb(117,129,0,0))
        reading_page.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.poolBackground))
        spa_button.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.transparent))
        spa_button.setTextColor(ContextCompat.getColor(applicationContext,R.color.darkText))
    }

    private fun deactivateSpa(){
        println("Deactivating spa profile!")
        spa_button.toggle()
        spa_button.setBackgroundColor(Color.GRAY)
        spa_button.setTextColor(Color.WHITE)
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
                disableKeyboard(defaultElement.card_entry)
                cya_card.visibility = View.VISIBLE
                phos_card.visibility = View.VISIBLE
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
                disableKeyboard(defaultElement.card_entry)
                cya_card.visibility = View.GONE
                phos_card.visibility = View.GONE
                activateSpa()
                deactivatePool()
            }
        }
    }

    private fun hideElement(element: Element){ element.card_body.visibility = View.GONE }

    private fun showElement(element: Element){
        element.card_body.visibility = View.VISIBLE
        activeElement = element
        element.card_entry.requestFocus()
    }

    private fun handleEntry(element: Element) {
        val holder:String = element.card_entry.text.toString()
        if(holder == "."){
            element.card_entry.setText("")
        }
        else if(holder != ""){
            var entryValue:Float = 0f
            if("≤" in holder){ entryValue = element.full_range[0] }
            else if("≥" in holder){ entryValue = element.full_range[1] }
            else{ entryValue = holder.toFloat() }
            if(entryValue >= element.full_range[1]){
                element.card_entry.setText("≥${element.full_range[1].toInt()}")
            }
            else if(entryValue <= element.full_range[0]){
                element.card_entry.setText("≤${element.full_range[0].toInt()}")
            }
        }
    }

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
                handleEntry(activeElement)
            }
            activeElement = element
            showElement(element) //activate the selected card body
            enableKeyboard(element.card_entry) //bring up the keyboard so user can type shit
        }
        if(activeElement == defaultElement){println("DEFAULT CARD IS ACTIVE; NO CARD SELECTED")} //sanity check
    }


//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun setAnimations(){
//        check.apply{
//            setBackgroundResource(R.drawable.avd_anim)
//            phAnim = background as AnimatedVectorDrawable
//        }
//
//        check2.apply{
//            setBackgroundResource(R.drawable.avd_anim)
//            chlorAnim = background as AnimatedVectorDrawable
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        check.setOnClickListener({ phAnim.start() })
//        check2.setOnClickListener({ chlorAnim.start() })



        val phElement:Element = Element("ph", ph_card, ph_info_body, ph_entry, listOf(7f,8f), listOf(7.4f,7.6f), ph_level_icon)
        val chlorElement:Element = Element("chlor", chlor_card, chlor_info_body, chlor_entry, listOf(0f,10f), listOf(7.4f,7.6f), chlor_level_icon)
        val alkElement:Element = Element("alk", alk_card, alk_info_body, alk_entry, listOf(0f,999f), listOf(80f,120f), alk_level_icon)
        val caElement:Element = Element("ca", ca_card, ca_info_body, ca_entry, listOf(0f,999f), listOf(200f,400f), ca_level_icon)
        val cyaElement:Element = Element("cya", cya_card, cya_info_body, cya_entry, listOf(0f,400f), listOf(30f,80f), cya_level_icon)
        val phosElement:Element = Element("phos", phos_card, phos_info_body, phos_entry, listOf(0f,999f), listOf(0f,0f), phos_level_icon)

        defaultElement = Element("default", page_header, page_body, page_entry, listOf(0f,0f), listOf(0f,0f), page_level_icon)
        activeElement = defaultElement

        activatePool() //set page with pool profile selected by default
        deactivateSpa() //make sure spa profile is deactivated

        pool_button.setOnClickListener{ profileHandler("pool") }
        spa_button.setOnClickListener{ profileHandler("spa" ) }

        ph_card.setOnClickListener(){toggleActiveBody(phElement)}
        ph_entry.setOnFocusChangeListener{ _, _ -> handleEntry(phElement) }

        chlor_card.setOnClickListener(){ toggleActiveBody(chlorElement) }
        chlor_entry.setOnFocusChangeListener{ _, _ -> handleEntry(chlorElement) }

        alk_card.setOnClickListener(){ toggleActiveBody(alkElement) }
        alk_entry.setOnFocusChangeListener{ _, _ -> handleEntry(alkElement) }

        ca_card.setOnClickListener(){ toggleActiveBody(caElement) }
        ca_entry.setOnFocusChangeListener{ _, _ -> handleEntry(caElement) }

        cya_card.setOnClickListener(){ toggleActiveBody(cyaElement) }
        cya_entry.setOnFocusChangeListener{ _, _ -> handleEntry(cyaElement) }

        phos_card.setOnClickListener(){ toggleActiveBody(phosElement) }
        phos_entry.setOnFocusChangeListener{ _, _ -> handleEntry(phosElement) }
    }
}