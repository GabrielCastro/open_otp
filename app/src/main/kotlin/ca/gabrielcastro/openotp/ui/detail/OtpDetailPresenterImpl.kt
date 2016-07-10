package ca.gabrielcastro.openotp.ui.detail

import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.model.observeCodeString
import ca.gabrielcastro.openotp.rx.ioAndMain
import rx.Observable
import rx.Subscription
import javax.inject.Inject

class OtpDetailPresenterImpl @Inject constructor(
        val database: Database
) : OtpDetailContract.Presenter {


    lateinit var id: String
    lateinit var view: OtpDetailContract.View
    private var sub: Subscription? = null
    private var totp: Totp? = null

    override fun init(view: OtpDetailContract.View, id: String) {
        this.id = id
        this.view = view
    }

    override fun edit() {
        val totp = this.totp ?: return
        view.startEdit(totp.uuid, totp.userIssuer, totp.userAccountName)
    }

    override fun delete() {
        database.delete(id)
            .ioAndMain()
            .subscribe {
                view.finish(it)
            }
    }

    override fun resume() {
        val findOb = database.findById(id).publish()
        findOb.subscribe {
            view.showAccountName(it?.userAccountName ?: "")
            view.showIssuer(it?.userIssuer ?: "")
            this.totp = it
        }
        findOb
                .flatMap {
                    (it?.observeCodeString() ?: Observable.empty())
                            .ioAndMain()
                }
                .ioAndMain()
                .subscribe {
                    view.showCode(it)
                }
        sub = findOb.connect()
    }

    override fun pause() {
        sub?.unsubscribe()
    }
}
