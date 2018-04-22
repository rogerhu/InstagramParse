package codepath.com.instagramparse


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_feed.view.*


class FeedFragment : Fragment() {

    lateinit var mPosts : MutableList<Post>
    val LIMIT: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        val recyclerView = view.rvPosts
        mPosts = ArrayList<Post>(0)

        val query = fetchData(batchNum = 0)

        val images = arrayOf("https://instagram.fsnc1-1.fna.fbcdn.net/vp/1b3989ec2c1434320d5c05fd954dc561/5B6858B2/t51.2885-15/e35/30602785_344472682626150_8765006822013665280_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/f4e7679ec552f3ffb8717d023cf548d2/5B6578B5/t51.2885-15/e35/30086834_720059698383171_8213742920553988096_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/f4c9aed45618c7f413ab32aa57730020/5B4F0F33/t51.2885-15/e35/30590736_970161719806111_1639824213034401792_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/66e2c33d3a36e5125f4f13fc34c6f76d/5B6A309B/t51.2885-15/e35/30076470_2103926266558259_8135244241371660288_n.jpg")

        recyclerView.adapter = PostAdapter(mPosts)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        query.findInBackground({posts : List<Post>, e ->
            val postsSize = mPosts.size - 1
            mPosts.addAll(posts)
            recyclerView.adapter.notifyItemRangeInserted(postsSize, posts.size)
        })
        return view
    }

    fun fetchData(batchNum: Int) : ParseQuery<Post> {
        val query = ParseQuery.getQuery(Post::class.java)
//        query.whereEqualTo("author", ParseUser.getCurrentUser())
        query.limit = LIMIT

        query.setSkip(batchNum * LIMIT)
        return query
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                FeedFragment()
    }
}
