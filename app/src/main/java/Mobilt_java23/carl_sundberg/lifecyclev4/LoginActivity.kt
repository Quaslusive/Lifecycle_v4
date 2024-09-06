package Mobilt_java23.carl_sundberg.lifecyclev4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val fragment = MenuFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.action_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val loginButton = findViewById<Button>(R.id.btnLogin)


        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val savedUsername = sharedPreferences.getString("username", null)
        val savedPassword = sharedPreferences.getString("password", null)

        if (savedPassword != null && savedUsername != null) {
            navigateToProfile()
        }

        loginButton.setOnClickListener {

            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            if (validateLogin(username, password)) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                navigateToProfile()
            } else {
                Toast.makeText(this, "Felaktigt användarnamn eller lösenord", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun validateLogin(username: String, password: String): Boolean {
        return username == "" && password == ""

    }

    private fun navigateToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()  // Stäng login-aktiviteten så att användaren inte kan backa hit
    }

}