package sin.android.weather.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import sin.android.weather.data.RetrofitRepository
import sin.android.weather.data.UsingWeather
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ViewModelWeather @Inject constructor(
    private val retrofitRepository: RetrofitRepository
) : ViewModel() {

    val TAG = this.javaClass.simpleName

    suspend fun getUsingWeather() = retrofitRepository.getNowWeather().let {
        UsingWeather(
            it.main.feels_like,
            it.main.temp,
            it.weather[0].description,
            addTimeString(it.dt)
        ).also {
            Log.e(TAG, it.toString())
        }


    }


    fun getNowTime(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        return dateFormat.format(Calendar.getInstance().timeInMillis)
    }

    fun addTimeString(time: Int): String {
        val dateFormat = SimpleDateFormat("kk:mm:ss")
        return "${dateFormat.format(time)}"
    }

}