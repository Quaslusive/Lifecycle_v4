package Mobilt_java23.carl_sundberg.lifecyclev4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.Menu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Hämta referenser till TextViews
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val tvGender = findViewById<TextView>(R.id.tvGender)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvCeleryMan = findViewById<TextView>(R.id.tvCeleryMan)
        //   val tvSubscription = findViewById<TextView>(R.id.tvSubscription)

        // Ladda data från SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "N/A")
        val age = sharedPreferences.getString("age", "N/A")
        val gender = sharedPreferences.getString("gender", "N/A")
        val email = sharedPreferences.getString("email", "N/A")
        val hasSeenCeleryMan = sharedPreferences.getBoolean("hasSeenCeleryMan", false)
        val subscriptionType = sharedPreferences.getString("subscriptionType", "Free")

        // Sätt värden till TextViews
        tvName.text = "Namn: $name"
        tvAge.text = "Ålder: $age"
        tvGender.text = "Kön: $gender"
        tvEmail.text = "Email: $email"
        tvCeleryMan.text =
            "Har sett Tim and Eric - Celery Man:  ${if (hasSeenCeleryMan) "Ja" else "Nej"}"
        //  tvSubscription.text = "Subscription: $subscriptionType"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                // Navigera till ProfileActivity
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.profile -> {
                // Hantera klick på "Settings"
                // Exempelvis kan du öppna en inställningsskärm
                true
            }
            R.id.action_logout -> {
                // Hantera utloggning (om t.ex. du vill logga ut användaren)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
