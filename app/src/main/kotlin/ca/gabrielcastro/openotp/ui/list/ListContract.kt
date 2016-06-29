package ca.gabrielcastro.openotp.ui.list

interface ListContract {

    interface View {
        fun showItems(items: List<ListItem>)
        fun showDetailForId(id: String)
        fun startScanning()
    }

    interface Presenter {
        fun init(view: View)
        fun resume()
        fun pause()
        fun itemSelected(item: ListItem)
        fun addNewTotp()
    }

    data class ListItem(val id: String, val issuer: String, val account: String)

}

