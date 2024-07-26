package rookie.android.testandroid.views.test1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import rookie.android.testandroid.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetailMemeActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_meme)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val cardView: CardView = findViewById(R.id.image_area)
        val btnSimpan: Button = findViewById(R.id.btn_simpan)
        val imgMeme: ImageView = findViewById(R.id.img_detailmeme)
        val imgAdded: ImageView = findViewById(R.id.img_added)
        val btnLogo: Button = findViewById(R.id.btn_addlogo)
        val btnText: Button = findViewById(R.id.btn_addtext)
        val txtAdded: TextView = findViewById(R.id.txt_added)

        if(intent.hasExtra("url")){
            Glide.with(applicationContext).load(intent.getStringExtra("url")).centerCrop().into(imgMeme)
        }else{
            Toast.makeText(applicationContext, "Tidak Ada data", Toast.LENGTH_LONG).show()
        }

        val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Glide.with(applicationContext).load(it).centerCrop().into(imgAdded)
        }

        btnLogo.setOnClickListener(){
            galleryImage.launch("image/*")
        }

        btnText.setOnClickListener(){
            val alert = AlertDialog.Builder(this)
            val inflater: LayoutInflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.popup_input_text, null)
            val inputText: EditText = dialogLayout.findViewById(R.id.input_text)

            with(alert){
                setTitle("Input Text")
                setPositiveButton("OK"){
                    dialog, which -> txtAdded.text = inputText.text.toString()
                }
                setNegativeButton("Cancel"){
                    dialog, which -> Log.d("Main", "Cancel clicked")
                }
                setView(dialogLayout)
                show()
            }
        }

        btnSimpan.setOnClickListener(){
            val bitmap = getScreenShotFromView(cardView)
            if (bitmap != null) {
//                saveMediaToStorage(bitmap)
                var fileName: String? = "myImage"

                try {
                    val bx = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bx)
//                    val byteArray: ByteArray? = bx.toByteArray()

                    val fso: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
                    fso.write(bx.toByteArray())
                    // remember close file output
                    fso.close()




//                    val bytes = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                    val fo: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
//                    fo.write(bytes.toByteArray())
//                    // remember close file output
//                    fo.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    fileName = null
                }

                val intent = Intent(this, FinalMemeActivity::class.java)
//                intent.putExtra("BitmapImage", fileName)
//                intent.putExtra("sdsd", )
                startActivity(intent)
            }
        }
    }

    private fun getScreenShotFromView(v: View): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object
            // with Bitmap.createBitmap it
            // requires three parameters
            // width and height of the view and
            // the background color
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
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