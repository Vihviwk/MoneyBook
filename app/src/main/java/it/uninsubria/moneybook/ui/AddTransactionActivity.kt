package it.uninsubria.moneybook.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.Transaction
import it.uninsubria.moneybook.db.DataBaseHelper
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
        val db = DataBaseHelper(this)

        val tempTransaction : Transaction = Transaction(10f,"rest", "yyyy-MM-dd", "desc")

        db.insertData(tempTransaction)

        closeActivity()
    }

    private fun closeActivity() {
        //TODO("not yet implemented")
        //handle invalid input
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }



}