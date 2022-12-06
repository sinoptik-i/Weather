package sin.android.weather.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WeatherOneRequestWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}