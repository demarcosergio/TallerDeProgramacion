package ar.com.tallerdeprogramacion.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.tallerdeprogramacion.R
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val clickListener:(Product)-> Unit
) : RecyclerView.Adapter<ProductViewHolder>(){
    private val productList = mutableListOf<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productView= LayoutInflater.from(parent.context).inflate(R.layout.activity_product_list, parent, false)
        return  ProductViewHolder(productView)
    }

    override fun getItemCount()= productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.title.text = product.title
        holder.price.text = """$ ${product.price}"""
        holder.legend.text = if(product.condition == "new") "Nuevo" else "Usado"
        holder.description.text =  product.id

        Picasso.get()
            .load(product.imageUrl)
            .error(R.drawable.error_image)
            .fit().centerCrop()
            .into(holder.cover)


        holder.itemView.setOnClickListener{
            clickListener(product)
        }

    }

    fun updateProducts(results: List<Product>){
        productList.clear()
        productList.addAll(results)
    }

}

class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){
    val title : TextView = view.findViewById(R.id.title)
    val description : TextView = view.findViewById(R.id.description)
    val price : TextView = view.findViewById(R.id.price)
    val legend : TextView = view.findViewById(R.id.legend)
    val cover: ImageView = view.findViewById(R.id.imageProduct)
}