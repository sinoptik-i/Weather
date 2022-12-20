package sin.android.weather.room

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val preservedWeatherDao: PreservedWeatherDao,
    private val scope: CoroutineScope
) {
    fun getAllPeriodicallyWeather() = preservedWeatherDao.getAllWeathers()

    fun saveWeather(preservedWeather: PreservedWeather) {
        scope.launch {
            preservedWeatherDao.saveOneWeather(preservedWeather)
        }
    }

    suspend fun saveForecast(forecast: List<PreservedWeather>) =
        preservedWeatherDao.saveForecast(forecast)

    suspend fun deleteForecast()=preservedWeatherDao.deleteForecast()




}