package it.uninsubria.moneybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

private val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set listener to buttons
        buttonAll.setOnClickListener(this)
        buttonAdd.setOnClickListener(this)
        buttonStats.setOnClickListener(this)
        buttonSettings.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAll -> Log.i(TAG, "buttonAll")
            R.id.buttonAdd -> Log.i(TAG, "buttonAdd")
            R.id.buttonStats -> Log.i(TAG, "buttonStats")
            R.id.buttonSettings -> Log.i(TAG, "buttonSettings")
            else -> {
                print("unhandled event source!")
            }
        }

    }


}