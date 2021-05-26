package it.uninsubria.moneybook.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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

class AddTransactionActivity: AppCompatActivity(),
    View.OnClickListener,
    DatePickerFragment.NoticeDialogListener, AdapterView.OnItemSelectedListener {


    lateinit var category : String
    lateinit var description : String
    lateinit var date : String
    var amount : Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        setContentView(R.layout.add_transaction_activity)

        okButton.setOnClickListener(this)

        val cSpinner = findViewById<Spinner>(R.id.categ_spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            cSpinner.adapter = adapter
        }

        cSpinner.onItemSelectedListener = this
    }

    override fun onClick(v: View?) {
        //store the transaction in the db and handle invalid input

        val db = DataBaseHelper(this)
        var valid = true

        if(insertAmountTextView.text.toString() != "") {
            amount = parseFloat(insertAmountTextView.text.toString())
        } else {
            insertAmountTextView.error = "Insert a value"
            valid = false
        }

        if(insertDateView.text.toString() != "") {
            date = insertDateView.text.toString()
        } else {
            insertDateView.error = "Choose a date"
            valid = false
        }

        //description can be empty
        description = insert_description.text.toString()

        if(valid) {
            val transaction = Transaction(amount, category, date, description)

            db.insertData(transaction)

            closeActivity()
        }


    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun closeActivity() {
        //TODO("not yet implemented")
        //handle invalid input
//        val resultIntent = Intent()
//        setResult(Activity.RESULT_OK, resultIntent)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //make toast
        val item : String = parent!!.getItemAtPosition(position).toString()
        //showing selected spinner item
        Toast.makeText(parent.context, "Selected $item", Toast.LENGTH_SHORT).show()
        var s = ""
        when (position) {
            0 -> s = "Taxes"
            1 -> s = "Salary"
            2 -> s = "Restaurant"
            3 -> s = "Groceries"
            4 -> s = "Leisure"
            5 -> s = "Other"
        }

        category = s
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}