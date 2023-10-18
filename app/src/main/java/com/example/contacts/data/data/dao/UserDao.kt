package com.example.contacts.data.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contacts.data.data.entities.UserEntities
@Dao
interface UserDao {
    //getAllUser
    @Query("SELECT * FROM user_table ORDER BY firstName ASC")
    suspend fun getAllUser():List<UserEntities>

    //insert List User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<UserEntities>)

    //insert user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(user: UserEntities)

    //delete user
    /*@Query("SELECT * FROM user_table WHERE id =:id")
    suspend fun deleteSpecificUser(id: Int)*/

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    //update user
   /* @Query("SELECT * FROM user_table WHERE id =:id")
    suspend fun updateUser(user: UserEntities)*/
}