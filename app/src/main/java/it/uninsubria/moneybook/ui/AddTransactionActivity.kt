package it.uninsubria.moneybook.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.Transaction
import it.uninsubria.moneybook.db.DataBaseHelper
import kotlinx.android.synthetic.main.add_transaction_activity.*
import kotlinx.android.synthetic.main.row.*
import java.lang.Float.parseFloat
import java.util.*


private const val TAG = "Add Transaction"

class AddTransactionActivity: AppCompatActivity(), View.OnClickListener, DatePickerFragment.NoticeDialogListener {


    lateinit var category : String
    lateinit var description : String
    lateinit var date : String
    var amount : Float = 0.0f


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

        category = insertCategTextView.text.toString()
        description = insert_description.text.toString()
        amount = parseFloat(insertAmountTextView.text.toString())
        date = insertDateView.text.toString()

        val transaction = Transaction(amount, category, date, description)

        db.insertData(transaction)

        closeActivity()
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun closeActivity() {
        //TODO("not yet implemented")
        //handle invalid input
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        val sMonth = handleDate(month +1)
        val day = handleDate(dayOfMonth)
        val sDate = "$year-$sMonth-$day"
        insertDateView.text = sDate
    }

    private fun handleDate(num : Int) : String {
        var s = num.toString()
        if(num<10) {
            s = "0$s"
        }
        return s
    }

}