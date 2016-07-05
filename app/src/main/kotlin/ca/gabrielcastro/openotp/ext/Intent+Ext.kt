package ca.gabrielcastro.openotp.ext

import android.app.Activity
import android.content.Intent

fun Intent.start(activity: Activity) {
    activity.startActivity(this)
}

fun Intent.startForResult(activity: Activity, code: Int) {
    activity.startActivityForResult(this, code)
}
