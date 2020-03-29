package codepath.com.instagramparse

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ktx.putOrIgnore

/**
 * Created by rhu on 4/8/18.
 */

@ParseClassName("Post")
class Post : ParseObject() {

    // https://kotlinlang.org/docs/reference/properties.html#getters-and-setters
    var media: ParseFile?
        get() = getParseFile("media")
        set(file) = putOrIgnore("media", file)

    var caption: String?
        get() = getString("caption")
        set(caption) = putOrIgnore("caption", caption)

    var author: ParseUser?
        get() = getParseUser("author")
        set(author) = putOrIgnore("author", author)

    var likesCount: Int?
        get() = getInt("likesCount")
        set(likes) = putOrIgnore("likesCount", likes)

    var commentsCount: Int?
        get() = getInt("commentsCount")
        set(comments) = putOrIgnore("comments", comments)

}
