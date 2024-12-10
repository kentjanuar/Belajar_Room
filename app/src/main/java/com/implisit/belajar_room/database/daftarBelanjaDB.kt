package com.implisit.belajar_room.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [daftarBelanja::class, historyBarang::class], version = 2)
abstract class daftarBelanjaDB : RoomDatabase() {
    abstract fun fundaftarBelanjaDAO(): daftarBelanjaDAO
    abstract fun funhistoryBarangDAO(): historyBarangDAO

    companion object {
        @Volatile
        private var INSTANCE: daftarBelanjaDB? = null

        @JvmStatic
        fun getDatabase(context: Context): daftarBelanjaDB {
            if(INSTANCE == null){
                synchronized(daftarBelanjaDB::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        daftarBelanjaDB::class.java,
                        "daftarbelanja_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as daftarBelanjaDB
        }

    }
}

//kalau misal ganti versi maka jangan lupa untuk .fallbackToDestructiveMigration() (tapi data hilang)

//@JvmStatic
//fun getDatabase(context: Context): daftarBelanjaDB {
//    return INSTANCE ?: synchronized(this) {
//        val instance = Room.databaseBuilder(
//            context.applicationContext,
//            daftarBelanjaDB::class.java,
//            "daftarbelanja_db"
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//        INSTANCE = instance
//        instance
//    }
//}

