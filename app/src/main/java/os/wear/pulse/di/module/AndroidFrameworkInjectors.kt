package os.wear.pulse.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import os.wear.pulse.di.ActivityScope
import os.wear.pulse.presentation.activities.MainActivity


@Module
abstract class AndroidFrameworkInjectors {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}