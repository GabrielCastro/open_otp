package ca.gabrielcastro.openotp.ui.list

import ca.gabrielcastro.openotp.R
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
        this.view.showEmptyView(false)
    }

    override fun pause() {
        listSub?.unsubscribe()
    }

    override fun resume() {
        listSub = database.list()
                .ioAndMain()
                .map {
                    it.map {
                        val iconRes = iconForIssuer(it.userIssuer)
                                ?: iconForIssuer(it.issuer)
                                ?: R.drawable.issuer_default_36
                        ListContract.ListItem(it.uuid, it.userIssuer, it.userAccountName, iconRes)
                    }
                }
                .subscribe {
                    view.showItems(it)
                    this.view.showEmptyView(it.size == 0)
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

val iconMap = listOf(
        Regex(".*Google.*", RegexOption.IGNORE_CASE) to R.drawable.issuer_google_36,
        Regex(".*Slack.*", RegexOption.IGNORE_CASE) to R.drawable.issuer_slack_36
)

fun iconForIssuer(issuerName: String) : Int? {
    return iconMap.find { it.first.matches(issuerName) }?.second
}
