package os.wear.pulse.di.module

import dagger.Module
import dagger.Provides
import os.wear.pulse.data.repository.common.DefaultCountDownTimer
import javax.inject.Singleton


@Module
class TimerModule {

    @Provides
    @Singleton
    fun provideTimer(): DefaultCountDownTimer {
        return DefaultCountDownTimer()
    }
}