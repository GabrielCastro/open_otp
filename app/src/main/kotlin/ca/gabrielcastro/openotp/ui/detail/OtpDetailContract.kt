package ca.gabrielcastro.openotp.ui.detail


interface OtpDetailContract {

    interface View {
        fun showIssuer(issuer: String)
        fun showAccountName(accountName: String)
        fun showCode(code: String)
    }

    interface Presenter {
        fun init(view: View, id: String)
        fun resume()
        fun pause()
    }

}
