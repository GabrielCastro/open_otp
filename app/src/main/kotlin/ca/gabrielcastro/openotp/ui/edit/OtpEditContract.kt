package ca.gabrielcastro.openotp.ui.edit

import ca.gabrielcastro.openotp.ui.base.BasePresenter


interface OtpEditContract {

    interface View {
        fun setIssuer(issuer :CharSequence)
        fun setAccountName(accountName :CharSequence)
    }

    interface Presenter : BasePresenter {
        fun init(view: View, id: String, initialIssuer: String, initialAccount: String)
        fun issuerChanged(newIssuer :CharSequence)
        fun accountNameChanged(newAccountName :CharSequence)
    }

}
