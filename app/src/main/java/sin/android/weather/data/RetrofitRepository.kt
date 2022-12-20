package sin.android.weather.data

import sin.android.weather.retrofit.RetrofitUsing
import sin.android.weather.retrofit.WeatherRequests
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val retrofitUsing: WeatherRequests
) {
    //val retrofitUsing = RetrofitUsing.weatherRequests

    private val strPlace = "Lipetsk,ru"
    private val strLanguage = "ru"
    private val strUnits = "metric"
    private val APPID = "b1bde1ba8e7d072a163ca6aff1db67cb"


    suspend fun getNowWeather() =
        //retrofitUsing.getMomentaryWeather()
        retrofitUsing.getMomentaryWeatherParams(
            strPlace,
            strLanguage,
            strUnits,
            APPID
        )

    suspend fun getForecast()=
        retrofitUsing.get3DaysForecast(
            strPlace,
            strLanguage,
            strUnits,
            APPID
        )


    // suspend fun getNowWeatherParams()=
//suspend fun getNowWeather() =
    //  retrofitUsing.getMomentaryWeatherParams(strPlace)

}