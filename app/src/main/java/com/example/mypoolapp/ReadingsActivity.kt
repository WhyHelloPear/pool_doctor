package com.example.mypoolapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

private var vesselType = "pool"

class ReadingsActivity : AppCompatActivity(){

    fun getVesselType(): String{ return vesselType }

    fun activatePoolPage(){
        println("Activating pool profile!")
        vesselType = "pool"
        spa_button.toggle()
        reading_page.setBackgroundColor(Color.CYAN)
        pool_button.setBackgroundColor(Color.CYAN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reading_page.setBackgroundColor(Color.CYAN)

        pool_button.setOnClickListener{
            d("pool_button", "Pool button pressed!")
            if(vesselType == "pool"){
                d("pool_button", "Pool button already active!")
                pool_button.toggle() //cancel out the initial button press
            }
            else{
                activatePoolPage()
                spa_button.setBackgroundColor(Color.GRAY)
                d("spa_button", "Deactivating spa button!")
            }
            d("pool_button", vesselType)
        }

        spa_button.setOnClickListener{
            d("spa_button", "Spa button pressed!")
            if(vesselType == "spa"){
                d("spa_button", "Spa button already active!")
                spa_button.toggle() //cancel out the initial button press
            }
            else{
                d("spa_button", "Activating spa button!")
                vesselType = "spa"
                pool_button.toggle()
                d("pool_button", "Deactivating pool button!")
                pool_button.setBackgroundColor(Color.GRAY)
                reading_page.setBackgroundColor(Color.RED)
                spa_button.setBackgroundColor(Color.RED)
            }
            d("spa_button", vesselType)
        }

    }

}

