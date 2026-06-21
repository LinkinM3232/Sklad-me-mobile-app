package com.example.sklad_me_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAllTrips(): List<TripEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrip(trip: TripEntity)
}