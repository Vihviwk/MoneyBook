package it.uninsubria.moneybook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import it.uninsubria.moneybook.ui.AddTransactionActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val ADD_TRANS_CODE = 1
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
            R.id.buttonAdd -> {
                Log.i(TAG, "buttonAdd")
                val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
                startActivityForResult(intent, ADD_TRANS_CODE)
                //TODO("deprecated? come si fa adesso?")

            }
            R.id.buttonStats -> Log.i(TAG, "buttonStats")
            R.id.buttonSettings -> Log.i(TAG, "buttonSettings")
            else -> {
                print("unhandled event source!")
            }
        }
    }

    //TODO("deprecated? come si fa adesso?")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        }

    }

}