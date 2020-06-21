package ar.com.tallerdeprogramacion

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.tallerdeprogramacion.retrofit.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var productService : RetrofitProductService
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependencies()
        setSearchViewListener()
        setupRecyclerView()
    }

    private fun injectDependencies() {
        productService = RetrofitProductService()
        productsAdapter = ProductsAdapter{product ->
            val intent = Intent(this@MainActivity,ProductDetailActivity::class.java)
            intent.putExtra("product",product)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProductList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = productsAdapter
    }

    private fun setSearchViewListener() {
        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    onSearch(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
    }
    private fun onSearch(query: String?){
        query?.run {
            productService.search(query)
                .enqueue(object : Callback<Products> {
                    override fun onResponse(call: Call<Products>, response: Response<Products>) {
                       if(response.isSuccessful){
                           //Log.d("PRODUCTOS",response.body()!!.toString())
                           val products = response.body()!!
                           productsAdapter.updateProducts(products.results)
                           productsAdapter.notifyDataSetChanged()
                       }
                        else{
                           showError()
                       }
                    }

                    override fun onFailure(call: Call<Products>?, t: Throwable?) {
                        showError()
                    }
                })
        }
    }

    private fun showError() {
        Toast.makeText(this@MainActivity, "Hubo un error", Toast.LENGTH_SHORT).show()
    }
}
