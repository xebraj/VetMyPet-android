package com.vetmypet.vetmypet.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.vetmypet.vetmypet.R
import com.vetmypet.vetmypet.io.ApiService
import com.vetmypet.vetmypet.model.Vetmypet
import com.vetmypet.vetmypet.util.PreferenceHelper
import com.vetmypet.vetmypet.util.PreferenceHelper.get
import com.vetmypet.vetmypet.util.toast
import kotlinx.android.synthetic.main.activity_mypet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypetActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private val mypetAdapter = MypetAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypet)

        loadAppointments()

        rvMypet.layoutManager = LinearLayoutManager(this)
        rvMypet.adapter = mypetAdapter
    }

    private fun loadAppointments() {
        val jwt = preferences["jwt", ""]
        val call = apiService.getMypet("Bearer $jwt")
        call.enqueue(object: Callback<ArrayList<Vetmypet>> {
            override fun onFailure(call: Call<ArrayList<Vetmypet>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<ArrayList<Vetmypet>>, response: Response<ArrayList<Vetmypet>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        mypetAdapter.mypet = it
                        mypetAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}