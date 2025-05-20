package at.sfraungruber.assetbrowser.data.repository

import at.sfraungruber.assetbrowser.data.remote.AssetResponse
import at.sfraungruber.assetbrowser.data.remote.CryptoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepository
    @Inject
    constructor(
        private val cryptoApi: CryptoApi,
    ) {
        suspend fun getCoins(): AssetResponse = cryptoApi.getAsset()
    }
