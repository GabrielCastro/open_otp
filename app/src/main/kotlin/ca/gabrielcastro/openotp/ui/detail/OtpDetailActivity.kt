package ca.gabrielcastro.openotp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.ext.ByteArrays
import ca.gabrielcastro.openotp.ext.start
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import ca.gabrielcastro.openotp.ui.edit.OtpEditActivity
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

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_otp_detail, menu)
        super.onPrepareOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> OtpEditActivity.intent(this, Totp(secret = ByteArrays.EMPTY, issuer = "Google", accountName = "example@gmail.com")).start(this)
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
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
