package com.vetmypet.vetmypet.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vetmypet.vetmypet.PreferenceHelper
import kotlinx.android.synthetic.main.activity_menu.*
import com.vetmypet.vetmypet.PreferenceHelper.set
import com.vetmypet.vetmypet.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCreate.setOnClickListener {

            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        btnVetMyPet.setOnClickListener {
            val intent = Intent(this, MypetActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            clearSessionPreference()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        }


    private fun clearSessionPreference(){
        /*
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", false)
        editor.apply()
        */
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = false
    }
}
