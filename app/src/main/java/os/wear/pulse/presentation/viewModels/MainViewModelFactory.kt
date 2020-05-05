package os.wear.pulse.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import os.wear.pulse.data.fileSystem.SharedPreferencesManager
import os.wear.pulse.domain.usecase.timer.TimerUseCase
import os.wear.pulse.domain.usecase.vibration.VibrationUseCase


class ViewModelFactory(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val timerUseCase: TimerUseCase,
    private val vibrationUseCase: VibrationUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(
                sharedPreferencesManager,
                timerUseCase,
                vibrationUseCase
            )
            else -> throw IllegalArgumentException("Unknown class: ${modelClass.simpleName}")
        } as T
    }
}