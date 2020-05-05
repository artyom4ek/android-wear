package os.wear.pulse.domain.usecase.vibration

import os.wear.pulse.data.repository.VibrationRepository
import os.wear.pulse.domain.common.IntensityDurationType
import os.wear.pulse.domain.common.IntensityType
import javax.inject.Inject


class VibrationUseCase
@Inject constructor(private val vibrationRepository: VibrationRepository) {
    val vibrationStatus = vibrationRepository.mVibrationStatus
    val intensityEnabled = vibrationRepository.mAmplitudeControlStatus

    fun changeVibrationConfig(type: IntensityDurationType) {
        vibrationRepository.changeIntensityDuration(type)
    }

    fun changeIntensityConfig(type: IntensityType) {
        vibrationRepository.changeIntensity(type)
    }
}