package ar.com.tallerdeprogramacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import ar.com.tallerdeprogramacion.retrofit.Product
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var textViewTitle: TextView
    private lateinit var textViewProductState : TextView
    private lateinit var imageViewProductDetail: ImageView
    private lateinit var textViewPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        initViews()
        mapDataOnViews()
    }

    private fun mapDataOnViews() {
        val product= intent.getSerializableExtra("product") as Product
        val productTitle= this.textViewTitle
        productTitle.text=product.title
        val productState= textViewProductState
        if(product.condition == "new") productState.text = "Nuevo " else productState.text = "Usado " +product.soldQuantity+" Vendidos"
        val productPrice=textViewPrice
        productPrice.text= """$ ${product.price}"""

        Picasso.get()
            .load(product.imageUrl)
            .error(R.drawable.error_image)
            .into(imageViewProductDetail)
    }

    private fun initViews() {
        imageViewProductDetail=findViewById(R.id.imageViewProductDetail)
        textViewProductState=findViewById(R.id.textViewProductState)
        textViewTitle=findViewById(R.id.textViewTitle)
        textViewPrice=findViewById(R.id.textViewPrice)

    }
}
