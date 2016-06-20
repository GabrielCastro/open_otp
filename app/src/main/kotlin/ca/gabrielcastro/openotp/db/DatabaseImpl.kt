package ca.gabrielcastro.openotp.db

import ca.gabrielcastro.openotp.model.Totp
import rx.Observable


internal class DatabaseImpl : Database {

    val items: List<Totp> = arrayListOf(
            Totp(issuer = "Google Inc.", accountName = "john@gmail.com", secret = "ABC".toByteArray()),
            Totp(issuer = "iCloud", accountName = "j.ive@apple.com", secret = "DEF".toByteArray()),
            Totp(issuer = "Steam", accountName = "gaben", secret = "GHI".toByteArray())
    )

    override fun list(): Observable<List<Totp>> {
        return Observable.just(items)
    }

}
