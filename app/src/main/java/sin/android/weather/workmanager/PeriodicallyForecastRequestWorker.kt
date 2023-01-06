package sin.android.weather.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Operation
import androidx.work.Worker

import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import sin.android.weather.appComponent
import sin.android.weather.data.RetrofitRepository
import sin.android.weather.room.PreservedWeather
import sin.android.weather.room.RoomRepository
import javax.inject.Inject

class PeriodicallyForecastRequestWorker
    (context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {
    val TAG = this.javaClass.simpleName

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var roomRepository: RoomRepository

   init {
       applicationContext.appComponent.inject(this)
   }

    override fun doWork(): Result {
        return try {
            scope.launch {
                val forecast = retrofitRepository.getForecast()
                val savingForecast = forecast.list.map {
                    with(it) {
                        PreservedWeather(
                            main.feels_like,
                            main.temp,
                            weather[0].description,
                            dt
                        )
                    }
                        .also {
                            Log.e(TAG, it.toString())
                        }
                }
                if(savingForecast.isNotEmpty()) {
                    roomRepository.deleteForecast()
                    roomRepository.saveForecast(savingForecast)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            Result.failure()
        }
    }
}