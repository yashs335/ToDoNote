package com.example.todonote.handler
import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.todonote.model.TaskModel

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "ToDO"
val COL_ID = "id"
val COL_TASK_TITLE = "task_title"
val COL_TASK = "task"
val COL_DATE = "modified_date"

class DatabaseHandler  (private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,2){
    override fun onCreate(db: SQLiteDatabase?) {

//        val createTable = "CREATE TABLE "+ TABLE_NAME + "("+
//                COL_ID + " INTEGER PRIMARY KEY AUTO INCREMENT,"+
//                COL_TASK_TITLE + " VARCHAR(50) NOT NULL ,"+
//                COL_TASK + " VARCHAR(200) NOT NULL )";


        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TASK_TITLE VARCHAR(50) NOT NULL, " +
                "$COL_TASK VARCHAR(200) NOT NULL, " +
                "$COL_DATE VARCHAR(50) NOT NULL)"


        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            Log.d("DB_UPGRADE", "Upgrading database from $oldVersion to $newVersion")
            val query = "ALTER TABLE $TABLE_NAME ADD COLUMN $COL_DATE VARCHAR(50)"
            db?.execSQL(query)
        }
    }


    fun createNewTask(id : Int?,title : String,body : String,update : Boolean = false){

        val db = this.writableDatabase
        val dateFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        var cv = ContentValues()
        cv.put(COL_TASK_TITLE,title)
        cv.put(COL_TASK,body)
        cv.put(COL_DATE, currentDate)

        if(update){
            db.update(
                TABLE_NAME,
                cv,
                "$COL_ID = ?",
                arrayOf(id.toString())
            )
            Log.i("SQL","update performed")
        }else{
            var res = db.insert(TABLE_NAME,null,cv)

            if(res == (-1).toLong())
                Toast.makeText(context, "FAILED TO ADD TASK",Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context, "Task added",Toast.LENGTH_LONG).show()


        }
    }

    fun readAllTask() : ArrayList<TaskModel>{
        val taskList : ArrayList<TaskModel> = ArrayList()

        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_NAME"

        val res = db.rawQuery(query,null)

        if(res.moveToFirst()){
            do {
                val id : Int = res.getString(res.getColumnIndexOrThrow(COL_ID)).toInt()
                val taskTitle : String = res.getString(res.getColumnIndexOrThrow(COL_TASK_TITLE))
                val taskBody : String = res.getString(res.getColumnIndexOrThrow(COL_TASK))
                val date = res.getString(res.getColumnIndexOrThrow(COL_DATE) ?: 0)?: ""

                val taskModel : TaskModel = TaskModel(id,taskTitle,taskBody,date)
//                Toast.makeText(context, "Title : "+taskTitle+" TASK : "+taskBody,Toast.LENGTH_LONG).show()
                taskList.add(taskModel)
            }while (res.moveToNext())

        }else{
            Toast.makeText(context, "NO Task ",Toast.LENGTH_LONG).show()
        }
        res.close()
        db.close()
        return taskList
    }


    fun deleteTheTask(id : Int){
        val db = this.writableDatabase


        val res = db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
//            db.execSQL(query)

        db.close()

    }



}