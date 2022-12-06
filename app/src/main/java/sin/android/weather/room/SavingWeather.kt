package sin.android.weather.room

import androidx.room.*

@Entity(tableName = "usingWeathers")
data class SavingWeather(
    @ColumnInfo(name = "tempFeelsLike") val tempFeelsLike: Double,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "description") val description: String,
    @PrimaryKey(autoGenerate = false) val date: Int,
)


@Dao
interface SavingWeatherDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOneWeather(savingWeather: SavingWeather)

    @Query("SELECT * FROM usingWeathers")
    fun getAllWeathers(): List<SavingWeather>
}