package sin.android.weather.di

import android.app.Application
import androidx.work.Worker
import dagger.BindsInstance
import dagger.Component
import sin.android.weather.MainActivity
import sin.android.weather.di.modules.*
import sin.android.weather.workmanager.PeriodicallyForecastRequestWorker
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelsModule::class,
        RetrofitRepositoryModule::class,
        RoomRepositoryModule::class,
        WorkManagerModule::class,
    ]
)

@Singleton
interface WeatherComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(periodicallyForecastRequestWorker: PeriodicallyForecastRequestWorker)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): WeatherComponent
    }
}