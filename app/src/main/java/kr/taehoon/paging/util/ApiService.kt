package kr.taehoon.paging.util

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET("/v3/search/book")
    fun getSearchBook(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 50
    ): Single<JsonElement>
}