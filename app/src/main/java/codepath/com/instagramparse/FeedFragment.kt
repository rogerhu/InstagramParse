package codepath.com.instagramparse


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.fragment_feed.view.*


class FeedFragment : Fragment() {

    lateinit var mPosts : MutableList<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        val query = ParseQuery.getQuery(Post::class.java)
        val recyclerView = view.rvPosts
        mPosts = ArrayList<Post>(0)

        recyclerView.adapter = PostAdapter(mPosts)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        query.findInBackground({posts : List<Post>, e ->
            mPosts.addAll(posts)
            recyclerView.adapter.notifyDataSetChanged()
        })
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                FeedFragment()
    }
}
