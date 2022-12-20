package sin.android.weather.retrofit

import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//import sin.android.weather.retrofit.data.AnswerForecastData
import sin.android.weather.retrofit.data.AnswerWeatherData
import sin.android.weather.retrofit.examples.AnswerForecastData

//import sin.android.weather.retrofit.examples.AnswerForecastData

//https://api.openweathermap.org/data/2.5/
// weather?q=Lipetsk,ru&APPID=b1bde1ba8e7d072a163ca6aff1db67cb&units=metric&lang=ru

interface WeatherRequests {

    @GET("weather?q=Lipetsk,ru&APPID=b1bde1ba8e7d072a163ca6aff1db67cb&units=metric&lang=ru")
    suspend fun getMomentaryWeather(): AnswerWeatherData

    @GET("weather?")
    suspend fun getMomentaryWeatherParams(
        @Query("q") place: String,
        @Query("lang") language: String,
        @Query("units") units: String,
        @Query("APPID") APPID: String
    ): AnswerWeatherData

    @GET("forecast?")
    suspend fun get3DaysForecast(
        @Query("q") place: String,
        @Query("lang") language: String,
        @Query("units") units: String,
        @Query("APPID") APPID: String
    ): AnswerForecastData

}