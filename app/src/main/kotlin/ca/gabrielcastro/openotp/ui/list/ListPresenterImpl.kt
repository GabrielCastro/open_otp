package ca.gabrielcastro.openotp.ui.list

import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.rx.ioAndMain
import rx.Subscription
import timber.log.Timber
import javax.inject.Inject


internal class ListPresenterImpl @Inject constructor(
        val database: Database
) : ListContract.Presenter {

    lateinit var view: ListContract.View

    var listSub: Subscription? = null

    override fun init(view: ListContract.View) {
        this.view = view
    }

    override fun pause() {
        listSub?.unsubscribe()
    }

    override fun resume() {
        listSub = database.list()
                .ioAndMain()
                .map {
                    it.map { ListContract.ListItem(it.uuid, it.userIssuer, it.userAccountName) }
                }
                .subscribe {
                    view.showItems(it)
                }
    }

    override fun itemSelected(item: ListContract.ListItem) {
        Timber.i("selected totp: $item")
        view.showDetailForId(item.id)
    }

    override fun addNewTotp() {
        Timber.i("add new totp clicked")
        view.startScanning()
    }

    override fun invalidCodeScanned() {
        Timber.i("invalid code scanned")
        view.showTemporaryMessage("Invalid Code Scanned")
    }

}
