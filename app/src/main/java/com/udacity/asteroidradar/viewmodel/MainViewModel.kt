package com.udacity.asteroidradar.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidRepo = AsteroidRepo(database)

    init {
        viewModelScope.launch {
            try {
                asteroidRepo.run {
                    refreshDataAsteroidAndSave()
                    refreshImageOfTodayAndSave()
                }
            } catch (e: Exception) {
                Log.e("Asteroid_error", "Exception thrown: ${e.message}")
            }
        }
    }
    val pictureOfDay = asteroidRepo.picture

    val asteroidsSaved = asteroidRepo.asteroidsSaved

    val asteroidsWeek = asteroidRepo.asteroidsWeek

    val asteroidsToday = asteroidRepo.asteroidsToday

}