package codepath.com.instagramparse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.immersive_viewer_row_footer.view.*
import kotlinx.android.synthetic.main.immersive_viewer_row_header.view.*
import kotlinx.android.synthetic.main.item_post.view.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class PostAdapter(private val mPosts: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    // https://github.com/antoniolg/Kotlin-for-Android-Developers
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        val images = arrayOf("https://instagram.fsnc1-1.fna.fbcdn.net/vp/1b3989ec2c1434320d5c05fd954dc561/5B6858B2/t51.2885-15/e35/30602785_344472682626150_8765006822013665280_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/f4e7679ec552f3ffb8717d023cf548d2/5B6578B5/t51.2885-15/e35/30086834_720059698383171_8213742920553988096_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/f4c9aed45618c7f413ab32aa57730020/5B4F0F33/t51.2885-15/e35/30590736_970161719806111_1639824213034401792_n.jpg",
                "https://instagram.fsnc1-1.fna.fbcdn.net/vp/66e2c33d3a36e5125f4f13fc34c6f76d/5B6A309B/t51.2885-15/e35/30076470_2103926266558259_8135244241371660288_n.jpg")

        fun bindPost(post: Post) {
            with(post) {
                itemView.post_photo.visibility = View.GONE

                if (media != null) {
                    itemView.post_photo.visibility = View.VISIBLE
//                    itemView.post_photo.setParseFile(media)
//                    itemView.post_photo.loadInBackground()

//                    Glide.with(view.context).load(media?.url).into(itemView.post_photo)
                      Glide.with(view.context).load(images[adapterPosition % images.size]).into(itemView.post_photo)

                }
                Glide.with(view.context)
                        .load("https://instagram.fsnc1-1.fna.fbcdn.net/vp/d1933e703d5d82784a43157c8d02ff04/5B656952/t51.2885-19/s320x320/12918039_230227960666719_282379501_a.jpg")
                        .apply(RequestOptions.circleCropTransform())
                        .into(itemView.row_feed_photo_profile_imageview)

                itemView.ufi_row_feed_button_save.isSelected = true
                itemView.row_feed_photo_profile_name.text = "beyonce"
                itemView.post_caption.text = caption
                itemView.post_caption.text = ""
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