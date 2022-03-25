package com.bakharaalief.menuapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity

@Database(entities = [MenuEntity::class], version = 1, exportSchema = false)
abstract class MenuDB : RoomDatabase() {

    abstract fun menuDao() : MenuDAO

    companion object {
        @Volatile
        private var instance: MenuDB? = null

        fun getInstance(context: Context): MenuDB =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MenuDB::class.java, "Menu.db"
                ).build()
            }
    }
}