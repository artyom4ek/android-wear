package os.wear.pulse.presentation.viewModels

import android.view.View
import androidx.lifecycle.ViewModel
import os.wear.pulse.R
import androidx.lifecycle.MutableLiveData
import os.wear.pulse.data.fileSystem.SharedPreferencesManager
import os.wear.pulse.domain.common.IntensityDurationType.*
import os.wear.pulse.domain.common.IntensityType.*
import os.wear.pulse.domain.usecase.timer.TimerUseCase
import os.wear.pulse.domain.usecase.vibration.VibrationUseCase
import os.wear.pulse.presentation.common.ObservableToLiveData
import javax.inject.Inject


class MainViewModel
@Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val timerUseCase: TimerUseCase,
    private val vibrationUseCase: VibrationUseCase
) :
    ViewModel() {

    val mStartButtonStateName = MutableLiveData<String>().apply {
        postValue("START")
    }

    private val mStart = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    val mVibrationStatusLiveData = ObservableToLiveData.map(vibrationUseCase.vibrationStatus)
    val mIsIntensityEnabled = vibrationUseCase.intensityEnabled

    fun onClickStartButton() {
        mStart.value = !mStart.value!!
        mStartButtonStateName.value = if (mStart.value!!) "STOP" else "START"

        if (mStart.value!!) timerUseCase.startTimer()
        else timerUseCase.stopTimer()
    }

    fun intensityControl(v: View?) {
        val intensity = when (v?.id) {
            R.id.increase_pulse_intensity -> {
                INCREASE
            }
            R.id.decrease_pulse_intensity -> {
                DECREASE
            } else -> NONE_INTENSITY
        }
        vibrationUseCase.changeIntensityConfig(intensity)
    }

    fun intensityDurationControl(v: View) {
        val duration = when (v.id) {
            R.id.intensity_duration_one -> {
                LOW
            }
            R.id.intensity_duration_two -> {
                MEDIUM
            }
            R.id.intensity_duration_three -> {
                HIGH
            }
            else -> MEDIUM
        }
        vibrationUseCase.changeVibrationConfig(duration)
    }
}