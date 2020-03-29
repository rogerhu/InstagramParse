package codepath.com.instagramparse


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_feed.view.*

// https://github.com/googlesamples/android-architecture-components/blob/f95d30e6d0dae5105a8521139824e32617a11b06/PagingWithNetworkSample/app/src/main/java/com/android/example/paging/pagingwithnetwork/reddit/ui/RedditActivity.kt
// https://www.bignerdranch.com/blog/activity-and-fragment-layouts-with-androidx/
class FeedFragment : Fragment(R.layout.fragment_feed) {

    val viewModel : PostViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val recyclerView = view?.rvPosts

        val images = arrayOf("https://instagram.fsnc1-1.fna.fbcdn.net/vp/1b3989ec2c1434320d5c05fd954dc561/5B6858B2/t51.2885-15/e35/30602785_344472682626150_8765006822013665280_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/f4e7679ec552f3ffb8717d023cf548d2/5B6578B5/t51.2885-15/e35/30086834_720059698383171_8213742920553988096_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/f4c9aed45618c7f413ab32aa57730020/5B4F0F33/t51.2885-15/e35/30590736_970161719806111_1639824213034401792_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/66e2c33d3a36e5125f4f13fc34c6f76d/5B6A309B/t51.2885-15/e35/30076470_2103926266558259_8135244241371660288_n.jpg")

        val swipeContainer = view?.findViewById<SwipeRefreshLayout>(R.id.swipeContainer)
        swipeContainer?.setOnRefreshListener { viewModel?.load() }
        swipeContainer?.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        recyclerView?.adapter = viewModel.postAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this.context)

        viewModel.load()
        // https://github.com/KucherenkoIhor/Android-Architecture-Components

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                FeedFragment()
    }
}
