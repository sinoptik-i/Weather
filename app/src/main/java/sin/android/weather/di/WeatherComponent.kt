package sin.android.weather.di

import android.content.Context
import dagger.Component
import sin.android.weather.MainActivity
import sin.android.weather.di.modules.ModuleForRetrofitRepository
import sin.android.weather.di.modules.ViewModelsModule
import javax.inject.Singleton

@Component(
    modules = [
        ViewModelsModule::class,
        ModuleForRetrofitRepository::class
    ]
)

@Singleton
interface WeatherComponent {

    fun inject(mainActivity: MainActivity)
}