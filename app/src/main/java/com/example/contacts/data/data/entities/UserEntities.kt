package com.example.contacts.data.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.contacts.domain.model.User

@Entity(tableName = "user_table")
data class UserEntities(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int? = 0,

    @ColumnInfo("lastName") val lastName: String? = null,

    @ColumnInfo("avatar") val avatar: String? = null,

    @ColumnInfo("firstName") val firstName: String? = null,

    @ColumnInfo("email") val email: String? = null
)
fun User.toDataBase() =UserEntities(id = id,lastName = lastName, avatar = avatar, firstName = firstName, email = email)