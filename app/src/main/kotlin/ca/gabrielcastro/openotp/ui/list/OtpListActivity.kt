package ca.gabrielcastro.openotp.ui.list

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.otp_list_activity.*
import kotlinx.android.synthetic.main.otp_list_item.view.*
import java.util.*
import javax.inject.Inject

class OtpListActivity : BaseActivity(), ListContract.View {

    @set:Inject
    lateinit var presenter: ListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_list_activity)
        App.component(this).listComponent.inject(this)
        presenter.init(this)

        otp_list_recycler.adapter = Adapter()
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

}

private class Holder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.otp_list_item, parent, false)
) {

    init {
        itemView.otp_item_card.setOnClickListener {
            //
        }
    }

    var item: ListContract.ListItem? = null
        set(value) {
            field = value
            itemView.otp_item_issuer.text = field?.issuer
            itemView.otp_item_account.text = field?.account
        }

}

private class Adapter : RecyclerView.Adapter<Holder>() {

    var items: List<ListContract.ListItem> = Collections.emptyList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.item = items[position]
    }

}
