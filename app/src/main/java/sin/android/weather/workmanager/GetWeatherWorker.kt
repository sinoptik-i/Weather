package sin.android.weather.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import sin.android.weather.data.RetrofitRepository
import sin.android.weather.di.DaggerWeatherComponent
import javax.inject.Inject

class GetWeatherWorker(
    context: Context,
    parameters: WorkerParameters
) :
    Worker(context, parameters) {


    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}