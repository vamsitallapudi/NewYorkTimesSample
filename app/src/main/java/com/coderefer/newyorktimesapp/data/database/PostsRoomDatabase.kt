package com.coderefer.newyorktimesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coderefer.newyorktimesapp.data.home.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostsRoomDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostsRoomDatabase? = null

        fun getDatabase(context: Context): PostsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsRoomDatabase::class.java,
                    "posts_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}