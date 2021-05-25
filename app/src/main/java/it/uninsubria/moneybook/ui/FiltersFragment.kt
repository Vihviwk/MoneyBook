package it.uninsubria.moneybook.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import it.uninsubria.moneybook.R


class FiltersFragment : DialogFragment() {

    private val selectedItems = ArrayList<Int>() //where I track the selected items

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;



            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_filters, null))
                // Add action buttons
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        //TODO("give results back")
                        dialog.dismiss()
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setTitle("Filters")
                .setMultiChoiceItems(R.array.categories, null,
                                        DialogInterface.OnMultiChoiceClickListener {
                                            dialog, which, isChecked ->
                                            if(isChecked) {
                                                selectedItems.add(which)
                                            } else if(selectedItems.contains(which)) {
                                                selectedItems.remove(Integer.valueOf(which))
                                            }
                                        })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}