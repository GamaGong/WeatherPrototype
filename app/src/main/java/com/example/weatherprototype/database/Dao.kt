package com.example.weatherprototype.database

import androidx.room.*
import com.example.weatherprototype.database.FavoriteLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteLocation: FavoriteLocation)

    @Query("delete from favorite_locations_table where name = :name")
    suspend fun remove(name: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(favoriteLocation: FavoriteLocation)

    @Query("select * from favorite_locations_table where name = :name")
    suspend fun get(name: String): FavoriteLocation?

    @Query("delete from favorite_locations_table")
    suspend fun clear()

    @Query("select * from favorite_locations_table order by name desc")
    fun getAll(): Flow<List<FavoriteLocation>>
}

@Dao
interface HardCodedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hardCodedLocation: HardCodedLocation)

    @Query("select * from hard_coded_locations_table order by name desc")
    fun getAll(): Flow<List<HardCodedLocation>>
}