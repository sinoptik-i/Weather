package sin.android.weather.retrofit

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import sin.android.weather.retrofit.data.AnswerWeatherData

//https://api.openweathermap.org/data/2.5/
// weather?q=Lipetsk,ru&APPID=b1bde1ba8e7d072a163ca6aff1db67cb&units=metric&lang=ru

interface WeatherRequests {

    @GET("weather?q=Lipetsk,ru&APPID=b1bde1ba8e7d072a163ca6aff1db67cb&units=metric&lang=ru")
    suspend fun getMomentaryWeather(): AnswerWeatherData
}