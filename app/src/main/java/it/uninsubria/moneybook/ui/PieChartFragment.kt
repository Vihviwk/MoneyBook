package it.uninsubria.moneybook.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import it.uninsubria.moneybook.db.Transaction
import kotlin.math.abs


class PieChartFragment : Fragment() {

    private lateinit var pieChart : PieChart
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pie_chart, container, false)

        val db = DataBaseHelper(requireContext())

        pieChart = view.findViewById(R.id.pieChart)

        setup(db)

        return view
    }

    private fun setup(db : DataBaseHelper) {

        val taxes = db.readCategories(arrayListOf(0))
        val salary = db.readCategories(arrayListOf(1))
        val rest = db.readCategories(arrayListOf(2))
        val groc = db.readCategories(arrayListOf(3))
        val leisure = db.readCategories(arrayListOf(4))
        val other = db.readCategories(arrayListOf(5))
        
        val tTaxes = totalExpenses(taxes)
        val tSalary = totalExpenses(salary)
        val tRest = totalExpenses(rest)
        val tGroc = totalExpenses(groc)
        val tLeisure = totalExpenses(leisure)
        val tOther = totalExpenses(other)

        val sum = sum(tTaxes, tSalary, tRest, tGroc, tLeisure, tOther)

        val array = arrayListOf(tTaxes, tSalary, tRest, tGroc, tLeisure, tOther)
        val nameArray = arrayListOf("Taxes", "Salary", "Restaurant", "Groceries", "Leisure", "Other")

        val removeArray = ArrayList<Int>()

        for(x in array.indices) {
            if(array[x] < 0.1f ) {
                removeArray.add(x)
            }
        }

        for(x in removeArray.reversed()) {
            array.removeAt(x)
            nameArray.removeAt(x)
        }

        val pieEntries = ArrayList<PieEntry>()

        for(i in array.indices) {
            pieEntries.add(PieEntry(array[i]/sum, nameArray[i]))
        }

        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }

        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        val dataSet = PieDataSet(pieEntries, "Expense Category")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data

        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setDrawEntryLabels(false)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Spending by Category"
        pieChart.setCenterTextSize(12f)
        pieChart.description.isEnabled = false

        //legend attributes
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.textSize = 12f
        l.setDrawInside(false)
        l.isEnabled = true


        pieChart.invalidate()
    }

    private fun sum(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float): Float {
        return a + b + c + d + e + f
    }

    private fun totalExpenses(category: MutableList<Transaction>) : Float{
        var sum = 0f

        for(t in category) {
            if(t.amount < 0f)
                sum += abs(t.amount)
        }
        return sum
    }

}