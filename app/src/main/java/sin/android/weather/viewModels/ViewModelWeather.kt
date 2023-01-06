package sin.android.weather.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import sin.android.weather.addTimeString
import sin.android.weather.data.RetrofitRepository
import sin.android.weather.data.UsingWeather
import sin.android.weather.getNowTime
import sin.android.weather.retrofit.data.AnswerWeatherData
import sin.android.weather.room.RoomRepository
import sin.android.weather.workmanager.GetWeatherWorkerV2
import sin.android.weather.workmanager.PeriodicallyForecastRequestWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

val FORECAST_TAG = "FORECAST_TAG"
val UNIQUE_FORECAST_WORK_NAME = "UNIQUE_FORECAST_WORK_NAME"

class ViewModelWeather @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val workManager: WorkManager,
    private val roomRepository: RoomRepository
) : ViewModel() {


    val TAG = this.javaClass.simpleName

    private val _nowWeather = MutableLiveData<UsingWeather>()
    val nowWeather: LiveData<UsingWeather> = _nowWeather

    private val _forecast = MutableLiveData<List<UsingWeather>>()

    val forecast: LiveData<List<UsingWeather>> = _forecast
//        val forecast: LiveData<List<UsingWeather>> = roomRepository.trackaAllWeathers().asLiveData()
/*    val forecast: LiveData<List<UsingWeather>> = LiveData {
        val data = getUsingWeather()
        emit(data)
    }*/


    fun refreshWeather() {
        viewModelScope.launch {
            _nowWeather.value = getUsingWeather()
        }
    }

    fun refreshSavedForecast() {
        viewModelScope.launch {
            _forecast.value = roomRepository.getAllWeather()
        }
    }

    suspend fun getUsingWeather() = retrofitRepository.getNowWeather().let {
//        val timeStr = "${addTimeString(it.dt)}, (${getNowTime()})"
        val timeStr = "${addTimeString(it.dt.toLong())}, (${getNowTime()})"
        //  Log.e(TAG, timeStr)
        UsingWeather(
            it.main.feels_like,
            it.main.temp,
            it.weather[0].description,
            timeStr
        ).also {
            Log.e(TAG, it.toString())
        }
    }


    fun takeForecast() {
        val workRequest = OneTimeWorkRequestBuilder<PeriodicallyForecastRequestWorker>()
            .addTag(FORECAST_TAG)
            .build()
        workManager.enqueue(workRequest)
    }


    fun startPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<PeriodicallyForecastRequestWorker>(
            3,
            TimeUnit.HOURS,
            2,
            TimeUnit.HOURS
        )
//            .addTag(FORECAST_TAG)
            .setConstraints(constraints)
            .build()

        workManager
            .enqueueUniquePeriodicWork(UNIQUE_FORECAST_WORK_NAME,ExistingPeriodicWorkPolicy.KEEP,workRequest)

    }
}
