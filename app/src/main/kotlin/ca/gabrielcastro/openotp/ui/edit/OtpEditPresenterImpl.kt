package ca.gabrielcastro.openotp.ui.edit

import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.rx.ioAndMain
import ca.gabrielcastro.openotp.ui.base.BasePresenterImpl
import javax.inject.Inject

class OtpEditPresenterImpl @Inject constructor(
        val database: Database
) : BasePresenterImpl(), OtpEditContract.Presenter {

    lateinit var view: OtpEditContract.View
    lateinit var id: String
    lateinit var issuer: String
    lateinit var account: String

    override fun init(view: OtpEditContract.View, id: String, initialIssuer: String, initialAccount: String) {
        this.view = view
        this.id = id
        this.issuer = initialIssuer
        this.account = initialAccount
        view.setAccountName(initialAccount)
        view.setIssuer(initialIssuer)
    }

    override fun issuerChanged(newIssuer: CharSequence) {
        issuer = newIssuer.toString()
    }

    override fun accountNameChanged(newAccountName: CharSequence) {
        account = newAccountName.toString()
    }

    override fun save() {
        database.update(id, account, issuer)
            .ioAndMain()
            .subscribe {
                view.finish(true)
            }
    }
}
