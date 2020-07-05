package ar.com.tallerdeprogramacion.Utils

import android.view.View
import android.widget.HorizontalScrollView

class BannerControl {

    fun hideBanner(horizontalScrollView: HorizontalScrollView){
        horizontalScrollView.visibility= View.GONE
    }
}