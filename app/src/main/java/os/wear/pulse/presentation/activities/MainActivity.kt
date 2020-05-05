package os.wear.pulse.presentation.activities

import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.wear.ambient.AmbientModeSupport
import dagger.android.support.DaggerAppCompatActivity
import os.wear.pulse.databinding.MainActivityBinding
import os.wear.pulse.presentation.viewModels.MainViewModel
import javax.inject.Inject
import android.os.Bundle


class MainActivity : DaggerAppCompatActivity(),  AmbientModeSupport.AmbientCallbackProvider{

    @Inject lateinit var mMainViewModel: MainViewModel

    private var mAmbientController: AmbientModeSupport.AmbientController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: MainActivityBinding = DataBindingUtil.setContentView(
            this, os.wear.pulse.R.layout.main_activity)

        binding.lifecycleOwner = this
        binding.mainViewModel = mMainViewModel

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        mAmbientController = AmbientModeSupport.attach(this)
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {
        return DefaultAmbientCallback()
    }

    private inner class DefaultAmbientCallback : AmbientModeSupport.AmbientCallback() {
        override fun onEnterAmbient(ambientDetails: Bundle?) {

        }

        override fun onExitAmbient() {

        }

        override fun onUpdateAmbient() {

        }
    }
}