package it.uninsubria.moneybook.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import it.uninsubria.moneybook.R
import it.uninsubria.moneybook.db.DataBaseHelper
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val newFragment = ConfirmDialog()
        newFragment.show(supportFragmentManager, "confirm")
    }

    class ConfirmDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle(getString(R.string.confirm_delete))
                    .setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, id ->
                            val db = DataBaseHelper(requireContext())
                            db.deleteAll()
                            Toast.makeText(requireContext(), getString(R.string.data_deleted), Toast.LENGTH_SHORT).show()
                        })
                    .setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

}