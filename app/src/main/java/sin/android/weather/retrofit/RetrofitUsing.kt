package sin.android.weather.retrofit

object RetrofitUsing {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val weatherRequests: WeatherRequests
        get() = RetrofitClient.getClient(BASE_URL).create(WeatherRequests::class.java)
}