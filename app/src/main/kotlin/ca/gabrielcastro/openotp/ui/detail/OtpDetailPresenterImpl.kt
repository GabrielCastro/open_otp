package ca.gabrielcastro.openotp.ui.detail

import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.model.observeCodeString
import ca.gabrielcastro.openotp.rx.ioAndMain
import rx.Subscription
import java.util.*
import javax.inject.Inject

class OtpDetailPresenterImpl @Inject constructor(
        val database: Database
) : OtpDetailContract.Presenter {

    lateinit var id: String
    lateinit var view: OtpDetailContract.View
    private var sub: Subscription? = null
    private var sub2: Subscription? = null

    override fun init(view: OtpDetailContract.View, id: String) {
        this.id = id
        this.view = view
    }

    override fun resume() {
        sub = database.findById(id)
                .subscribe {
                    view.showAccountName(it.userAccountName)
                    view.showIssuer(it.userIssuer)
                }
        sub2 = database.findById(id)
                .flatMap {
                    it.observeCodeString()
                            .ioAndMain()
                }
                .ioAndMain()
                .subscribe {
                    view.showCode(it)
                }
    }

    override fun pause() {
        sub?.unsubscribe()
        sub2?.unsubscribe()
    }
}
