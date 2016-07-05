package ca.gabrielcastro.openotp.ui.scan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.model.Totp
import ca.gabrielcastro.openotp.model.totpFrom
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import com.google.zxing.integration.android.IntentIntegrator
import timber.log.Timber
import javax.inject.Inject

class BarcodeScanActivity : BaseActivity() {
    companion object {
        const val RESULT_INVALID_CODE = Activity.RESULT_FIRST_USER + 1
        fun intent(context: Context): Intent {
            return Intent(context, BarcodeScanActivity::class.java)
        }
    }

    @Inject
    lateinit var presenter: ScanContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)
        App.component(this).scanComponent.inject(this)
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Scan the verification code")
        integrator.initiateScan()
    }

    private fun totpForData(requestCode: Int, resultCode: Int, data: Intent?): Totp? {
        if (data == null) {
            return null
        }
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data) ?: return null
        val contents = result.contents ?: return null
        return totpFrom(contents)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val totp = totpForData(requestCode, resultCode, data)
        if (totp != null) {
            Timber.e("Found totp: $totp")
            presenter.addTotp(totp)
            setResult(RESULT_OK)
        } else {
            Timber.e("Invalid totp")
            presenter.invalidScan()
            setResult(RESULT_INVALID_CODE)
        }
        finish()
    }

}
