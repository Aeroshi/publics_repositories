package com.aeroshi.repositories.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aeroshi.repositories.data.entitys.Rep


@Dao
interface PublicReps {

    @Query("SELECT * FROM repositories")
    fun getPublicReps(): List<Rep>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(configManager: List<Rep>)

    @Query("DELETE FROM repositories")
    fun deleteTable(): Int

    @Query("UPDATE repositories SET id=0 WHERE NAME='repositories'")
    fun resetTable(): Int
}




