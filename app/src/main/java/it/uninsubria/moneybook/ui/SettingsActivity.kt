package it.uninsubria.moneybook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //TODO("popup dialog to confirm")
        val db = DataBaseHelper(this)
        db.deleteAll()
    }

}