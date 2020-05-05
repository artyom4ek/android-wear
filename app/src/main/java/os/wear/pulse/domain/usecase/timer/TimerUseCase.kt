package os.wear.pulse.domain.usecase.timer

import os.wear.pulse.data.repository.common.DefaultCountDownTimer
import javax.inject.Inject


class TimerUseCase
@Inject constructor(private val defaultCountDownTimer: DefaultCountDownTimer) {

    fun startTimer() {
        defaultCountDownTimer.start()
    }

    fun stopTimer() {
        defaultCountDownTimer.stop()
    }
}