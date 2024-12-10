package com.implisit.belajar_room.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBarangDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: historyBarang)
}