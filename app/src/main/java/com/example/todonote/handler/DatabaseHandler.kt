package com.example.todonote.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.todonote.model.TaskModel

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "ToDO"
val COL_ID = "id"
val COL_TASK_TITLE = "task_title"
val COL_TASK = "task"

class DatabaseHandler  (private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

//        val createTable = "CREATE TABLE "+ TABLE_NAME + "("+
//                COL_ID + " INTEGER PRIMARY KEY AUTO INCREMENT,"+
//                COL_TASK_TITLE + " VARCHAR(50) NOT NULL ,"+
//                COL_TASK + " VARCHAR(200) NOT NULL )";

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK_TITLE + " VARCHAR(50) NOT NULL, " +
                COL_TASK + " VARCHAR(200) NOT NULL)"


        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun createNewTask(taskModel: TaskModel){
        val db = this.writableDatabase

        var cv = ContentValues()
        cv.put(COL_TASK_TITLE,taskModel.title)
        cv.put(COL_TASK,taskModel.task)

        var res = db.insert(TABLE_NAME,null,cv)

        if(res == (-1).toLong())
            Toast.makeText(context, "FAILED TO ADD TASK",Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "TASK ADDED",Toast.LENGTH_LONG).show()

    }

    fun readAllTask() : ArrayList<TaskModel>{
        val taskList : ArrayList<TaskModel> = ArrayList()

        val db = this.readableDatabase

        val query = "SELECT * FROM "+ TABLE_NAME

        val res = db.rawQuery(query,null)

        if(res.moveToFirst()){
            do {
                val id : Int = res.getString(res.getColumnIndexOrThrow(COL_ID)).toInt()
                val taskTitle : String = res.getString(res.getColumnIndexOrThrow(COL_TASK_TITLE))
                val taskBody : String = res.getString(res.getColumnIndexOrThrow(COL_TASK))
                val taskModel : TaskModel = TaskModel(id,taskTitle,taskBody)
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
}