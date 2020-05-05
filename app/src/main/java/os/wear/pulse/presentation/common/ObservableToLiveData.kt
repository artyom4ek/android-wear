package os.wear.pulse.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import os.wear.pulse.domain.entities.Optional


object ObservableToLiveData {

    fun <T> mapWithOptionalUnwrap(observable: Observable<Optional<T>>): LiveData<T?> {
        return Transformations.map(
            LiveDataReactiveStreams.fromPublisher(observable.toFlowable(BackpressureStrategy.BUFFER)))
        { it.value }
    }

    fun <T> map(observable: Observable<T>): LiveData<T> {
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.BUFFER))
    }
}
