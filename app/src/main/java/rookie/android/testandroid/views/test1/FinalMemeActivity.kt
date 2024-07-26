package rookie.android.testandroid.views.test1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import rookie.android.testandroid.R


class FinalMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_final_meme)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val finalImage: ImageView = findViewById(R.id.final_image)
        val bitmap = BitmapFactory.decodeStream(applicationContext.openFileInput("myImage"))
        finalImage.setImageBitmap(bitmap)
//        if(intent.hasExtra("BitmapImage")){
//
//
////            val bx = intent.getByteArrayExtra("BitmapImage")
//
////            Toast.makeText(applicationContext, "jhi iu", Toast.LENGTH_LONG).show()
////            val bitmap = BitmapFactory.decodeStream()
////            val bitmap = intent.getParcelableExtra<Bitmap>("BitmapImage")
////            finalImage.setImageBitmap(bitmap)
//
////            Glide.with(applicationContext).load(intent.getStringExtra("url")).centerCrop().into(imgMeme)
//        }else{
//            Toast.makeText(applicationContext, "Tidak Ada data", Toast.LENGTH_LONG).show()
//        }
    }
}