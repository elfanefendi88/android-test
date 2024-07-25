package rookie.android.testandroid.views.test1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rookie.android.testandroid.R
import rookie.android.testandroid.api.ApiClient
import rookie.android.testandroid.api.adapter.MemeAdapter
import rookie.android.testandroid.api.model.Meme
import rookie.android.testandroid.api.model.MemeResponse

class MimGeneratorActivity : AppCompatActivity() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<MemeResponse>
    private lateinit var memeAdapter: MemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mim_generator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.refresh_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        swipeRefresh = findViewById(R.id.refresh_layout)
        recyclerView = findViewById(R.id.recycleview_test1)

        memeAdapter = MemeAdapter { meme -> memeOnclick(meme) }
        recyclerView.adapter = memeAdapter
        recyclerView.layoutManager = GridLayoutManager(applicationContext,3)
//        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        swipeRefresh.setOnRefreshListener {
            getData()
        }

        getData()
    }

    private fun memeOnclick(meme: Meme){
        val intent = Intent(this, DetailMemeActivity::class.java)
        intent.putExtra("url", meme.url)
        startActivity(intent)
//        Toast.makeText(applicationContext, meme.name, Toast.LENGTH_SHORT).show()
    }

    private fun getData(){
        swipeRefresh.isRefreshing = true

        call = ApiClient.memeService.getAllMemes()
        call.enqueue(object : Callback<MemeResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MemeResponse>,
                response: Response<MemeResponse>
            ) {
                swipeRefresh.isRefreshing = false

                if(response.isSuccessful){
                    memeAdapter.submitList(response.body()?.data?.memes)
                    memeAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                swipeRefresh.isRefreshing = false

                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}