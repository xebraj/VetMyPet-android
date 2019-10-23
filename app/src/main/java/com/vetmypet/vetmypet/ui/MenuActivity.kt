package com.vetmypet.vetmypet.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vetmypet.vetmypet.util.PreferenceHelper
import kotlinx.android.synthetic.main.activity_menu.*
import com.vetmypet.vetmypet.util.PreferenceHelper.set
import com.vetmypet.vetmypet.util.PreferenceHelper.get
import com.vetmypet.vetmypet.R
import com.vetmypet.vetmypet.io.ApiService
import com.vetmypet.vetmypet.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

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
            performLogout()
        }
        }

    private fun performLogout() {
        val jwt = preferences["jwt", ""]
        val call = apiService.postLogout("Bearer $jwt")
        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()

                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }


    private fun clearSessionPreference(){
        preferences["jwt"] = ""
    }
}
