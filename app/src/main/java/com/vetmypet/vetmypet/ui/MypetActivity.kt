package com.vetmypet.vetmypet.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.vetmypet.vetmypet.R
import com.vetmypet.vetmypet.model.Vetmypet
import kotlinx.android.synthetic.main.activity_mypet.*

class MypetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypet)


        val mypet = ArrayList<Vetmypet>()
        mypet.add(
            Vetmypet(1, "Medico test", "12/12/2019", "3:00 PM")
        )

        mypet.add(
            Vetmypet(2, "Medico BB", "15/12/2019", "5:00 PM")
        )

        mypet.add(
            Vetmypet(3, "Medico CC", "17/12/2019", "8:00 AM")
        )

        rvMypet.layoutManager = LinearLayoutManager (this ) // GridLayoutManager
        rvMypet.adapter = MypetAdapter(mypet)
    }
}
