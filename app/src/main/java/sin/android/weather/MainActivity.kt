package sin.android.weather

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import sin.android.weather.data.UsingWeather
import sin.android.weather.databinding.ActivityMainBinding
import sin.android.weather.di.DaggerWeatherComponent
import sin.android.weather.di.WeatherComponent
import sin.android.weather.viewModels.FORECAST_TAG
import sin.android.weather.viewModels.ViewModelWeather
import javax.inject.Inject

class MainApp : Application() {
    lateinit var weatherComponent: WeatherComponent

    override fun onCreate() {
        super.onCreate()
        weatherComponent =
            DaggerWeatherComponent.builder()
                .application(this)
                .build()
    }
}


val Context.appComponent: WeatherComponent
    get() = when (this) {
        is MainApp -> weatherComponent
        else -> this.applicationContext.appComponent
    }


class MainActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var workManager: WorkManager

    private lateinit var binding: ActivityMainBinding

    private val viewModelWeather: ViewModelWeather by viewModels(factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        appComponent.inject(this)
        setContentView(binding.root)
        //  binding.buttonGetWeather.setOnClickListener {

        binding.swipeRefreshLayout.setOnRefreshListener {
            //viewModelWeather.refreshWeather()
            viewModelWeather.takeForecast()
            // getNowWeather()
        }
        viewModelWeather.nowWeather.observe(this, observeNowWeather())
        observeForecast()

    }

    private fun observeForecast() {
        workManager
            .getWorkInfosByTagLiveData(FORECAST_TAG)
            .observe(this, {
                if(!it.isEmpty()) {
                    val workInfo = it[0]
                    Log.e(TAG, "${workInfo.state}")
                }
/*
                if(it[0].state==WorkInfo.State.SUCCEEDED)
                {}
*/
            })
    }


    override fun onResume() {
        super.onResume()
        viewModelWeather.refreshWeather()
        // getNowWeather()
    }

    private fun observeNowWeather(): Observer<UsingWeather> {
        return Observer {
            with(binding) {
                viewTemp.text = it.temp.toString()
                viewTempFeelsLike.text = it.tempFeelsLike.toString()
                viewDescription.text = it.description
                viewTime.text = it.time
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }


    fun getNowWeather() {
        lifecycle.coroutineScope.launch {
            try {
                viewModelWeather.getUsingWeather().let {
                    with(binding) {
                        viewTemp.text = it.temp.toString()
                        viewTempFeelsLike.text = it.tempFeelsLike.toString()
                        viewDescription.text = it.description
                        viewTime.text = it.time
                        swipeRefreshLayout.isRefreshing = false
                    }


                }
            } catch (err: Throwable) {
                Log.e(TAG, "${err.message},   ${err.stackTrace}")
            }
        }
    }



}