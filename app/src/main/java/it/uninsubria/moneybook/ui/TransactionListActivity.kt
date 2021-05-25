package it.uninsubria.moneybook.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import it.uninsubria.moneybook.db.Transaction
import kotlinx.android.synthetic.main.transaction_list_activity.*

class TransactionListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var list : MutableList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_list_activity)

        val db : DataBaseHelper = DataBaseHelper(this)

        list  = db.readAllData()
        list.reverse()

        if(list.isEmpty()) {
            Snackbar.make( findViewById(R.id.constraintLayout),
                R.string.no_transactions,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        listView.adapter = MyAdapter(this, list)

        fab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //TODO("Not yet implemented")
        //open filters dialog
//        val db = DataBaseHelper(this)
//        db.deleteAll()
    }

    class MyAdapter(private val context: Context, private val data : MutableList<Transaction>) : BaseAdapter() {

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
                val category = newView.findViewById<TextView>(R.id.categoryRow)
                val description = newView.findViewById<TextView>(R.id.descriptionRow)
                val date = newView.findViewById<TextView>(R.id.dateRow)
                val amount  = newView.findViewById<TextView>(R.id.amountRow)
                category.text = data[position].category
                description.text = data[position].description
                date.text = data[position].date
                amount.text = context.resources.getString(R.string.amount, data[position].amount)
                //amount.text = data[position].amount.toString()

                if (data[position].amount > 0f) {
                    amount.setTextColor(ContextCompat.getColor(context, R.color.green))
                } else {
                    amount.setTextColor(ContextCompat.getColor(context, R.color.red))
                }

            }
            return newView
        }

    }


}