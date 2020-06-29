package com.example.mypoolapp

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


private  var vesselType: String = "pool"

class ReadingsActivity : AppCompatActivity(){

    fun getVesselType(): String{ return vesselType }

    fun setVesselType(ves: String){ vesselType = ves }

    fun activatePool(){
        println("Activating pool profile!")
        setVesselType("pool")
        reading_page.setBackgroundColor(Color.argb(117,21,96,103))
        pool_button.setBackgroundColor(Color.TRANSPARENT)
        pool_button.setTextColor(Color.WHITE)
    }

    fun deactivatePool(){
        println("Deactivating pool profile!")
        pool_button.toggle()
        pool_button.setBackgroundColor(Color.GRAY)
        pool_button.setTextColor(Color.BLACK)

    }

    fun activateSpa(){
        println("Activating spa profile!")
        setVesselType("spa")
        reading_page.setBackgroundColor(Color.argb(117,129,0,0))
        spa_button.setBackgroundColor(Color.TRANSPARENT)
        spa_button.setTextColor(Color.WHITE)
    }

    fun deactivateSpa(){
        println("Deactivating spa profile!")
        spa_button.toggle()
        spa_button.setBackgroundColor(Color.GRAY)
        spa_button.setTextColor(Color.BLACK)
    }

    fun phEntryHandler(input: CharSequence){
        println(input)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activatePool() //set page with pool profile selected by default
        deactivateSpa() //make sure spa profile is deactivated
        pool_button.setOnClickListener{
            println("Pool button pressed!")
            if(getVesselType() == "pool"){
                println("Pool button already active!")
                pool_button.toggle() //cancel out the initial button press
            }
            else{
                activatePool()
                deactivateSpa()
            }
            println(getVesselType())
        }
        spa_button.setOnClickListener{
            println("Spa button pressed!")
            if(getVesselType() == "spa"){
                println("Spa button already active!")
                spa_button.toggle() //cancel out the initial button press
            }
            else{
                activateSpa()
                deactivatePool()
            }
            println(getVesselType())
        }

        ph_entry.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                phEntryHandler(s)
            }
        })
    }

}

