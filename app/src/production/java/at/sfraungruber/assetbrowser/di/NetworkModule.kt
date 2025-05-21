package at.sfraungruber.assetbrowser.di

import at.sfraungruber.assetbrowser.BuildConfig
import at.sfraungruber.assetbrowser.data.remote.CryptoApi
import at.sfraungruber.assetbrowser.data.remote.CurrencyApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()

                val url =
                    request
                        .url()
                        .newBuilder()
                        .addQueryParameter(
                            "apiKey",
                            BuildConfig.COINCAP_API_KEY,
                        )
                        .build()

                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rest.coincap.io/v3/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(MediaType.get("application/json")),
            )
            .build()
    }

    @Provides
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create()
    }

    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create()
    }
}
