package com.udacity.asteroidradar.viewmodel

import android.app.Application
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
            asteroidRepo.run {
                refreshDataAsteroidAndSave()
                refreshImageOfTodayAndSave()
            }
        }
    }

    val pictureOfDay = asteroidRepo.picture

    val asteroidsWeek = asteroidRepo.asteroidsWeek

}