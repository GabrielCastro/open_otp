package ca.gabrielcastro.openotp.ui.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ca.gabrielcastro.openotp.model.totpFrom
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import com.google.zxing.integration.android.IntentIntegrator
import timber.log.Timber

class BarcodeScanActivity : BaseActivity() {
    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, BarcodeScanActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Scan the verification code")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        finish()
        if (data == null) {
            return
        }
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data) ?: return
        val contents = result.contents ?: return
        val totp = totpFrom(contents) ?: return
        Timber.i("Found totp: $totp")
    }

}
