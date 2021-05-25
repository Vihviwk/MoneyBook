package it.uninsubria.moneybook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import kotlinx.android.synthetic.main.fragment_overview.*


class OverviewFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var  db : DataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val spinner = view.findViewById<Spinner>(R.id.period_spinner)

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
        // Inflate the layout for this fragment
        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        //make toast
        val item : String = parent!!.getItemAtPosition(position).toString()
        //showing selected spinner item
        Toast.makeText(parent.context, "Selected $item", Toast.LENGTH_LONG).show()

        //get amount from db
        val amount = db.totalAmount(item)

        textViewAmount.text = resources.getString(R.string.amount, amount)
        //change color
        if(amount >= 0.0f) {
            textViewAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        } else {
            textViewAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}