package it.uninsubria.moneybook.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.uninsubria.moneybook.R


class FiltersFragment : DialogFragment() {

    interface FiltersListener {
        fun onFiltersSelection(selected : ArrayList<Int>)
        fun onResetFilters()
    }

    private val selectedItems = ArrayList<Int>()
    private lateinit var listener : FiltersListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                // Add action buttons
                builder.setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onFiltersSelection(selectedItems)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                .setNeutralButton(R.string.resetFilters,
                    DialogInterface.OnClickListener { dialog , id ->
                        listener.onResetFilters()
                    })
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setTitle(getString(R.string.filters_title))
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

    // Override the Fragment.onAttach() method to instantiate the FiltersListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the FiltersListener so we can send events to the host
            listener = context as FiltersListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement FiltersListener"))
        }
    }

}