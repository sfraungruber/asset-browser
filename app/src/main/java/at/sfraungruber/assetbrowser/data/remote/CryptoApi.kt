package at.sfraungruber.assetbrowser.data.remote

import retrofit2.http.GET

interface CryptoApi {
    @GET("assets")
    suspend fun getAsset(): AssetResponse
}
