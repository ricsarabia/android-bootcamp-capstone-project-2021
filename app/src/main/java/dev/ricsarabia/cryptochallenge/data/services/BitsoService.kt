package dev.ricsarabia.cryptochallenge.data.services

import dev.ricsarabia.cryptochallenge.data.AvailableBooksResponse
import dev.ricsarabia.cryptochallenge.data.OrderBookResponse
import dev.ricsarabia.cryptochallenge.data.TickerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitsoService {
    @GET("available_books/")
    suspend fun getAvailableBooks(): Response<AvailableBooksResponse>

    @GET("ticker/")
    suspend fun getTicker(@Query("book") book: String): Response<TickerResponse>

    @GET("order_book/")
    suspend fun getOrderBook(@Query("book") book: String,
                             @Query("aggregate") aggregate: Boolean = true): Response<OrderBookResponse>
}