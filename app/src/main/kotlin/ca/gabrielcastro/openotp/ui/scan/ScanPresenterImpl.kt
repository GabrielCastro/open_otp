package ca.gabrielcastro.openotp.ui.scan

import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.model.Totp
import javax.inject.Inject


internal class ScanPresenterImpl @Inject constructor(
        val database: Database
) : ScanContract.Presenter {

    override fun addTotp(totp: Totp) {
        database.add(totp)
                .subscribe()
    }

    override fun invalidScan() {
        // TODO
    }

}
