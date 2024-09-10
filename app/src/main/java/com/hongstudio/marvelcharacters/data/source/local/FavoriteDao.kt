package com.hongstudio.marvelcharacters.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites ORDER BY timestamp ASC")
    fun getAll(): Flow<List<CharacterLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterLocal: CharacterLocal)

    @Delete
    suspend fun delete(characterLocal: CharacterLocal)

    @Query("DELETE FROM favorites WHERE id IN (SELECT id FROM favorites ORDER BY timestamp ASC LIMIT :count)")
    suspend fun deleteOldestItems(count: Int)
}
