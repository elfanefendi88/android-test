package rookie.android.testandroid.views.test1

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import rookie.android.testandroid.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


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

        val btnDownload: Button = findViewById(R.id.btn_download)
        val finalImage: ImageView = findViewById(R.id.final_image)
        val bitmap = BitmapFactory.decodeStream(applicationContext.openFileInput("myImage"))
        finalImage.setImageBitmap(bitmap)

        btnDownload.setOnClickListener(){
            saveMediaToStorage(bitmap)
        }
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

    // this method saves the image to gallery
    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }
}