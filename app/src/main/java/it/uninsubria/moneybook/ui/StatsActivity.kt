package it.uninsubria.moneybook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import it.uninsubria.moneybook.R

class StatsActivity : AppCompatActivity(),
    DatePickerFragment.NoticeDialogListener,FiltersFragment.FiltersListener {

    private var startDate = "2015-01-01"
    private var endDate = "2025-01-01"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)



    }

    fun onClickDate(v: View) {
        val newFragment = DatePickerFragment()
        if(v.id == R.id.toTextView) {
            newFragment.flag = 0
        } else if(v.id == R.id.fromTextView) {
            newFragment.flag = 1
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
        TODO("Not yet implemented")
    }

    override fun onResetFilters() {
        TODO("Not yet implemented")
    }
}