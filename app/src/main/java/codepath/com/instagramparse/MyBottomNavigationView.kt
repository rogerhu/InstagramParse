package codepath.com.instagramparse

import android.support.design.internal.BottomNavigationMenuView
import java.lang.reflect.AccessibleObject.setAccessible
import android.support.design.widget.BottomNavigationView
import android.view.Gravity
import android.R.attr.gravity
import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import android.support.v7.widget.AppCompatImageView
import android.support.design.internal.BottomNavigationItemView
import android.util.AttributeSet


class MyBottomNavigationView : BottomNavigationView {

    private val bottomMenuView: BottomNavigationMenuView?
        get() {
            var menuView: Any? = null
            try {
                val field = BottomNavigationView::class.java.getDeclaredField("mMenuView")
                field.isAccessible = true
                menuView = field.get(this)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return menuView as BottomNavigationMenuView?
        }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        centerMenuIcon()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        centerMenuIcon()
    }

    @SuppressLint("RestrictedApi")
    public fun centerMenuIcon() {
        val menuView = bottomMenuView

        if (menuView != null) {
            for (i in 0 until menuView.childCount) {
                val menuItemView = menuView.getChildAt(i) as BottomNavigationItemView

                val icon = menuItemView.getChildAt(0) as AppCompatImageView

                val params = icon.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.CENTER

                menuItemView.setShiftingMode(true)
            }
        }
    }


}