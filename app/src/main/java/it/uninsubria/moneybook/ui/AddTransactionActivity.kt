package it.uninsubria.moneybook.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import it.uninsubria.moneybook.R
import kotlinx.android.synthetic.main.add_transaction_activity.*


private const val TAG = "Add Transaction"

class AddTransactionActivity: AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        setContentView(R.layout.add_transaction_activity)

        okButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //TODO("Not yet implemented")
        //store the transaction in the db or handle invalid input
    }



}