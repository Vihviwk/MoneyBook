package it.uninsubria.moneybook.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
}