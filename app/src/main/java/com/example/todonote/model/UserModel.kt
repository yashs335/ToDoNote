package com.example.todonote.model

import java.sql.Timestamp

class UserModel(
    val userId : Int,
    val userName : String,
    val userEmail : String,
    val userPass : String,
    val cretedAt : Timestamp
)