package ar.com.tallerdeprogramacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.tallerdeprogramacion.retrofit.Products
import ar.com.tallerdeprogramacion.retrofit.ProductsAdapter
import ar.com.tallerdeprogramacion.retrofit.RetrofitProductService
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
                           HideBanner()
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
    private fun HideBanner(){
       val scrollView= findViewById<HorizontalScrollView>(R.id.scrollView)
        scrollView.visibility=View.GONE
    }
}
