package ca.gabrielcastro.openotp.db

import ca.gabrielcastro.openotp.model.Totp
import rx.Observable


interface Database {
    fun list() : Observable<List<Totp>>
    fun findById(id: String) : Observable<Totp?>
    fun add(totp: Totp) : Observable<Unit>
    fun update(id: String, newUserAccountName: String, newUserIssuer: String) : Observable<Boolean>
}
