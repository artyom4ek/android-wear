package os.wear.pulse.core

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import os.wear.pulse.di.component.DaggerAppComponent


class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val coreComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        coreComponent.inject(this)

        return coreComponent as AndroidInjector<out DaggerApplication>
    }
}
