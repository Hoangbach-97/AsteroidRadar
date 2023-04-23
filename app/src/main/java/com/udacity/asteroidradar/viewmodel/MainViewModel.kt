package com.udacity.asteroidradar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val asteroidRepo = AsteroidRepo()

    private val asteroidList: MutableLiveData<MutableList<Asteroid>> = MutableLiveData()
    val _asteroidList: MutableLiveData<MutableList<Asteroid>>
        get() = asteroidList

    init {
        viewModelScope.launch {
            asteroidRepo.refreshDataAsteroid()
        }
    }
}