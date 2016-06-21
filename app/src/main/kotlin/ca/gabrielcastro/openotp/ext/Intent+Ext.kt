package ca.gabrielcastro.openotp.ext

import android.app.Activity
import android.content.Intent

fun Intent.start(activity: Activity) {
    activity.startActivity(this)
}
