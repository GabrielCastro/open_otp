package ca.gabrielcastro.openotp.ui.list

import android.os.Bundle
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.app.App
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.otp_list_activity.*
import javax.inject.Inject

class OtpListActivity : BaseActivity(), ListContract.View {

    @set:Inject
    lateinit var presenter: ListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_list_activity)
        App.component(this).listComponent.inject(this)
        presenter.init(this)
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
        val str = items.map { it.toString() }
                .joinToString("\n\n\t")
        textView.text = str
    }

}
