package com.udacity.asteroidradar.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidsAndPictureOfDayDatabase
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.util.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.util.parseAsteroidsJsonResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AsteroidRepo(
    private val database: AsteroidsAndPictureOfDayDatabase
) {


    companion object {
        @SuppressLint("WeekBasedYear")
        private val dateTimeFormatter = DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT)
        private val todayTimeWithFormat = LocalDate.now().format(dateTimeFormatter)
        private val nextSevenDayWithFormat = LocalDate.now().plusDays(7).format(dateTimeFormatter)
    }

    suspend fun refreshDataAsteroidAndSave() {
        withContext(Dispatchers.IO) {
            val result = AsteroidApi
                .retrofitService
                .getAsteroidProperties(
                    startDate = todayTimeWithFormat,
                    endDate = nextSevenDayWithFormat
                )
            val bodyResponse = parseAsteroidsJsonResult(
                JSONObject(
                    result
                )
            )
            database.asteroidDao.insertAll(*bodyResponse.asDatabaseModel())

        }
    }


    suspend fun refreshImageOfTodayAndSave() {
        withContext(Dispatchers.IO) {
            val result = AsteroidApi.retrofitService.getPlanetaty()
            if (result.mediaType == "image") database.pictureOfDayDao.insertAll(result.asDatabaseModel())
        }
    }


    val picture: LiveData<PictureOfDay> =
        Transformations.map(database.pictureOfDayDao.getTheLatestPictureOfDay()) {
                it?.asDomainModel()
        }

    val asteroidsSaved: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroidListSaved()) {
            it?.asDomainModel()
        }


    val asteroidsToday: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroidListToday(todayTimeWithFormat.toString())) {
            it?.asDomainModel()
        }

    val asteroidsWeek: LiveData<List<Asteroid>> =
        Transformations.map(
            database.asteroidDao.getAsteroidListWeek(
                todayTimeWithFormat.toString(),
                nextSevenDayWithFormat.toString()
            )
        ) {
            it?.asDomainModel()
        }


// TODO: Today
// TODO: Week
}