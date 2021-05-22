package it.uninsubria.moneybook.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "Add Transaction"

class AddTransactionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }
}