package ca.gabrielcastro.openotp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_otp_detail.*
import timber.log.Timber
import javax.inject.Inject

class OtpDetailActivity : BaseActivity(), OtpDetailContract.View {

    companion object {
        const val EXTRA_ID = "id"
        fun intent(context: Context, id: String) =
                Intent(context, OtpDetailActivity::class.java)
                    .putExtra(EXTRA_ID, id)
    }

    val id: String
        get() = intent.getStringExtra(EXTRA_ID)!!

    @set:Inject
    lateinit var presenter: OtpDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component(this).detailComponent.inject(this)
        setContentView(R.layout.activity_otp_detail)
        presenter.init(this, id)
    }

    override fun showIssuer(issuer: String) {
        otp_detail_issuer.text = issuer
    }

    override fun showAccountName(accountName: String) {
        otp_detail_account.text = accountName
    }

    override fun showCode(code: String) {
        otp_detail_code.text = code
    }

}
