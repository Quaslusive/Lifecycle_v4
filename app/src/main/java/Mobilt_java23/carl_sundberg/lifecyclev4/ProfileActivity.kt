package Mobilt_java23.carl_sundberg.lifecyclev4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val fragment = MenuFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val nameEditText = findViewById<EditText>(R.id.etName)
        val ageEditText = findViewById<EditText>(R.id.etAge)
        val celeryManSwitch = findViewById<Switch>(R.id.switchCeleryMan)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.rgGender)
        val emailEditText = findViewById<EditText>(R.id.etEmail)
        val submitButton = findViewById<Button>(R.id.btnSubmit)

        if (savedInstanceState != null) {
            nameEditText.setText(savedInstanceState.getString("name"))
            ageEditText.setText(savedInstanceState.getString("age"))
            emailEditText.setText(savedInstanceState.getString("email"))
            celeryManSwitch.isChecked = savedInstanceState.getBoolean("celeryMan")

            val genderId = savedInstanceState.getInt("gender")
            if (genderId != -1) {
                genderRadioGroup.check(genderId)
            }
        }

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val email = emailEditText.text.toString()

            if (!validateName(name) || !validateAge(age) || !validateEmail(email)) {
                Toast.makeText(this, "Kontrollera din input!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveData(name, age, celeryManSwitch.isChecked, genderRadioGroup, email)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        loadProfileData()
    }


    private fun validateName(name: String): Boolean {
        val namePattern = "^[A-Za-z]{2,30}$"  // Endast bokstäver, mellan 2 och 30 tecken
        return Pattern.matches(namePattern, name)
    }

    private fun validateAge(age: String): Boolean {
        val agePattern = "^[0-9]{1,3}$"  // Endast siffror, mellan 1 och 3 tecken
        return Pattern.matches(agePattern, age) && age.toInt() in 1..420
    }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"  //  e-postformat
        return Pattern.matches(emailPattern, email)
    }

    private fun saveData(
        name: String, age: String, hasSeenCeleryMan: Boolean,
        genderRadioGroup: RadioGroup, email: String
    ) {
        val selectedGenderId = genderRadioGroup.checkedRadioButtonId
        val gender = when (selectedGenderId) {
            R.id.rbMale -> "Man"
            R.id.rbFemale -> "Kvinna"
            else -> "Något helt annat"
        }


        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("age", age)
        editor.putBoolean("hasSeenCeleryMan", hasSeenCeleryMan)
        editor.putString("gender", gender)
        editor.putString("email", email)
        editor.apply()

        Toast.makeText(this, "Profil sparad!💾", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileData() {
        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        findViewById<EditText>(R.id.etName).setText(sharedPreferences.getString("name", ""))
        findViewById<EditText>(R.id.etAge).setText(sharedPreferences.getString("age", ""))
        findViewById<Switch>(R.id.switchCeleryMan).isChecked =
            sharedPreferences.getBoolean("hasSeenCeleryMan", false)

        val gender = sharedPreferences.getString("gender", "")
        if (gender == "Man") {
            findViewById<RadioButton>(R.id.rbMale).isChecked = true
        } else if (gender == "Kvinna") {
            findViewById<RadioButton>(R.id.rbFemale).isChecked = true
        } else {
            findViewById<RadioButton>(R.id.rbOther).isChecked = true
        }
        findViewById<EditText>(R.id.etEmail).setText(sharedPreferences.getString("email", ""))
    }


// Spara tillfällig data när aktiviteten pausas eller skärmen vänds
override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    val nameEditText = findViewById<EditText>(R.id.etName)
    val ageEditText = findViewById<EditText>(R.id.etAge)
    val emailEditText = findViewById<EditText>(R.id.etEmail)
    val celeryManSwitch = findViewById<Switch>(R.id.switchCeleryMan)
    val genderRadioGroup = findViewById<RadioGroup>(R.id.rgGender)


    outState.putString("name", nameEditText.text.toString())
    outState.putString("age", ageEditText.text.toString())
    outState.putString("email", emailEditText.text.toString())
    outState.putBoolean("celeryMan", celeryManSwitch.isChecked)


    val selectedGenderId = genderRadioGroup.checkedRadioButtonId
    outState.putInt("gender", selectedGenderId)
}

// Återställ datan när aktiviteten återupptas
override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)

    val nameEditText = findViewById<EditText>(R.id.etName)
    val ageEditText = findViewById<EditText>(R.id.etAge)
    val emailEditText = findViewById<EditText>(R.id.etEmail)
    val celeryManSwitch = findViewById<Switch>(R.id.switchCeleryMan)
    val genderRadioGroup = findViewById<RadioGroup>(R.id.rgGender)

    nameEditText.setText(savedInstanceState.getString("name"))
    ageEditText.setText(savedInstanceState.getString("age"))
    emailEditText.setText(savedInstanceState.getString("email"))
    celeryManSwitch.isChecked = savedInstanceState.getBoolean("celeryMan")

    val selectedGenderId = savedInstanceState.getInt("gender")
    if (selectedGenderId != -1) {
        genderRadioGroup.check(selectedGenderId)
    }
}
}