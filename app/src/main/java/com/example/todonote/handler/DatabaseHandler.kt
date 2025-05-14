@file:Suppress("UNREACHABLE_CODE")

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
import com.example.todonote.model.UserModel
import java.sql.Timestamp

val DATABASE_NAME = "ToDoListDB"
val TABLE_NAME = "todos"
val COL_ID = "id"
val COL_TASK_TITLE = "task_title"
val COL_TASK = "task"
val COL_CREATED_AT = "created_at"
val COL_UPDATED_AT = "updated_at"

val AUTH_TABLE_NAME = "users"
val AUTH_COL_USER_ID = "userId"
val AUTH_COL_EMAIL = "email"
val AUTH_COL_PASS = "password"
val AUTH_COL_CREATED_AT = "created_at"
val AUTH_COL_USER_NAME = "user_name"


class DatabaseHandler  (private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        val userTable = "CREATE TABLE $AUTH_TABLE_NAME ( " +
                "$AUTH_COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$AUTH_COL_USER_NAME VARCHAR(50) NOT NULL, " +
                "$AUTH_COL_EMAIL VARCHAR(100) NOT NULL, " +
                "$AUTH_COL_PASS VARCHAR(50) NOT NULL , "+
                "$AUTH_COL_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP );"

        db?.execSQL(userTable)

        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TASK_TITLE VARCHAR(50) NOT NULL, " +
                "$COL_TASK VARCHAR(200) NOT NULL, " +
                "$COL_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "$COL_UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "$AUTH_COL_USER_ID INTEGER NOT NULL, "+
                "FOREIGN KEY ($AUTH_COL_USER_NAME) REFERENCES $AUTH_TABLE_NAME($AUTH_COL_USER_ID) ON DELETE CASCADE);"


        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun createNewTask(id : Int?,title : String,body : String,userId : Int,update : Boolean = false){

        val db = this.writableDatabase
        val dateFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        var cv = ContentValues()
        cv.put(COL_TASK_TITLE,title)
        cv.put(COL_TASK,body)
        cv.put(COL_UPDATED_AT, currentDate)
        cv.put(AUTH_COL_USER_ID, userId)

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

    fun readAllTask(userId: Int) : ArrayList<TaskModel>{
        val taskList : ArrayList<TaskModel> = ArrayList()

        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_NAME WHERE $AUTH_COL_USER_ID = $userId"

        val res = db.rawQuery(query,null)

        if(res.moveToFirst()){
            do {
                val id : Int = res.getString(res.getColumnIndexOrThrow(COL_ID)).toInt()
                val taskTitle : String = res.getString(res.getColumnIndexOrThrow(COL_TASK_TITLE))
                val taskBody : String = res.getString(res.getColumnIndexOrThrow(COL_TASK))
                val createDate : String = res.getString(res.getColumnIndexOrThrow(COL_CREATED_AT))
                val updateDate : String = res.getString(res.getColumnIndexOrThrow(COL_UPDATED_AT))

                val taskModel : TaskModel = TaskModel(id,userId,taskTitle,taskBody,createDate,updateDate)
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

    fun loginUser(email : String,pass : String):String?{
        val db = this.readableDatabase

        val query = "SELECT * FROM $AUTH_TABLE_NAME WHERE $AUTH_COL_EMAIL = $email"

        val res = db.rawQuery(query,null)

        if(res.moveToFirst()){
            do {
                val id : Int = res.getInt(res.getColumnIndexOrThrow(AUTH_COL_USER_ID))
                val emailSql : String = res.getString(res.getColumnIndexOrThrow(AUTH_COL_EMAIL))
                val password : String = res.getString(res.getColumnIndexOrThrow(AUTH_COL_PASS))

                if(emailSql == email && password != pass){
                    return "Invalid password"
                }else if(email == emailSql || password == pass){
                    return null
                }
                return "User Not Found"
            }while (res.moveToNext())
        }else{
            Toast.makeText(context,"User not found",Toast.LENGTH_LONG).show()
            return "User not found"
        }
        db.close()
    }

    fun signUp(email : String,pass: String,userName : String,id: Int?,update : Boolean = false) {
        val user : String? = loginUser(email,pass)

        if(user == null){
            Toast.makeText(context, "User already exist ",Toast.LENGTH_LONG).show()
            return
        }else{
            val db = this.writableDatabase


            val cv = ContentValues()
            cv.put(AUTH_COL_EMAIL,email)
            cv.put(AUTH_COL_PASS,pass)
            cv.put(AUTH_COL_USER_NAME,userName)

            if(update){
                Toast.makeText(context,"  P  ",Toast.LENGTH_LONG).show()
            }else{
                val res = db.insert(AUTH_TABLE_NAME,null,cv)
                if(res == (-1).toLong())
                    Toast.makeText(context, "FAILED TO SIGN UP",Toast.LENGTH_LONG).show()
//                else
//                    Toast.makeText(context, "LOGIN",Toast.LENGTH_LONG).show()
            }
        }

    }
}