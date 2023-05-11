package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    //    Override functions area
    override suspend fun doWork(): Result {
        // TODO: Handle worker saving the next 7 days
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepo(database)
        return try {
            repository.refreshDataAsteroidAndSave()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}