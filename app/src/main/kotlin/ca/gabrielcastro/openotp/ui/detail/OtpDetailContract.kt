package ca.gabrielcastro.openotp.ui.detail

import ca.gabrielcastro.openotp.ui.base.BaseView


interface OtpDetailContract {

    interface View : BaseView {
        fun showIssuer(issuer: String)
        fun showAccountName(accountName: String)
        fun showCode(code: String)
        fun startEdit(id: String, issuer: String, account: String)
    }

    interface Presenter {
        fun init(view: View, id: String)
        fun edit()
        fun delete()
        fun resume()
        fun pause()
    }

}
