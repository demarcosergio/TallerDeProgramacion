package ar.com.tallerdeprogramacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import ar.com.tallerdeprogramacion.retrofit.Product
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    lateinit var textViewTitle: TextView
    lateinit var textViewProductState : TextView
    lateinit var imageViewProductDetail: ImageView
    lateinit var textViewPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        InitViews()
        MapDataOnViews()
    }

    private fun MapDataOnViews() {
        val product= intent.getSerializableExtra("product") as Product
        val productTitle= this.textViewTitle
        productTitle.text=product.title
        val productState= textViewProductState
        productState.text = if(product.condition == "new") "Nuevo " else "Usado " +product.soldQuantity+" Vendidos"
        val productPrice=textViewPrice
        productPrice.text="$ "+product.price

        Picasso.get()
            .load(product.imageUrl)
            .error(R.drawable.error_image)
            .into(imageViewProductDetail)
    }

    private fun InitViews() {
        imageViewProductDetail=findViewById(R.id.imageViewProductDetail)
        textViewProductState=findViewById(R.id.textViewProductState)
        textViewTitle=findViewById(R.id.textViewTitle)
        textViewPrice=findViewById(R.id.textViewPrice)

    }
}
