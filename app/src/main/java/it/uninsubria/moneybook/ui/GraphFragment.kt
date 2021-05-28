package it.uninsubria.moneybook.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import it.uninsubria.moneybook.db.Transaction


class GraphFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var db : DataBaseHelper

    lateinit var lineChart : LineChart
    lateinit var dataSet : LineDataSet

    var list : MutableList<Transaction> = ArrayList()
    var entries = ArrayList<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_graph, container, false)

        val spinner = view.findViewById<Spinner>(R.id.spinnyBoy)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.current_period,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        db = DataBaseHelper(requireContext())
        list.addAll(db.readAllData())
        list.reverse()


        lineChart = view.findViewById<LineChart>(R.id.graph)

        // Inflate the layout for this fragment
        return view
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        //make toast
        val item : String = parent!!.getItemAtPosition(position).toString()
        //showing selected spinner item
        Toast.makeText(parent.context, resources.getString(R.string.selected, item),
                                                            Toast.LENGTH_SHORT).show()

        //TODO("other cases, queries")
        when(position) {
            0 -> {
                list.clear()
                list.addAll(db.readWeek())
                list.reverse()

                handleData()
            }
            1 -> {
                list.clear()
                list.addAll(db.readMonth())
                list.reverse()

                handleData()
            }
            2 -> {
                list.clear()
                list.addAll(db.readYear())
                list.reverse()

                handleData()
            }
            3 -> {
                list.clear()
                list.addAll(db.readAllData())
                list.reverse()

                handleData()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun handleData() {
        entries.clear()
        entries.add(Entry(0f, 0f))
        var amount = 0f
        for(i in list.indices) {
            amount += list[i].amount
            entries.add(Entry(i+1f, amount))
        }

        dataSet = LineDataSet(entries, null)
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.red)
        dataSet.lineWidth = 2f
        dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.red))
        dataSet.setDrawValues(false)
//        dataSet.valueTextSize = 12f
//        dataSet.valueTextColor = Color.BLACK

        lineChart.data = LineData(dataSet)

        lineChart.setBackgroundColor(Color.WHITE)
        lineChart.xAxis.granularity= 1f      // minimum axis-step (interval) is 1
        lineChart.xAxis.setDrawLabels(false)
        lineChart.legend.isEnabled = false
        lineChart.axisLeft.textSize = 12f
        lineChart.axisRight.textSize = 12f

        lineChart.data.notifyDataChanged()
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }

}