package rookie.android.testandroid.api.services

import retrofit2.Call
import retrofit2.http.GET
import rookie.android.testandroid.api.model.MemeResponse

interface MemeService {
    @GET("get_memes")
    fun getAllMemes(): Call<MemeResponse>
}