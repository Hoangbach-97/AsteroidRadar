package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("select * from databaseAsteroid order by closeApproachDate")
    fun getAsteroidListSaved(): LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseAsteroid where closeApproachDate = :today")
    fun getAsteroidListToday(today: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseAsteroid where closeApproachDate between :today and :endDate")
    fun getAsteroidListWeek(today: String, endDate: String): LiveData<List<DatabaseAsteroid>>

}

@Dao
interface PictureOfDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pictureOfDay: DatabasePictureOfDay)

    @Query("select * from databasePictureOfDay")
    fun getTheLatestPictureOfDay(): LiveData<DatabasePictureOfDay>
}

// Create room database
@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfDay::class], version = 1)
abstract class AsteroidsAndPictureOfDayDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureOfDayDao: PictureOfDayDao
}


// Create singleton
private lateinit var INSTANCE: AsteroidsAndPictureOfDayDatabase

fun getDatabase(context: Context): AsteroidsAndPictureOfDayDatabase {
    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            AsteroidsAndPictureOfDayDatabase::class.java,
            "asteroids_picture_database"
        ).build()
    }
    return INSTANCE
}