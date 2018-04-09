package codepath.com.instagramparse

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import android.widget.Toast
import android.graphics.BitmapFactory
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.parse.Parse
import com.parse.ParseFile
import kotlinx.android.synthetic.main.activity_landing.*

// findViewById -> replace with import kotlinx.android.synthetic.main.activity_landing.*
// bottm navigation view -> use selected state
// fromHtml -> has a legacy mode
// Kotlin let
// bottom navigation view -> when
// Kotlin return value
// https://medium.com/@eduardo22i/parse-sdk-android-and-kotlin-bc4c55e95ba8


class LandingActivity : AppCompatActivity() {

    val APP_TAG = "MyCustomApp"
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    var photoFileName = "photo.jpg"
    var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        bottom_navigation.setOnNavigationItemSelectedListener({ menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
                }
                R.id.action_camera -> {
                    onLaunchCamera()
                }
                R.id.action_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show()
                } else -> {}
            }
            true
        })
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        val fileProvider = photoFile?.let { FileProvider.getUriForFile(this, "com.codepath.fileprovider", it) }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(packageManager) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                var post = Post()
                with(post) {
                    media = ParseFile(photoFile)
                }
                post.saveInBackground()
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File? {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename

        return File(mediaStorageDir.path + File.separator + fileName)
    }
}
