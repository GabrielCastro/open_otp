package ca.gabrielcastro.openotp

import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.rx.ioAndMain
import rx.Observable
import javax.inject.Inject

class FakeDatabase @Inject constructor() : Database {

    val items: List<Totp> = arrayListOf(
            Totp(issuer = "Google Inc.", accountName = "john@gmail.com", secret = "ABC".toByteArray()),
            Totp(issuer = "iCloud", accountName = "j.ive@apple.com", secret = "DEF".toByteArray()),
            Totp(issuer = "Steam", accountName = "gaben", secret = "GHI".toByteArray())
    )

    override fun list(): Observable<List<Totp>> {
        return Observable.just(items)
                .ioAndMain()
    }

    override fun findById(id: String): Observable<Totp> {
        return Observable.fromCallable {
            val filtered = items.filter { it.uuid == id }
            return@fromCallable filtered[0]
        }
    }

}
