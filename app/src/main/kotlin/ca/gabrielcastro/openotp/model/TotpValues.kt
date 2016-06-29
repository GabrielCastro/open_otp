package ca.gabrielcastro.openotp.model

import rx.Observable
import rx.Scheduler
import rx.internal.operators.OnSubscribeTimerPeriodically
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

fun Totp.observeCode() : Observable<Long> {
    val now = System.currentTimeMillis()
    val interval = now / (1000 * period)

    val endOfPeriod = period * 1000 * (interval + 1)
    val timeToNext = endOfPeriod - now

    val sub = OnSubscribeTimerPeriodically(timeToNext, (period * 1000).toLong(), TimeUnit.MILLISECONDS, Schedulers.computation())

    return Observable.create<Long>(sub)
        .startWith(0L)
        .map { this.calculateCode() }
        .distinctUntilChanged()
}

fun Totp.observeCodeString() : Observable<String> {
    return observeCode()
        .map { String.format(Locale.CANADA, "%0${digits}d", it)}
}
