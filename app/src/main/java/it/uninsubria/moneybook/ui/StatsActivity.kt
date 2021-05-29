package it.uninsubria.moneybook.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import it.uninsubria.moneybook.db.Transaction
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerFragment.NoticeDialogListener,FiltersFragment.FiltersListener {

    private var startDate = "1970-01-01"
    private var endDate = "2100-01-01"

    private var pieChartVisible = false

    private var filtersSelected : ArrayList<Int> = ArrayList()

    private val db = DataBaseHelper(this)

    private lateinit var transactions : MutableList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        fromTextView.text = startDate
        toTextView.text = endDate

        //initial data, all
        transactions = db.readAllData()

        //set listener
        applyButton.setOnClickListener(this)

        incomeTextView.text = computeIncome(transactions).toString()
        expensesTextView.text = computeExpenses(transactions).toString()

    }

    fun onClickDate(v: View) {
        val newFragment = DatePickerFragment()
        if(v.id == R.id.toTextView) {
            newFragment.flag = 1
        } else if(v.id == R.id.fromTextView) {
            newFragment.flag = 0
        }
        newFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int, flag: Int) {
        val sMonth = handleDate(month +1)
        val day = handleDate(dayOfMonth)
        val sDate = "$year-$sMonth-$day"
        if(flag == 0) {
            startDate = sDate
        } else if(flag == 1) {
            endDate = sDate
        }
        fromTextView.text = startDate
        toTextView.text = endDate
    }

    private fun handleDate(num : Int) : String {
        var s = num.toString()
        if(num<10) {
            s = "0$s"
        }
        return s
    }

    fun onClickFilters(v: View) {
        val newFragment = FiltersFragment()
        newFragment.show(supportFragmentManager, "statsFilters")
    }

    override fun onFiltersSelection(selected: ArrayList<Int>) {
        filtersSelected = selected
    }

    override fun onResetFilters() {
        filtersSelected.clear()

        //get data from db
        transactions.clear()
        transactions.addAll(db.read(startDate, endDate))

        //set textview value
        incomeTextView.text = computeIncome(transactions).toString()
        expensesTextView.text = computeExpenses(transactions).toString()
    }

    //apply button
    override fun onClick(v: View?) {
        //get data from db
        transactions.clear()
        transactions.addAll(db.read(startDate, endDate, filtersSelected))

        //set textview value
        incomeTextView.text = computeIncome(transactions).toString()
        expensesTextView.text = computeExpenses(transactions).toString()
    }

    fun chartOnClick(v: View) {
        if(pieChartVisible) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<GraphFragment>(R.id.graphFragment)
            }
            pieChartVisible = false
            //button text
            pieChartButton.text = getString(R.string.show_pie)
        } else {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<PieChartFragment>(R.id.graphFragment)
            }
            pieChartVisible = true
            //button text
            pieChartButton.text = getString(R.string.show_line)
        }
    }

    private fun computeIncome(transactions : MutableList<Transaction>) : Float {
        var sum = 0f
        for(t in transactions) {
            if(t.amount >= 0)
                sum += t.amount
        }
        return sum
    }
    private fun computeExpenses(transactions: MutableList<Transaction>) : Float {
        var sum = 0f
        for(t in transactions) {
            if(t.amount < 0)
                sum -= t.amount
        }
        return sum
    }
}