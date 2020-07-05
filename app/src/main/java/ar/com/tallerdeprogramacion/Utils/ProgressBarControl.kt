package ar.com.tallerdeprogramacion.Utils

import android.view.View
import android.widget.ProgressBar

class ProgressBarControl {
    fun hideProgressBar(progressBar: ProgressBar){
        progressBar.visibility= View.GONE
    }
    fun showProgressBar(progressBar: ProgressBar){
        progressBar.visibility= View.VISIBLE
    }
}