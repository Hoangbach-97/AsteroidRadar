package com.udacity.asteroidradar.util

import java.time.LocalDate

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY ="WVS2cKMXVCgRvVwiomouHJ3pS4PJIyffhpprqZPB"
     var TODAY =  LocalDate.now().dayOfMonth.toString()
}