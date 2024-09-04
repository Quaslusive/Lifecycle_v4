package Mobilt_java23.carl_sundberg.lifecyclev4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEditText = findViewById<EditText>(R.id.etName)
        val ageEditText = findViewById<EditText>(R.id.etAge)
        val celeryManSwitch = findViewById<Switch>(R.id.swicthCeleryMan)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.rgGender)
        val emailEditText = findViewById<EditText>(R.id.etEmail)
    //    val subscriptionSpinner = findViewById<Spinner>(R.id.spinnerSubscription)

        val submitButton = findViewById<Button>(R.id.btnSubmit)
        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val hasSeenCeleryMan = celeryManSwitch.isChecked
            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val gender = when (selectedGenderId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> "Other"
            }
            val email = emailEditText.text.toString()
          //  val subscriptionType = subscriptionSpinner.selectedItem.toString()

            // Spara data i SharedPreferences
            val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("age", age)
            editor.putBoolean("hasSeenCeleryMan", hasSeenCeleryMan)
            editor.putString("gender", gender)
            editor.putString("email", email)
         //   editor.putString("subscriptionType", subscriptionType)
            editor.apply()

            Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()
        }

        // Ladda sparad data om det finns
        loadProfileData()
    }

    private fun loadProfileData() {
        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        findViewById<EditText>(R.id.etName).setText(sharedPreferences.getString("name", ""))
        findViewById<EditText>(R.id.etAge).setText(sharedPreferences.getString("age", ""))
        findViewById<Switch>(R.id.swicthCeleryMan).isChecked =
            sharedPreferences.getBoolean("hasSeenCeleryMan", false)
        val gender = sharedPreferences.getString("gender", "")
        if (gender == "Male") {
            findViewById<RadioButton>(R.id.rbMale).isChecked = true
        } else if (gender == "Female") {
            findViewById<RadioButton>(R.id.rbFemale).isChecked = true
        } else {
            findViewById<RadioButton>(R.id.rbOther).isChecked = true
        }
        findViewById<EditText>(R.id.etEmail).setText(sharedPreferences.getString("email", ""))
     /*   val subscriptionType = sharedPreferences.getString("subscriptionType", "Free")
        val spinner = findViewById<Spinner>(R.id.spinnerSubscription)
        val adapter = spinner.adapter as ArrayAdapter<String>
        val position = adapter.getPosition(subscriptionType)
        spinner.setSelection(position)*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
           //     val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_profile -> {
                // Om användaren redan är på profilsidan, kan du välja att inte göra något
                true
            }

            R.id.action_settings -> {
            //    val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
