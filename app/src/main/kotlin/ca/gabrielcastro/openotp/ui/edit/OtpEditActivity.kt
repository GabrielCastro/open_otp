package ca.gabrielcastro.openotp.ui.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.ext.addAfterTextChangedListener
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_otp_edit.*
import javax.inject.Inject

class OtpEditActivity : BaseActivity(), OtpEditContract.View {

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_ACCOUNT = "account"
        const val EXTRA_ISSUER = "issuer"
        fun intent(context: Context, id: String, account: String, issuer: String): Intent {
            return Intent(context, OtpEditActivity::class.java)
                    .putExtra(EXTRA_ACCOUNT, account)
                    .putExtra(EXTRA_ISSUER, issuer)
                    .putExtra(EXTRA_ID, id)
        }
    }

    @Inject
    lateinit var presenter: OtpEditContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_edit)
        App.component(this).editComponent.inject(this)
        val id = intent.getStringExtra(EXTRA_ID)!!
        val account = intent.getStringExtra(EXTRA_ACCOUNT) ?: ""
        val issuer = intent.getStringExtra(EXTRA_ISSUER) ?: ""
        basePresenter = presenter
        presenter.init(this, id, issuer, account)

        otp_edit_issuer.addAfterTextChangedListener {
            presenter.issuerChanged(it)
        }
        otp_edit_account.addAfterTextChangedListener {
            presenter.accountNameChanged(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_otp_edit, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> presenter.save()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun setIssuer(issuer: CharSequence) {
        otp_edit_issuer.setText(issuer)
    }

    override fun setAccountName(accountName: CharSequence) {
        otp_edit_account.setText(accountName)
    }

}
