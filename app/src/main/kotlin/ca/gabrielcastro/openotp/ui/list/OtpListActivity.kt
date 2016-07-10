package ca.gabrielcastro.openotp.ui.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.ext.start
import ca.gabrielcastro.openotp.ext.startForResult
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import ca.gabrielcastro.openotp.ui.detail.OtpDetailActivity
import ca.gabrielcastro.openotp.ui.scan.BarcodeScanActivity
import kotlinx.android.synthetic.main.opt_list_item_card_content.view.*
import kotlinx.android.synthetic.main.otp_list_activity.*
import kotlinx.android.synthetic.main.otp_list_item.view.*
import java.util.*
import javax.inject.Inject

class OtpListActivity : BaseActivity(), ListContract.View {

    companion object {
        const val SCAN_REQ_CODE = 11
    }

    @set:Inject
    lateinit var presenter: ListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_list_activity)
        App.component(this).listComponent.inject(this)
        presenter.init(this)

        otp_list_recycler.adapter = Adapter { item ->
            presenter.itemSelected(item)
        }
        otp_list_add.setOnClickListener {
            presenter.addNewTotp()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun showItems(items: List<ListContract.ListItem>) {
        val adapter = otp_list_recycler.adapter as Adapter
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    override fun showDetailForId(id: String) {
        OtpDetailActivity.intent(this, id).start(this)
    }

    override fun startScanning() {
        BarcodeScanActivity.intent(this).startForResult(this, SCAN_REQ_CODE)
    }

    override fun showTemporaryMessage(text: CharSequence) {
        val sb = Snackbar.make(main_view, text, Snackbar.LENGTH_LONG)
        sb.setAction("Try Again") {
            presenter.addNewTotp()
        }
        sb.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQ_CODE) {
            if (resultCode == BarcodeScanActivity.RESULT_INVALID_CODE) {
                presenter.invalidCodeScanned()
            }
        }
    }
}


private class Holder(
        parent: ViewGroup,
        val listener: (ListContract.ListItem) -> Unit
)
: RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.otp_list_item, parent, false)
) {

    init {
        itemView.otp_item_card.setOnClickListener {
            listener(item!!)
        }
    }

    var item: ListContract.ListItem? = null
        set(value) {
            field = value
            itemView.otp_item_issuer.text = field?.issuer
            itemView.otp_item_account.text = field?.account
        }

}

private class Adapter(val listener: (ListContract.ListItem) -> Unit) : RecyclerView.Adapter<Holder>() {

    var items: List<ListContract.ListItem> = Collections.emptyList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.item = items[position]
    }

}
