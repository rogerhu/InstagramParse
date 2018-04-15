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
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.parse.Parse
import com.parse.ParseFile
import junit.runner.Version.id
import kotlinx.android.synthetic.main.activity_landing.*
import kotlin.reflect.KClass

// findViewById -> replace with import kotlinx.android.synthetic.main.activity_landing.*
// bottm navigation view -> use selected state
// fromHtml -> has a legacy mode
// Kotlin let
// bottom navigation view -> when
// Kotlin return value
// https://medium.com/@eduardo22i/parse-sdk-android-and-kotlin-bc4c55e95ba8


class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        bottom_navigation.setOnNavigationItemSelectedListener({ menuItem ->
            selectDrawerItem(menuItem.itemId)
            true
        })
        selectDrawerItem(R.id.action_home)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
    }

    // https://gist.github.com/rcgonzalezf/b1755891115abe44a96e77d3587dd824

    fun selectDrawerItem(menuId : Int) {
        var fragmentClass: KClass<out Fragment>? = when (menuId) {
            R.id.action_home -> FeedFragment::class
            R.id.action_camera -> CameraFragment::class
            R.id.action_profile -> {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                null
            }
            else -> null
        }

        if (fragmentClass != null) {
            var fragment = fragmentClass.java.newInstance() as Fragment
            var fragmentManager = getSupportFragmentManager()
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}
