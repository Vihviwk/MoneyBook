package it.uninsubria.moneybook.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import it.uninsubria.moneybook.db.Transaction
import kotlinx.android.synthetic.main.transaction_list_activity.*

class TransactionListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_list_activity)

        val db : DataBaseHelper = DataBaseHelper(this)

        var list : MutableList<Transaction> = db.readData()

        listView.adapter = MyAdapter(this, list)
    }


    class MyAdapter(private val context: Context, val data : MutableList<Transaction>) : BaseAdapter() {

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var newView = convertView
            if(convertView==null) {
                newView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)
            }
            if(newView != null) {
                //TODO
                val category = newView.findViewById<TextView>(R.id.categoryRow)
                val description = newView.findViewById<TextView>(R.id.descriptionRow)
                //val date = newView.findViewById<TextView>(R.id.dateRow)
                val amount  = newView.findViewById<TextView>(R.id.amountRow)
                category.text = data[position].category
                description.text = data[position].description
                //date.text = data[position].date
                amount.text = data[position].amount.toString()
            }
            return newView
        }

    }
}