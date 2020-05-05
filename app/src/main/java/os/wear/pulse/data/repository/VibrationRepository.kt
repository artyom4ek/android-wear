package os.wear.pulse.data.repository

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import os.wear.pulse.data.fileSystem.SharedPreferencesManager
import os.wear.pulse.data.repository.common.DefaultCountDownTimer
import os.wear.pulse.domain.common.IntensityDurationType
import os.wear.pulse.domain.common.IntensityType
import javax.inject.Inject


class VibrationRepository
@Inject constructor(
    private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val defaultCountDownTimer: DefaultCountDownTimer) {

    private val mVibrator = context.getSystemService(WearableActivity.VIBRATOR_SERVICE) as Vibrator
    private val mVibrationStatusSubject = BehaviorSubject.createDefault<Boolean>(false)
    val mVibrationStatus: Observable<Boolean>
        get() = mVibrationStatusSubject

    private var mCurrentIntensityDurationPattern = getVibrationPattern(IntensityDurationType.MEDIUM)

    val mAmplitudeControlStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mVibrator.hasAmplitudeControl()
    } else {
        false
    }

    private var mVibrationStatusForIntensity = false
    private var mCurrentIntensityValue = DEFAULT_INTENSITY_VALUE

    init {
        defaultCountDownTimer.mTimerStatus
            .doOnNext {
                mVibrationStatus
                if (it) startVibration()
                else cancelVibrator()
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    private fun startVibration() {
        mVibrationStatusForIntensity = true
        vibrate(mCurrentIntensityDurationPattern)
        mVibrationStatusSubject.onNext(true)
    }

    fun changeIntensityDuration(type: IntensityDurationType) {
        mCurrentIntensityDurationPattern = getVibrationPattern(type)
        vibrate(mCurrentIntensityDurationPattern)
    }

    fun changeIntensity(type: IntensityType) {
        when(type) {
            IntensityType.INCREASE -> {
                mCurrentIntensityValue += STEP_INTENSITY_VALUE
                if (mCurrentIntensityValue > MAX_INTENSITY_VALUE)
                    mCurrentIntensityValue = MAX_INTENSITY_VALUE
            }
            IntensityType.DECREASE -> {
                mCurrentIntensityValue -= STEP_INTENSITY_VALUE
                if (mCurrentIntensityValue < MIN_INTENSITY_VALUE)
                    mCurrentIntensityValue = MIN_INTENSITY_VALUE
            }
            else -> mCurrentIntensityValue
        }

        if (mVibrationStatusForIntensity) {
            mVibrator.cancel()
            vibrate(mCurrentIntensityDurationPattern)
        }
    }

    private fun vibrate(pattern: LongArray) {
        // 0 - delay; intensity_value; 0 = disabled
        val amplitudePattern = intArrayOf(0, mCurrentIntensityValue, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mVibrator.hasAmplitudeControl()) {
                mVibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudePattern, 0))
            } else {
                mVibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
            }

        } else {
            mVibrator.vibrate(pattern, 0)
        }
    }

    private fun cancelVibrator() {
        mVibrationStatusForIntensity = false
        mVibrator.cancel()
        mVibrationStatusSubject.onNext(false)
    }

    private fun getVibrationPattern(type: IntensityDurationType): LongArray {
        val vibrateTime: Long
        val vibratePause: Long
        val delay: Long = 0

        when(type) {
            IntensityDurationType.LOW -> {
                vibrateTime = VIBRATE_TIME_ONE
                vibratePause = VIBRATE_PAUSE_ONE
            }
            IntensityDurationType.MEDIUM -> {
                vibrateTime = VIBRATE_TIME_TWO
                vibratePause = VIBRATE_PAUSE_TWO
            }
            IntensityDurationType.HIGH -> {
                vibrateTime = VIBRATE_TIME_THREE
                vibratePause = VIBRATE_PAUSE_THREE
            }
        }

        return longArrayOf(delay, vibrateTime, vibratePause)
    }

    companion object {
        const val DEFAULT_INTENSITY_VALUE: Int = 120
        const val STEP_INTENSITY_VALUE: Int = 10
        const val MAX_INTENSITY_VALUE: Int = 255
        const val MIN_INTENSITY_VALUE: Int = 0

        const val VIBRATE_TIME_ONE: Long = 250
        const val VIBRATE_PAUSE_ONE: Long = 1750

        const val VIBRATE_TIME_TWO: Long = 250
        const val VIBRATE_PAUSE_TWO: Long = 2000

        const val VIBRATE_TIME_THREE: Long = 400
        const val VIBRATE_PAUSE_THREE: Long = 1750
    }
}