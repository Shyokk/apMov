package com.example.corpaliv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.corpaliv.model.Registro
import com.example.corpaliv.model.Usuario

// version 2 porque se agrego la tabla usuarios
@Database(entities = [Registro::class, Usuario::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registroDao(): RegistroDao

    abstract fun usuarioDao(): UsuarioDao

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
                )
                    // si cambia la version se borra y se crea de nuevo (sirve mientras desarrollamos)
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}
