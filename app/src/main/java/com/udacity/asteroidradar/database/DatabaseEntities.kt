package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import java.time.LocalDate

@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    val absoluteMagnitude: Double,
    // Get estimated_diameter_max field
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
    val closeApproachDate: String
)

// Convert for display on screen
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map { asteroidItem ->
        Asteroid(
            id = asteroidItem.id,
            absoluteMagnitude = asteroidItem.absoluteMagnitude,
            estimatedDiameter = asteroidItem.estimatedDiameter,
            relativeVelocity = asteroidItem.relativeVelocity,
            distanceFromEarth = asteroidItem.distanceFromEarth,
            isPotentiallyHazardous = asteroidItem.isPotentiallyHazardous,
            closeApproachDate = asteroidItem.closeApproachDate,
            codename = asteroidItem.name
        )
    }
}

@Entity
data class DatabasePictureOfDay constructor(
    @PrimaryKey
    val url: String,
    val mediaType: String,
    val title: String
)

fun DatabasePictureOfDay.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        url = url,
        mediaType = mediaType,
        title = title
    )
}
