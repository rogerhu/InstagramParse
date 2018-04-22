package codepath.com.instagramparse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.parse.ParseFile
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import android.graphics.BitmapFactory
import com.parse.Parse
import com.parse.ParseUser


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CameraFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {

    val APP_TAG = "MyCustomApp"
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    var photoFileName = "photo.jpg"
    var photoFile: File? = null

    companion object {
        fun newInstance(): CameraFragment {
            return CameraFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_camera, container, false)
        val button = view.findViewById<Button>(R.id.take_picture)
        button.setOnClickListener { onLaunchCamera() }
        view.findViewById<Button>(R.id.post_button).setOnClickListener { onPost() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        resetState()
        super.onViewCreated(view, savedInstanceState)
    }

    fun resetState() {
        take_picture.visibility = View.VISIBLE
        post_button.visibility = View.GONE
        image_caption.visibility = View.GONE
        image_caption.text.clear()
        imagePreview.setImageBitmap(null)
    }
    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        val fileProvider = photoFile?.let { FileProvider.getUriForFile(context!!, "com.codepath.fileprovider", it) }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(context!!.packageManager) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File? {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir = File(
                activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename

        return File(mediaStorageDir.path + File.separator + fileName)
    }

    fun onPost() {
        // by this point we have the camera photo on disk
        var post = Post()
        with(post) {
            media = ParseFile(photoFile)
            caption = image_caption.text.toString()
            author = ParseUser.getCurrentUser()
            commentsCount = 0
            likesCount = 0
        }
        post.saveInBackground({
            Toast.makeText(context!!, "Saved", Toast.LENGTH_LONG).show()
            resetState()
//            (activity as LandingActivity).selectDrawerItem(R.id.action_home )
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
                imagePreview.setImageBitmap(takenImage)

                image_caption.visibility = View.VISIBLE
                take_picture.visibility = View.GONE
                post_button.visibility = View.VISIBLE
            } else { // Result was a failure
                Toast.makeText(context!!, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}