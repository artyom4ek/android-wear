package os.wear.pulse.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import os.wear.pulse.data.fileSystem.SharedPreferencesManager
import javax.inject.Singleton


@Module
class FileSystemModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }
}