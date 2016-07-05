package ca.gabrielcastro.openotp.ext

import io.realm.Realm
import io.realm.RealmConfiguration

fun RealmConfiguration.useTx(txBlock: (realm: Realm) -> Unit) {
    val realm = Realm.getInstance(this)
    realm.use {
        it.executeTransaction {
            txBlock(it)
        }
    }
}
