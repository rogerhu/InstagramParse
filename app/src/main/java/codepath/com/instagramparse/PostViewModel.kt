package codepath.com.instagramparse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Toast
import com.parse.ParseQuery

// https://www.youtube.com/watch?v=MfHsPGQ6bgE
// https://stackoverflow.com/questions/44208618/how-to-handle-error-states-with-livedata

class PostViewModel : ViewModel() {

    val LIMIT: Int = 100

    var postAdapter: PostAdapter? = null

    init {
        postAdapter = PostAdapter()
    }

    fun load() {
        val query = fetchData(batchNum = 0)
        query.findInBackground({posts : List<Post>?, e ->
            if (e == null) {
                if (posts != null) {
                    for (post in posts) {
                        postAdapter?.addPost(post)
                    }
                }
            } else {
                // handler error
            }
        })
    }

    fun fetchData(batchNum: Int) : ParseQuery<Post> {
        val query = ParseQuery.getQuery(Post::class.java)
//        query.whereEqualTo("author", ParseUser.getCurrentUser())
        query.limit = LIMIT

        query.setSkip(batchNum * LIMIT)
        return query
    }

    override fun onCleared() {
        super.onCleared()
    }
}