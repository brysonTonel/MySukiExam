package com.bryson.mysukiexam.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bryson.mysukiexam.data.local.dao.UserDao
import com.bryson.mysukiexam.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}