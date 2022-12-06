package sin.android.weather.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SavingWeather::class), version = 1)
abstract class PeriodicallyWeatherDatabase : RoomDatabase() {

    abstract fun savingWeatherDao(): SavingWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: PeriodicallyWeatherDatabase? = null
        fun getDataBase(context: Context): PeriodicallyWeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PeriodicallyWeatherDatabase::class.java,
                    "period_weather_db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}