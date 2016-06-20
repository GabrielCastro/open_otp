package ca.gabrielcastro.openotp.ui.list

import android.os.Bundle
import ca.gabrielcastro.openotp.R
import ca.gabrielcastro.openotp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.otp_list_activity.*

class OtpListActivity : BaseActivity(), ListContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_list_activity)
    }

    override fun showItems(items: List<ListContract.ListItem>) {
        val str = items.map { it.toString() }
                .joinToString("\n\t")
        textView.text = str
    }

}
