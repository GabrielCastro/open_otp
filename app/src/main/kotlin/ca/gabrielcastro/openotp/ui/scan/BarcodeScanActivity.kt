package ca.gabrielcastro.openotp.ui.scan

import android.content.Context
import android.content.Intent
import ca.gabrielcastro.openotp.ui.base.BaseActivity

class BarcodeScanActivity : BaseActivity() {
    companion object {
        fun intent(context: Context) : Intent {
            return Intent(context, BarcodeScanActivity::class.java)
        }
    }
}
