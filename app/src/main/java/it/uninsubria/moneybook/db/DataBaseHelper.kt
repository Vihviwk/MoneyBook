package it.uninsubria.moneybook.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//database values
private const val DATABASE_NAME = "MoneyBookDB"
private const val DB_VERSION = 1

//Transactions table
private val TABLE_NAME = "Transactions"
private val COL_ID = "id"
private val COL_AMOUNT = "amount"
private val COL_DATE = "date"
private val COL_MSG = "description"
private val COL_TYPE = "type"

private val CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        COL_AMOUNT + " REAL," +
        COL_DATE + " TEXT," +
        COL_MSG + " TEXT," +
        COL_TYPE + " TEXT)"


class DataBaseHelper(var context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TRANSACTION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(transaction : Transaction) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_AMOUNT, transaction.amount)
        contentValues.put(COL_TYPE, transaction.category)
        contentValues.put(COL_DATE, transaction.date)
        contentValues.put(COL_MSG, transaction.description)

        val result = database.insert(TABLE_NAME, null, contentValues)

        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData() : MutableList<Transaction> {
        //TODO("la funzione legge solo tutti i dati")
        val list : MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val transaction = Transaction()
                transaction.id = result.getInt(result.getColumnIndex(COL_ID))
                transaction.amount = result.getFloat(result.getColumnIndex(COL_AMOUNT))
                transaction.category = result.getString(result.getColumnIndex(COL_TYPE))
                transaction.date = result.getString(result.getColumnIndex(COL_DATE))
                transaction.description = result.getString(result.getColumnIndex(COL_MSG))
                list.add(transaction)
            } while (result.moveToNext())
        }
        result.close()
        return list

    }
}