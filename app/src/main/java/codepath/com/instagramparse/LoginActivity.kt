package codepath.com.instagramparse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //signup_button.text = getString(R.string.user_signup_message).toSpanned()

        val currentUser = ParseUser.getCurrentUser() // this will now be null

        if (currentUser != null) {
            launchActivity()
        }

        signup_button.setOnClickListener({
            val user = ParseUser()
            if (login_username.text.isEmpty() || password.text.isEmpty()) {
                return@setOnClickListener
            }

            with(user) {
                username = login_username.text.toString()
                setPassword(password.text.toString())
                signUpInBackground({ e ->
                    if (e != null) {
                        Toast.makeText(this@LoginActivity, "Error logging in: " + e.toString(), Toast.LENGTH_LONG).show()

                    }
                    else {
                        launchActivity()
                    }
                })
            }
        })

        login_button.setOnClickListener({
            ParseUser.logInInBackground(login_username.text.toString(), password.text.toString(), ({ user, e ->
                if (user != null) {
                    launchActivity()
                } else {
                    Toast.makeText(this, "Could not find user", Toast.LENGTH_LONG).show()
                }
            }))
        })

        //        public void done(ParseUser user, ParseException e) {
//            if (user != null) {
//                // Hooray! The user is logged in.
//            } else {
//                // Signup failed. Look at the ParseException to see what happened.
//            }
//        }
//    });

//        // Create the ParseUser
//        val user = ParseUser()
//        user.username = "joestevens"
//        user.setPassword("secret123")
//        user.email = "email@example.com"
//        user.put("phone", "650-253-0000")
//        user.signUpInBackground({e ->
//            if (e == null) {
//                // Hooray! Let them use the app now.
//            } else {
//                // Sign up didn't succeed. Look at the ParseException
//                // to figure out what went wrong
//            }
//        })


    }

    fun launchActivity() {
        val intent = Intent(this@LoginActivity, LandingActivity::class.java)
        startActivity(intent)
    }
}
