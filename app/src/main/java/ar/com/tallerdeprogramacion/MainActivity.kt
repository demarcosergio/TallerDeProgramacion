package ar.com.tallerdeprogramacion

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_detail_product.*

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById<View>(R.id.imageViewLogo) as ImageView
        val button = findViewById<Button>(R.id.search_button)
        button.setOnClickListener{
            val intent = Intent(this, DetailProductActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView,
                ViewCompat.getTransitionName(imageView).toString()
            )
            startActivity(intent,options.toBundle())
            //startActivity(intent)
        }
    }
}
