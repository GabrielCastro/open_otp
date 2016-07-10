package ca.gabrielcastro.openotp.db

import android.content.Context
import ca.gabrielcastro.openotp.app.AppContext
import ca.gabrielcastro.openotp.ext.ByteArrays
import ca.gabrielcastro.openotp.ext.useTx
import ca.gabrielcastro.openotp.model.Algorithm
import ca.gabrielcastro.openotp.model.Algorithms
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.rx.ioAndMain
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class DatabaseImpl @Inject constructor(
        private @param:AppContext val context: Context
) : Database {

    var realmConfig: RealmConfiguration

    init {
        val builder = RealmConfiguration.Builder(context)
                .schemaVersion(1)
        if (BuildConfig.DEBUG) {
            builder.deleteRealmIfMigrationNeeded()
        }
        realmConfig = builder.build()
    }

    override fun list(): Observable<List<Totp>> {
        val realm = Realm.getInstance(realmConfig)
        return realm.where(TotpEntry::class.java)
                .findAllAsync()
                .asObservable()
                .map { it.map { it.toModel() } }
                .doOnUnsubscribe {
                    realm.close()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
    }


    override fun findById(id: String): Observable<Totp?> {
        val realm = Realm.getInstance(realmConfig)
        return realm.where(TotpEntry::class.java)
                .equalTo("uuid", id)
                .findAllAsync()
                .asObservable()
                .map { if (it.size > 0) it[0] else null }
                .map { it?.toModel() }
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun add(totp: Totp): Observable<Unit> {
        Timber.i("add: $totp")
        return Observable.fromCallable {
            realmConfig.useTx {
                val entry = TotpEntry.make(totp)
                it.copyToRealmOrUpdate(entry)
            }
            return@fromCallable Unit
        }.ioAndMain()
    }

    override fun update(id: String, newUserAccountName: String, newUserIssuer: String): Observable<Boolean> {
        Timber.i("update: id: $id, newUserAccountName: $newUserAccountName newUserIssuer: $newUserIssuer")
        return Observable.fromCallable {
            var found = false
            realmConfig.useTx {
                val one = it.where(TotpEntry::class.java)
                    .equalTo("uuid", id)
                    .findFirst() ?: return@useTx
                found = true
                one.userAccountName = newUserAccountName
                one.userIssuer = newUserIssuer
            }
            if (!found) {
                Timber.w("couldn't update $id")
            }
            return@fromCallable found
        }
    }
}

open class TotpEntry(
        @PrimaryKey
        var uuid: String = UUID.randomUUID().toString(),
        var issuer: String = "",
        var userIssuer: String = issuer,
        var accountName: String = "",
        var userAccountName: String = accountName,
        @Algorithm
        var algorithm: String = Algorithms.SHA1,
        var secret: ByteArray = ByteArrays.EMPTY,
        var digits: Int = 6,
        var period: Int = 30
) : RealmObject() {

    companion object {
        fun make(model: Totp): TotpEntry = TotpEntry(
                uuid = model.uuid,
                issuer = model.issuer,
                userIssuer = model.userIssuer,
                accountName = model.accountName,
                userAccountName = model.userAccountName,
                algorithm = model.algorithm,
                secret = model.secret.copyOf(),
                digits = model.digits,
                period = model.period
        )

    }

    fun toModel() = Totp(
            uuid = uuid,
            issuer = issuer,
            userIssuer = userIssuer,
            accountName = accountName,
            userAccountName = userAccountName,
            algorithm = algorithm,
            secret = secret.copyOf(),
            digits = digits,
            period = period
    )
}
