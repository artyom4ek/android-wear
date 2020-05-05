package os.wear.pulse.data.repository.common

import android.os.CountDownTimer
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


interface Timer {
    fun start()
    fun stop()
}

class DefaultCountDownTimer : Timer {

    private val mTimerStatusSubject = BehaviorSubject.createDefault<Boolean>(false)
    val mTimerStatus: Observable<Boolean>
        get() = mTimerStatusSubject

    private var mCountDownTimer: CountDownTimer? = null

    private fun initTimer() {
        mCountDownTimer = object : CountDownTimer(TIMER_MAX_VALUE, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                mTimerStatusSubject.onNext(false)
            }
        }
    }

    override fun start() {
        if (mCountDownTimer == null) initTimer()
        mCountDownTimer?.start()
        mTimerStatusSubject.onNext(true)
    }

    override fun stop() {
        mTimerStatusSubject.onNext(false)
        mCountDownTimer?.cancel()
    }

    companion object {
        // 20 - minute, 60 - seconds, 1000 - milliseconds
        const val TIMER_MAX_VALUE: Long = 20 * 60 * 1000
    }
}