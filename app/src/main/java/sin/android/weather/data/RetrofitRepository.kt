package sin.android.weather.data

import sin.android.weather.retrofit.RetrofitUsing
import sin.android.weather.retrofit.WeatherRequests
import javax.inject.Inject

class RetrofitRepository @Inject constructor (
   private val retrofitUsing: WeatherRequests
) {
    //val retrofitUsing = RetrofitUsing.weatherRequests

    suspend fun getNowWeather() =
        retrofitUsing.getMomentaryWeather()


}