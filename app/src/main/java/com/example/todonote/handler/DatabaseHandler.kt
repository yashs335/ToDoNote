@file:Suppress("UNREACHABLE_CODE")

package com.example.todonote.handler

import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat.startActivity
import com.example.todonote.LoginActivity
import com.example.todonote.MainActivity
import com.example.todonote.model.TaskModel
import com.example.todonote.view.ViewAuth
import com.example.todonote.view.ViewToDo

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


class DatabaseHandler(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1), ViewAuth, ViewToDo {
    override fun onCreate(db: SQLiteDatabase?) {

        val userTable = "CREATE TABLE $AUTH_TABLE_NAME ( " +
                "$AUTH_COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$AUTH_COL_USER_NAME VARCHAR(50) NOT NULL, " +
                "$AUTH_COL_EMAIL VARCHAR(100) NOT NULL, " +
                "$AUTH_COL_PASS VARCHAR(50) NOT NULL , " +
                "$AUTH_COL_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP );"

        db?.execSQL(userTable)

        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TASK_TITLE VARCHAR(50) NOT NULL, " +
                "$COL_TASK VARCHAR(200) NOT NULL, " +
                "$COL_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "$COL_UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "$AUTH_COL_EMAIL VARCHAR(100) NOT NULL, " +
                "FOREIGN KEY ($AUTH_COL_EMAIL) REFERENCES $AUTH_TABLE_NAME($AUTH_COL_EMAIL) ON DELETE CASCADE);"


        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    override fun createNewTask(
        title: String,
        body: String
    ) {

        val db = this.writableDatabase
        val dateFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE)
        val email : String = sharedPreferences.getString("user_email","").toString()
        Log.i("email from create task",email)

        val cv = ContentValues()
        cv.put(COL_TASK_TITLE, title)
        cv.put(COL_TASK, body)
        cv.put(COL_CREATED_AT, currentDate)
        cv.put(AUTH_COL_EMAIL, email)

        val res = db.insert(TABLE_NAME, null, cv)

        if (res == (-1).toLong())
                Toast.makeText(context, "FAILED TO ADD TASK", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context, "Task added", Toast.LENGTH_LONG).show()
    }

    override fun editTask(title: String, body: String,taskId:Int) {
        val db = this.writableDatabase
        val dateFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE)
        val email : String = sharedPreferences.getString("user_email","").toString()
        Log.i("email from edit task",email)

        val cv = ContentValues()
        cv.put(COL_TASK_TITLE, title)
        cv.put(COL_TASK, body)
        cv.put(COL_UPDATED_AT, currentDate)

            db.update(
                TABLE_NAME,
                cv,
                "$COL_ID = ?",
                arrayOf(taskId.toString())
            )
            Log.i("SQL", "task updated")
    }
//    }

    override fun readAllTask(email: String): ArrayList<TaskModel> {
        val taskList: ArrayList<TaskModel> = ArrayList()

        Log.i("email from realALlTask ",email)

        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_NAME WHERE $AUTH_COL_EMAIL = ?"

        val res = db.rawQuery(query, arrayOf(email))

        if (res.moveToFirst()) {
            do {
                val id: Int = res.getString(res.getColumnIndexOrThrow(COL_ID)).toInt()
                val taskTitle: String = res.getString(res.getColumnIndexOrThrow(COL_TASK_TITLE))
                val taskBody: String = res.getString(res.getColumnIndexOrThrow(COL_TASK))
                val createDate: String = res.getString(res.getColumnIndexOrThrow(COL_CREATED_AT))
                val updateDate: String = res.getString(res.getColumnIndexOrThrow(COL_UPDATED_AT))

                val taskModel: TaskModel =
                    TaskModel(id, email, taskTitle, taskBody, createDate, updateDate)
//                Toast.makeText(context, "Title : "+taskTitle+" TASK : "+taskBody,Toast.LENGTH_LONG).show()
                taskList.add(taskModel)
            } while (res.moveToNext())

        }
        res.close()
        db.close()
        return taskList
    }

    override fun deleteTheTask(id: Int) {
        val db = this.writableDatabase


        val res = db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
//            db.execSQL(query)

        db.close()

    }

    override fun loginUser(email: String, pass: String): Boolean {
        val db = this.readableDatabase

        try {
            // Use parameterized query
            val query = "SELECT * FROM $AUTH_TABLE_NAME WHERE $AUTH_COL_EMAIL = ?"
            val res = db.rawQuery(query, arrayOf(email))

            if (res.moveToFirst()) {
                val storedPassword = res.getString(res.getColumnIndexOrThrow(AUTH_COL_PASS))

                return if (storedPassword == pass) {
                    saveUser(email)
                    true
                } else {
                    Toast.makeText(context, "Invalid password", Toast.LENGTH_LONG).show()
                    false
                }
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
                return false
            }

        } catch (e: Exception) {
            Log.e("DB_ERROR", "Login Error: ${e.message}")
            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show()
        } finally {
            db.close()
        }

        return false
    }

    private fun saveUser(email: String) {
        try {
            val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("user_email", email)
            editor.apply() // or use .commit() to write synchronously
//
            Log.i("SharedPrefs login ", "User email saved: $email")

        } catch (e: Exception) {
            Log.e("DB_ERROR", "saveUser Error: ${e.message}")
            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show()
        }
    }

    override fun signUp(email: String, pass: String, userName: String) : Boolean{
        val db = this.writableDatabase

        try {
            val cursor = db.rawQuery(
                "SELECT * FROM $AUTH_TABLE_NAME WHERE $AUTH_COL_EMAIL = ?",
                arrayOf(email)
            )

            if (cursor.moveToFirst()) {
                Toast.makeText(context, "User already exists", Toast.LENGTH_LONG).show()
                cursor.close()
                return false
            }
            cursor.close()

            val cv = ContentValues().apply {
                put(AUTH_COL_EMAIL, email)
                put(AUTH_COL_PASS, pass)
                put(AUTH_COL_USER_NAME, userName)
            }

            val result = db.insert(AUTH_TABLE_NAME, null, cv)
            if (result == -1L) {
                Toast.makeText(context, "Failed to sign up", Toast.LENGTH_LONG).show()
                return false
            } else {
                Toast.makeText(context, "Signup successful", Toast.LENGTH_LONG).show()
                saveUser(email)
                return true
            }

        } catch (e: Exception) {
            Log.e("DB_ERROR", "Signup Error: ${e.message}")
            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show()
        } finally {
            db.close()
        }
        return false
    }

    override fun logOut() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("user", MODE_PRIVATE)
        sharedPreferences.edit().remove("user_email").apply()
//        sharedPreferences.edit().putString("user_email","").apply()

    }
}