package com.coderefer.newyorktimesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coderefer.newyorktimesapp.data.database.dao.MultiMediaDao
import com.coderefer.newyorktimesapp.data.database.dao.PostDao
import com.coderefer.newyorktimesapp.data.database.entity.MultiMedia
import com.coderefer.newyorktimesapp.data.database.entity.Post

@Database(entities = [Post::class, MultiMedia::class], version = 1, exportSchema = false)
abstract class PostsRoomDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {

        private const val DATABASE_NAME = "posts_database"

        @Volatile
        private var instance: PostsRoomDatabase? = null

        fun getDatabase(context: Context
        ): PostsRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PostsRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PostsRoomDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}