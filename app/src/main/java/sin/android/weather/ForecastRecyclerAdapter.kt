package sin.android.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sin.android.weather.data.UsingWeather
import sin.android.weather.databinding.WeatherItemViewBinding


class ForecastRecyclerAdapter(
    private val forecastWeatherList: List<UsingWeather>
) : RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastViewHolder>() {


    class ForecastViewHolder(val binding: WeatherItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val viewHolder = ForecastViewHolder(
            WeatherItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastWeatherList.get(position)
        with(holder.binding) {
            viewTemp.text = item.temp.toString()
            viewTempFeelsLike.text = item.tempFeelsLike.toString()
            viewDescription.text = item.description
            viewTime.text = item.time
        }
    }

    override fun getItemCount() = forecastWeatherList.size
}