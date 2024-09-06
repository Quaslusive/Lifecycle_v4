package Mobilt_java23.carl_sundberg.lifecyclev4

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.view.MenuItem
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val fragment = MenuFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.action_home)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            val tvName = findViewById<TextView>(R.id.tvName)
            val tvAge = findViewById<TextView>(R.id.tvAge)
            val tvGender = findViewById<TextView>(R.id.tvGender)
            val tvEmail = findViewById<TextView>(R.id.tvEmail)
            val tvCeleryMan = findViewById<TextView>(R.id.tvCeleryMan)

            val sharedPreferences: SharedPreferences =
                getSharedPreferences("userProfile", MODE_PRIVATE)
            val name = sharedPreferences.getString("name", "N/A")
            val age = sharedPreferences.getString("age", "N/A")
            val gender = sharedPreferences.getString("gender", "N/A")
            val email = sharedPreferences.getString("email", "N/A")
            val hasSeenCeleryMan = sharedPreferences.getBoolean("hasSeenCeleryMan", false)


            tvName.text = "Namn: $name"
            tvAge.text = "Ålder: $age"
            tvGender.text = "Kön: $gender"
            tvEmail.text = "Email: $email"
            tvCeleryMan.text =
                "Har sett Tim and Eric - Celery Man:  ${if (hasSeenCeleryMan) "Ja" else "Nej"}"

        }
    }

