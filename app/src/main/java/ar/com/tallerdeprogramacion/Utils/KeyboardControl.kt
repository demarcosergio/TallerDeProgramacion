package ar.com.tallerdeprogramacion.Utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class KeyboardControl {
    fun hideKeyboard(activity: Activity?) {
        val imm: InputMethodManager = activity!!
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null && activity != null) {
            val currentFocus = activity.currentFocus
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
    }
}