package ca.gabrielcastro.openotp.ui.detail

import javax.inject.Inject

class OtpDetailPresenterImpl @Inject constructor() : OtpDetailContract.Presenter {

    override fun init(view: OtpDetailContract.View, id: String) {
        view.showAccountName("account Name")
        view.showIssuer("Issuer")
        view.showCode("123 456")
    }

    override fun resume() {
    }

    override fun pause() {
    }
}
