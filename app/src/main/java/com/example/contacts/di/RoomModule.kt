package com.example.contacts.di

import android.content.Context
import androidx.room.Room
import com.example.contacts.data.data.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val USER_DATABASE_NAME = "user_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, UserDataBase::class.java, USER_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUserDao(db:UserDataBase) = db.getUserDao()
}