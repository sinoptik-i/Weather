package sin.android.weather.di.modules

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides


@Module
class WorkManagerModule {

    @Provides
    fun getWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
}
