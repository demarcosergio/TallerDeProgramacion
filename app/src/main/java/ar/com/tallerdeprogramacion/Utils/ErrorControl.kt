package ar.com.tallerdeprogramacion.Utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ar.com.tallerdeprogramacion.R

class ErrorControl {
    fun showError(title: String, message: String, context: Context) {
        val alertDialogBuilder =  AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setIcon(R.drawable.error_image)
        alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(context,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.show()
    }
}