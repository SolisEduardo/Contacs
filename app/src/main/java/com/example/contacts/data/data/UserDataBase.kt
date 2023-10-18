package com.example.contacts.data.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.data.data.dao.UserDao
import com.example.contacts.data.data.entities.UserEntities

@Database(entities = [UserEntities::class], version = 1)
 abstract class UserDataBase :RoomDatabase() {
     abstract fun getUserDao():UserDao
}