package os.wear.pulse.di.component

import android.app.Application
import os.wear.pulse.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import os.wear.pulse.core.App
import os.wear.pulse.di.module.AndroidFrameworkInjectors
import os.wear.pulse.di.module.FileSystemModule
import os.wear.pulse.di.module.TimerModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidFrameworkInjectors::class,
        AppModule::class,
        FileSystemModule::class,
        TimerModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}