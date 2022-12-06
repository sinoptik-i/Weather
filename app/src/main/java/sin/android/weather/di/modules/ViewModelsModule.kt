package sin.android.weather.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import sin.android.weather.di.ViewModelKey
import sin.android.weather.viewModels.ViewModelFactory
import sin.android.weather.viewModels.ViewModelWeather
import javax.inject.Provider
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelWeather::class)
    abstract fun oneVeaherRequest(vm: ViewModelWeather): ViewModel


    @Module
    companion object{
        @Provides
        @JvmStatic
        @Singleton
        fun provideViewModelFactory(
            viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory = ViewModelFactory(viewModels)
    }
}