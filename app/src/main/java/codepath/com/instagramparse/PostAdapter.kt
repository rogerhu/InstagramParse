package codepath.com.instagramparse

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.parse.ParseFile
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(private val mPosts: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    // https://github.com/antoniolg/Kotlin-for-Android-Developers
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bindPost(post: Post) {
            with(post) {
                itemView.post_photo.visibility = View.GONE

                if (media != null) {
                    itemView.post_photo.visibility = View.VISIBLE
                    itemView.post_photo.setParseFile(media)
                    itemView.post_photo.loadInBackground()
                }
                itemView.post_caption.text = description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val postView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        val viewHolder = ViewHolder(postView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPosts.count()
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = mPosts[position]

        holder.bindPost(post)
    }
}