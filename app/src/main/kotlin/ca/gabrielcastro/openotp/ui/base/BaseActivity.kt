package ca.gabrielcastro.openotp.ui.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity(), BaseView {

    var basePresenter: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("Activity").i(" onCreate: ${javaClass.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        basePresenter?.pause()
    }

    override fun onResume() {
        super.onResume()
        basePresenter?.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("Activity").i("onDestroy: ${javaClass.simpleName}")
    }

    override fun finish(ok: Boolean) {
        if (ok) {
            setResult(Activity.RESULT_OK)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }
}
