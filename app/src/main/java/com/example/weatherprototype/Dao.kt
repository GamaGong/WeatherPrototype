package com.example.weatherprototype

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteLocation: FavoriteLocation)

    @Query("delete from favorite_locations_table where location_name = :name")
    suspend fun remove(name: String)

    @Update
    suspend fun update(favoriteLocation: FavoriteLocation)

    @Query("select * from favorite_locations_table where favoriteId = :key")
    suspend fun get(key: Long): FavoriteLocation?

    @Query("delete from favorite_locations_table")
    suspend fun clear()

    @Query("select * from favorite_locations_table order by location_name desc")
    fun getAll(): Flow<List<FavoriteLocation>>
    @Query("select * from favorite_locations_table order by location_name desc")
    suspend fun getAllSuspend(): List<FavoriteLocation>
}