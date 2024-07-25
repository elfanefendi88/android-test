package rookie.android.testandroid.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rookie.android.testandroid.api.services.MemeService

object ApiClient {
    private const val BASE_URL = "https://api.imgflip.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val memeService: MemeService by lazy {
        retrofit.create(MemeService::class.java)
    }
}