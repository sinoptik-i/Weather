package sin.android.weather.room

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sin.android.weather.addTimeString
import sin.android.weather.data.UsingWeather
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val preservedWeatherDao: PreservedWeatherDao,
    private val scope: CoroutineScope
) {
   suspend fun getAllWeather() = preservedWeatherDao
        .getAllWeathers().map {
            UsingWeather(
                it.tempFeelsLike,
                it.temp,
                it.description,
                addTimeString(it.date.toLong())
//                        addTimeString(it.date)
            )
    }

    fun saveWeather(preservedWeather: PreservedWeather) {
        scope.launch {
            preservedWeatherDao.saveOneWeather(preservedWeather)
        }
    }

    suspend fun saveForecast(forecast: List<PreservedWeather>) =
        preservedWeatherDao.saveForecast(forecast)

    suspend fun deleteForecast()=preservedWeatherDao.deleteForecast()

    fun trackaAllWeathers() = preservedWeatherDao.trackAllWeathers()


}