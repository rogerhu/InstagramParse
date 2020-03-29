package codepath.com.instagramparse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bolts.Task
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parse.ParseUser
import kotlinx.android.synthetic.main.immersive_viewer_row_footer.view.*
import kotlinx.android.synthetic.main.immersive_viewer_row_header.view.*
import kotlinx.android.synthetic.main.item_post.view.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class PostAdapter: ListAdapter<Post, PostAdapter.ViewHolder>(PostDiffCallback()) {

    private val mPosts: MutableList<Post> = ArrayList()

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

                    Glide.with(view.context).load(media?.url).into(itemView.post_photo)
//                      Glide.with(view.context).load(images[adapterPosition % images.size]).into(itemView.post_photo)

                }
                Glide.with(view.context)
                        .load("https://instagram.fsnc1-1.fna.fbcdn.net/vp/d1933e703d5d82784a43157c8d02ff04/5B656952/t51.2885-19/s320x320/12918039_230227960666719_282379501_a.jpg")
                        .apply(RequestOptions.circleCropTransform())
                        .into(itemView.row_feed_photo_profile_imageview)

                itemView.ufi_row_feed_button_save.isSelected = true
                itemView.row_feed_photo_profile_name.text = ParseUser.getCurrentUser().username
                itemView.post_caption.text = caption
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val postView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        val viewHolder = ViewHolder(postView)
        return viewHolder
    }

    fun addPost(post: Post) {
        if(!mPosts.contains(post)) {
            mPosts.add(post)
            notifyItemInserted(mPosts.size)
        }
    }
    override fun getItemCount(): Int {
        return mPosts.count()
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = mPosts[position]

        holder.bindPost(post)
    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.objectId == newItem.objectId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem

    }
}
