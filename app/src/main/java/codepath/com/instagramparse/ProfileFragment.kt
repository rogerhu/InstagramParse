package codepath.com.instagramparse


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.parse.Parse
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_profile, container, false)
        v.findViewById<Button>(R.id.logout).setOnClickListener({
            ParseUser.logOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        })
        return v
    }

}
