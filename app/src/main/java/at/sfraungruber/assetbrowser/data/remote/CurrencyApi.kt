package at.sfraungruber.assetbrowser.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("rates/{id}")
    suspend fun getRateById(
        @Path("id") id: String = "euro",
    ): CurrencyResponse
}
