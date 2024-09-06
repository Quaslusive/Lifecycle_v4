package Mobilt_java23.carl_sundberg.lifecyclev4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import android.view.MenuItem
import android.view.Menu


class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val btnMenu = view.findViewById<Button>(R.id.btnMenu)
        btnMenu.setOnClickListener {

            showPopupMenu(it)
        }
        return view
    }

    private fun logoutUser() {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()


        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Rensa back stack
        startActivity(intent)
        activity?.finish()
    }

    private fun showPopupMenu(view: View) {

        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_home -> {

                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.activity_profile -> {
                    val intent = Intent(activity, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_login -> {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_logout -> {
                    logoutUser()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
