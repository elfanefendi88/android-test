package rookie.android.testandroid.api.model

data class MemeResponse (
    val success: Boolean,
    val data: MemeData
)

data class MemeData (
    val memes: List<Meme>
)