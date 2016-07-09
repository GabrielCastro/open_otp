package ca.gabrielcastro.openotp.ui.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_otp_edit.*

class OtpEditActivity : BaseActivity() {

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_ACCOUNT = "account"
        const val EXTRA_ISSUER = "issuer"
        fun intent(context: Context, totp: Totp): Intent {
            return Intent(context, OtpEditActivity::class.java)
                    .putExtra(EXTRA_ACCOUNT, totp.userAccountName)
                    .putExtra(EXTRA_ISSUER, totp.userIssuer)
                    .putExtra(EXTRA_ID, totp.uuid)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_edit)

        val account = intent.getStringExtra(EXTRA_ACCOUNT)
        val issuer = intent.getStringExtra(EXTRA_ISSUER)

        otp_edit_account.setText(account)
        otp_edit_issuer.setText(issuer)

    }


}
