package com.vetmypet.vetmypet.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.vetmypet.vetmypet.PreferenceHelper
import com.vetmypet.vetmypet.PreferenceHelper.get
import com.vetmypet.vetmypet.PreferenceHelper.set
import com.vetmypet.vetmypet.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val snackBar by lazy {
        Snackbar.make(mainLayout, R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // shared preferences
        // sqlite
        // files

        /*
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val session = preferences.getBoolean("session", false)
        */
        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["session", false])
            goToMenuActivity()

        btnLogin.setOnClickListener {
            // validates
            createSessionPreference()
            goToMenuActivity()
        }

        tvGoToRegister.setOnClickListener {
            Toast.makeText( this, "Por favor complete los datos", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createSessionPreference(){
        /*
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", true)
        editor.apply()
        */
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = true
    }

    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (snackBar.isShown)
            super.onBackPressed()
        else
            snackBar.show()

    }
}
