package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.util.Constants.TODAY
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Covert for saving to local
fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return map {
        DatabaseAsteroid(
            id = it.id,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            closeApproachDate = it.closeApproachDate,
            name = it.codename
        )
    }.toTypedArray()
}

fun PictureOfDay.asDatabaseModel(): DatabasePictureOfDay {
    return DatabasePictureOfDay(
        url = url,
        mediaType = mediaType,
        title = title
    )


}