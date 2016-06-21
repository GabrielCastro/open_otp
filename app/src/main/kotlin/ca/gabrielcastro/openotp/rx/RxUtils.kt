package ca.gabrielcastro.openotp.rx

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


fun <T> Observable<T>.ioAndMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
