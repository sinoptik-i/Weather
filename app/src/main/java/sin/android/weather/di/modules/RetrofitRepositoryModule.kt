package sin.android.weather.di.modules

import dagger.Module
import dagger.Provides
import sin.android.weather.retrofit.RetrofitUsing
import sin.android.weather.retrofit.WeatherRequests


@Module
object RetrofitRepositoryModule {

    @Provides
    fun retrofitUsing(): WeatherRequests = RetrofitUsing.weatherRequests

}