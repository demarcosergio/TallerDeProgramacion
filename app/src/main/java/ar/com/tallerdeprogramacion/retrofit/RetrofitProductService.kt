package ar.com.tallerdeprogramacion.retrofit;

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProductService {
    private val service : ProductsService = Retrofit.Builder()
    .baseUrl("https://api.mercadolibre.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ProductsService::class.java)

    fun search(term: String): Call<Products>{
        return service.search(term)
    }
}
