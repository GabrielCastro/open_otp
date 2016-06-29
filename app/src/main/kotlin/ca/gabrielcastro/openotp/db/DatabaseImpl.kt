package ca.gabrielcastro.openotp.db

import android.content.Context
import ca.gabrielcastro.openotp.app.AppContext
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.rx.ioAndMain
import rx.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class DatabaseImpl @Inject constructor(
        private @param:AppContext val context: Context
) : Database {

    val items: MutableList<Totp> = Collections.synchronizedList(arrayListOf(
            Totp(issuer = "Google Inc.", accountName = "john@gmail.com", secret = "ABC".toByteArray()),
            Totp(issuer = "iCloud", accountName = "j.ive@apple.com", secret = "DEF".toByteArray()),
            Totp(issuer = "Steam", accountName = "gaben", secret = "GHI".toByteArray())
    ))

    override fun list(): Observable<List<Totp>> {
        return Observable.just(Collections.unmodifiableList(items))
                .ioAndMain()
    }

    override fun findById(id: String): Observable<Totp> {
        return Observable.fromCallable {
            val filtered = items.filter { it.uuid == id }
            return@fromCallable filtered[0]
        }
    }

    override fun add(totp: Totp): Observable<Unit> {
        items.add(totp)
        return Observable.just(Unit)
    }
}
