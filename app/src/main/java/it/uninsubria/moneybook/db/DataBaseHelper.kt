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
private const val TABLE_NAME = "Transactions"
private const val COL_ID = "id"
private const val COL_AMOUNT = "amount"
private const val COL_DATE = "date"
private const val COL_MSG = "description"
private const val COL_TYPE = "type"

private const val CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
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
        //remember to change this if you update the app somehow - LO
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

    //query che ritorna tutte le transazioni in ordine di data
    fun readAllData() : MutableList<Transaction> {

        val list : MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME order by $COL_DATE desc"
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

    //query che ritorna il totale delle transazioni in un dato periodo
    fun totalAmount(period : Int) : Float{

        var amount = 0.0f
        val db = this.readableDatabase

        val query = when(period) {
            0 -> "Select sum($COL_AMOUNT) from $TABLE_NAME where $COL_DATE >= date('now', '-7 days');"
            1 -> "Select sum($COL_AMOUNT) from $TABLE_NAME where $COL_DATE >= date('now', 'start of month');"
            2 -> "Select sum($COL_AMOUNT) from $TABLE_NAME where $COL_DATE >= date('now', 'start of year');"
            3 -> "Select sum($COL_AMOUNT) from $TABLE_NAME"
            else -> ""
        }

        val result = db.rawQuery(query, null)
        if(result.moveToFirst())
            amount = result.getFloat(0)

        result.close()
        return amount
    }

    //query to delete all data
    fun deleteAll() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //query per leggere solo le transazioni di certe categorie
    fun readCategories(categories : ArrayList<Int>): MutableList<Transaction> {
        val list : MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase

        var s = ""
        for (item in categories) {
            if(s != "")
                s += ", "
            when (item) {
                0 -> s += "'Taxes'"
                1 -> s += "'Salary'"
                2 -> s += "'Restaurant'"
                3 -> s += "'Groceries'"
                4 -> s += "'Leisure'"
                5 -> s += "'Other'"
            }
        }

        val query = "Select * from $TABLE_NAME where $COL_TYPE in ($s) order by $COL_DATE desc"

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

    fun read(startDate : String, endDate : String, categories: ArrayList<Int>) : MutableList<Transaction> {
        val list :MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase

        if(categories.isEmpty())
            return read(startDate, endDate)
        else {

            var s = ""
            for (item in categories) {
                if(s != "")
                    s += ", "
                when (item) {
                    0 -> s += "'Taxes'"
                    1 -> s += "'Salary'"
                    2 -> s += "'Restaurant'"
                    3 -> s += "'Groceries'"
                    4 -> s += "'Leisure'"
                    5 -> s += "'Other'"
                }
            }

            val query = "Select * from $TABLE_NAME where ($COL_TYPE in($s) and ($COL_DATE between '$startDate' and '$endDate'))"

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

    fun read(startDate : String, endDate: String) : MutableList<Transaction> {
        val list :MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase

        val query = "Select * from $TABLE_NAME where $COL_DATE between '$startDate' and '$endDate'"

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

    fun readYear() : MutableList<Transaction> {
        val list :MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase

        val query = "Select * from $TABLE_NAME where $COL_DATE >= date('now', 'start of year');"

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

    fun readMonth() : MutableList<Transaction> {
        val list :MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase

        val query = "Select * from $TABLE_NAME where $COL_DATE >= date('now', 'start of month');"

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

    fun readWeek() : MutableList<Transaction> {
        val list :MutableList<Transaction> = ArrayList()
        val db = this.readableDatabase

        val query = "Select * from $TABLE_NAME where $COL_DATE >= date('now', '-7 days');"

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