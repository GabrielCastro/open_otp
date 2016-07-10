package ca.gabrielcastro.openotp.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView


fun TextView.addAfterTextChangedListener(listener: (text: CharSequence) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            listener(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}
