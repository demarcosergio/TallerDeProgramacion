package ar.com.tallerdeprogramacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.tallerdeprogramacion.Utils.*
import ar.com.tallerdeprogramacion.databinding.ActivityMainBinding
import ar.com.tallerdeprogramacion.retrofit.Products
import ar.com.tallerdeprogramacion.retrofit.ProductsAdapter
import ar.com.tallerdeprogramacion.retrofit.RetrofitProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var productService : RetrofitProductService
    private lateinit var productsAdapter: ProductsAdapter

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        injectDependencies()
        val searchFromOtherActivity = intent.extras?.getBoolean(getString(R.string.postQueryBoolean))
        if(searchFromOtherActivity!=null && searchFromOtherActivity){
            onSearch(intent.extras!!.getString(getString(R.string.postQueryText)))
        }
        else{setSearchViewListener()}

        setupRecyclerView()
        ProgressBarControl().hideProgressBar(binding.progressBar)
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
        binding.recyclerViewProductList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewProductList.adapter = productsAdapter
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
        if(NetworkControl().isActiveNetwork(this@MainActivity)){
            query?.run {
                ProgressBarControl().showProgressBar(binding.progressBar)
                KeyboardControl().hideKeyboard(this@MainActivity)
                //hideKeyboard(this@MainActivity)
                productService.search(query)
                    .enqueue(object : Callback<Products> {
                        override fun onResponse(call: Call<Products>, response: Response<Products>) {
                            ProgressBarControl().hideProgressBar(binding.progressBar)
                            if(response.isSuccessful){
                                BannerControl().hideBanner(binding.scrollView)
                                val products = response.body()!!
                                productsAdapter.updateProducts(products.results)
                                productsAdapter.notifyDataSetChanged()
                            }
                            else{
                                ErrorControl().showError(getString(R.string.sinResultados),getString(
                                                                    R.string.sinResultadosDetalle),this@MainActivity)
                            }
                        }

                        override fun onFailure(call: Call<Products>?, t: Throwable?) {
                            ProgressBarControl().hideProgressBar(binding.progressBar)
                            ErrorControl().showError(getString(R.string.errorEnBusqueda),getString(R.string.errorEnBusquedaDetalle),this@MainActivity)
                        }
                    })
            }
        }
        else{
            ErrorControl().showError(getString(R.string.SinInternet),getString(R.string.SinInternetDetalle),this@MainActivity)
        }
    }
}
