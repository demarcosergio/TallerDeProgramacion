package ar.com.tallerdeprogramacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ar.com.tallerdeprogramacion.databinding.ActivityProductDetailBinding
import ar.com.tallerdeprogramacion.retrofit.Product
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var textViewTitle: TextView
    private lateinit var textViewProductState : TextView
    private lateinit var imageViewProductDetail: ImageView
    private lateinit var textViewPrice: TextView
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(LayoutInflater.from(this@ProductDetailActivity))
        setContentView(binding.root)
        initViews()
        mapDataOnViews()
        val searchview = findViewById<SearchView>(R.id.searchView)
        searchview.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //onSearch(query)
                    val intent = Intent(this@ProductDetailActivity,MainActivity::class.java)
                    intent.putExtra(getString(R.string.postQueryBoolean),true)
                    intent.putExtra(getString(R.string.postQueryText),query)
                    startActivity(intent)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
    }

    private fun mapDataOnViews() {
        val product= intent.getSerializableExtra("product") as Product
        val productTitle= this.textViewTitle
        productTitle.text=product.title
        val productState= textViewProductState
        if(product.condition == "new") productState.text = getString(R.string.estadoNuevo) else productState.text = getString(
                    R.string.estadoUsado) +product.soldQuantity+getString(R.string.Vendidos)
        val productPrice=textViewPrice
        productPrice.text= """$ ${product.price}"""

        Picasso.get()
            .load(product.imageUrl)
            .error(R.drawable.error_image)
            .into(imageViewProductDetail)
    }

    private fun initViews() {
        imageViewProductDetail=binding.imageViewProductDetail
        textViewProductState=binding.textViewProductState
        textViewTitle=binding.textViewTitle
        textViewPrice=binding.textViewPrice
    }
}
