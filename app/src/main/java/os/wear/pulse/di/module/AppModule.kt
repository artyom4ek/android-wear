package os.wear.pulse.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import os.wear.pulse.data.fileSystem.SharedPreferencesManager
import os.wear.pulse.domain.usecase.timer.TimerUseCase
import os.wear.pulse.domain.usecase.vibration.VibrationUseCase
import os.wear.pulse.presentation.viewModels.ViewModelFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideViewModelFactory(
        sharedPreferencesManager: SharedPreferencesManager,
        timerUseCase: TimerUseCase,
        vibrationUseCase: VibrationUseCase
    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            sharedPreferencesManager,
            timerUseCase,
            vibrationUseCase
        )
    }
}
