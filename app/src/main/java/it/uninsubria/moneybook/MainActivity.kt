package it.uninsubria.moneybook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import it.uninsubria.moneybook.db.DataBaseHelper
import it.uninsubria.moneybook.ui.AddTransactionActivity
import it.uninsubria.moneybook.ui.SettingsActivity
import it.uninsubria.moneybook.ui.StatsActivity
import it.uninsubria.moneybook.ui.TransactionListActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //private val ADD_TRANS_CODE = 1
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
            R.id.buttonAll -> {
                Log.i(TAG, "buttonAll")
                val intent = Intent(this@MainActivity, TransactionListActivity::class.java)
                startActivity(intent)
            }
            R.id.buttonAdd -> {
                Log.i(TAG, "buttonAdd")
                val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
                startActivity(intent)

            }
            R.id.buttonStats -> {
                Log.i(TAG, "buttonStats")
                val intent = Intent(this@MainActivity, StatsActivity::class.java)
                startActivity(intent)
            }
            R.id.buttonSettings -> {
                Log.i(TAG, "buttonSettings")
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            else -> {
                print("unhandled event source!")
            }
        }
    }

}