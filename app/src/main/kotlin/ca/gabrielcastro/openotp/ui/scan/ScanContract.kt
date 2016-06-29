package ca.gabrielcastro.openotp.ui.scan

import ca.gabrielcastro.openotp.model.Totp

interface ScanContract {
    interface Presenter {
        fun addTotp(totp: Totp)
        fun invalidScan()
    }
}
