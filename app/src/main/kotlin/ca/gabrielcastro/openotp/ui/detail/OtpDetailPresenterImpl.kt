package ca.gabrielcastro.openotp.ui.detail

import ca.gabrielcastro.openotp.db.Database
import rx.Subscription
import java.util.*
import javax.inject.Inject

class OtpDetailPresenterImpl @Inject constructor(
        val database: Database
) : OtpDetailContract.Presenter {

    lateinit var id: String
    lateinit var view: OtpDetailContract.View
    var sub: Subscription? = null

    override fun init(view: OtpDetailContract.View, id: String) {
        this.id = id
        this.view = view
    }

    override fun resume() {
        sub = database.findById(id)
            .subscribe {
                view.showAccountName(it.userAccountName)
                view.showIssuer(it.userIssuer)
                view.showCode(String.format(Locale.CANADA, "%0${it.digits}d", it.calculateCode()))
            }
    }

    override fun pause() {
        sub?.unsubscribe()
    }
}
