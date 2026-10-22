package com.example.corpaliv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.corpaliv.model.Registro

@Database(entities = [Registro::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registroDao(): RegistroDao

    // se crea una sola base de datos para toda la app (igual que en MiAppRoom)
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "corpaliv_database"
                ).build().also { INSTANCE = it }
            }
    }
}
