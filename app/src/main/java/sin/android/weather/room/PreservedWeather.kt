package sin.android.weather.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "usingWeathers")
data class PreservedWeather(
    @ColumnInfo(name = "tempFeelsLike") val tempFeelsLike: Double,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "description") val description: String,
    @PrimaryKey(autoGenerate = false) val date: Int,
)


@Dao
interface PreservedWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOneWeather(preservedWeather: PreservedWeather)

    @Query("SELECT * FROM usingWeathers")
    suspend fun getAllWeathers(): List<PreservedWeather>

    @Query("SELECT * FROM usingWeathers")
    fun trackAllWeathers(): Flow<List<PreservedWeather>>

    @Insert(onConflict = REPLACE)
    suspend fun saveForecast(forecast: List<PreservedWeather>)

    @Query("DELETE FROM usingWeathers")
    suspend fun deleteForecast()
}