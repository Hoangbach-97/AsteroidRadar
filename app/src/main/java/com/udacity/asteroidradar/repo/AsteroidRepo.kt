package com.udacity.asteroidradar.repo

import android.annotation.SuppressLint
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.util.Constants.API_QUERY_DATE_FORMAT
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AsteroidRepo {
    companion object {
        @SuppressLint("WeekBasedYear")
        private val dateTimeFormatter = DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT)
        private val todayTimeWithFormat = LocalDate.now().format(dateTimeFormatter)
        private val nextSevenDayWithFormat = LocalDate.now().plusDays(1).format(dateTimeFormatter)
    }

    suspend fun refreshDataAsteroid() {
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

        }
    }

}