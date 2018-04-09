package codepath.com.instagramparse

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject

/**
 * Created by rhu on 4/8/18.
 */

@ParseClassName("Post")
class Post : ParseObject() {

    // https://kotlinlang.org/docs/reference/properties.html#getters-and-setters
    var media : ParseFile?
    get() = getParseFile("media")
    set(file) {
        put("media", file)
    }
}
