package sin.android.weather.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import sin.android.weather.data.RetrofitRepository
import sin.android.weather.data.UsingWeather
import sin.android.weather.retrofit.data.AnswerWeatherData
import sin.android.weather.workmanager.GetWeatherWorkerV2
import sin.android.weather.workmanager.PeriodicallyForecastRequestWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

val FORECAST_TAG = "FORECAST_TAG"

class ViewModelWeather @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val workManager: WorkManager
) : ViewModel() {


    val TAG = this.javaClass.simpleName

    private val _nowWeather = MutableLiveData<UsingWeather>()
    val nowWeather: LiveData<UsingWeather> = _nowWeather

/*    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date*/


    fun refreshWeather() {
        viewModelScope.launch {
            _nowWeather.value = getUsingWeather()
        }
    }

    suspend fun getUsingWeather() = retrofitRepository.getNowWeather().let {
        val timeStr = "${addTimeString(it.dt)}, (${getNowTime()})"
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
            .addTag(FORECAST_TAG)
            .setConstraints(constraints)
            .build()

        workManager
            .enqueue(workRequest)


    }


    fun getNowTime(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy kk:mm")
        return dateFormat.format(Calendar.getInstance().timeInMillis)
    }

    fun addTimeString(time: Int): String {
        val dateFormat = SimpleDateFormat("kk:mm:ss")
        return "${dateFormat.format(time)}"
    }

}