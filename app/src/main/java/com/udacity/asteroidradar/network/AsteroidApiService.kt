package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


// URL for Asteroid: https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=YOUR_API_KEY
// URL for NASA image of the day: https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()



interface AsteroidApiService {

}