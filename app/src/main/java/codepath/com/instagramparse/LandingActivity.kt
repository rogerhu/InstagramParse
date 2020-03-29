package codepath.com.instagramparse

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_landing.*
import kotlin.reflect.KClass

// findViewById -> replace with import kotlinx.android.synthetic.main.activity_landing.*
// bottm navigation view -> use selected state
// fromHtml -> has a legacy mode
// Kotlin let
// bottom navigation view -> when
// Kotlin return value
// https://medium.com/@eduardo22i/parse-sdk-android-and-kotlin-bc4c55e95ba8
// super.onActivityResult() for activity to handle it?
// BottomNavigationView -> shift mode (https://readyandroid.wordpress.com/disable-bottomnavigationview-shift-mode/)
// ParseFile upload
// BottomNavigationView - set checked
class LandingActivity : AppCompatActivity(R.layout.activity_landing) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        bottom_navigation.setTextVisibility(false)
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
            R.id.action_profile -> ProfileFragment::class

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
