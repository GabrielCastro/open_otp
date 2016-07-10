package ca.gabrielcastro.openotp.ui.list

import android.support.annotation.DrawableRes


interface ListContract {

    interface View {
        fun showItems(items: List<ListItem>)
        fun showDetailForId(id: String)
        fun startScanning()
        fun showTemporaryMessage(text: CharSequence)
        fun showEmptyView(showEmpty: Boolean)
    }

    interface Presenter {
        fun init(view: View)
        fun resume()
        fun pause()
        fun itemSelected(item: ListItem)
        fun addNewTotp()
        fun invalidCodeScanned()
    }

    data class ListItem(
            val id: String,
            val issuer: String,
            val account: String,
            @DrawableRes
            val iconRes: Int
    )

}

