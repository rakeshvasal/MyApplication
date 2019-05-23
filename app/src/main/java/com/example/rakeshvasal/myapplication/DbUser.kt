package com.example.rakeshvasal.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class DbUser(
        @PrimaryKey var id: Int,
        var user_name: String?,
        var user_email: String?,
        var user_id: String?,
        var photourl: String?,
        var contact_no: String?,
        var branch: String?,
        var course_year: String?,
        var password: String?,
        var googleid: String?,
        var role: String?
)
