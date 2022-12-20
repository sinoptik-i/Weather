package sin.android.weather.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import sin.android.weather.room.PeriodicallyWeatherDatabase
import sin.android.weather.room.PreservedWeatherDao
import javax.inject.Singleton

@Module
object RoomRepositoryModule {

    @Provides
    @Singleton
    fun getDataBase(context: Context): PeriodicallyWeatherDatabase =
        PeriodicallyWeatherDatabase.getDataBase(context)

    @Provides
    fun getDao(database: PeriodicallyWeatherDatabase): PreservedWeatherDao =
        database.savingWeatherDao()

    @Provides
    fun getScope(): CoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob())
}