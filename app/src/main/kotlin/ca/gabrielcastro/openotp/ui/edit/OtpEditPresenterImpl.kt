package ca.gabrielcastro.openotp.ui.edit

import ca.gabrielcastro.openotp.ui.base.BasePresenterImpl
import javax.inject.Inject

class OtpEditPresenterImpl @Inject constructor() : BasePresenterImpl(), OtpEditContract.Presenter {

    lateinit var view: OtpEditContract.View
    lateinit var id: String
    lateinit var initialIssuer: String
    lateinit var initialAccount: String

    override fun init(view: OtpEditContract.View, id: String, initialIssuer: String, initialAccount: String) {
        this.view = view
        this.id = id
        this.initialIssuer = initialIssuer
        this.initialAccount = initialAccount
        view.setAccountName(initialAccount)
        view.setIssuer(initialIssuer)
    }

    override fun issuerChanged(newIssuer: CharSequence) {
    }

    override fun accountNameChanged(newAccountName: CharSequence) {
    }

}
