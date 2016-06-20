package ca.gabrielcastro.openotp.rx

import rx.Observable
import rx.schedulers.Schedulers


fun <T> Observable<T>.ioAndMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
}
