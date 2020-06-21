package ar.com.tallerdeprogramacion.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable

interface ProductsService{
    @GET("sites/MLA/search?")
    fun search(
        @Query("q") term: String
        ): Call<Products>
}

data class Products(
    @SerializedName(value = "results")
    val results: List<Product>
)

data class Product(
    @SerializedName(value="title")
    val title : String,
    @SerializedName(value = "id")
    val id: String,
    @SerializedName(value = "price")
    val price: String,
    @SerializedName(value = "available_quantity")
    val quantity: String,
    @SerializedName(value = "condition")
    val condition: String,
    @SerializedName(value = "thumbnail")
    val imageUrl : String,
    @SerializedName(value = "sold_quantity")
    val soldQuantity : String
):Serializable
