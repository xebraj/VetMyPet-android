package com.vetmypet.vetmypet.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vetmypet.vetmypet.R
import com.vetmypet.vetmypet.model.Vetmypet
import kotlinx.android.synthetic.main.item_mypet.view.*

class MypetAdapter(private val mypet: ArrayList<Vetmypet>)
    : RecyclerView.Adapter<MypetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(Vetmypet: Vetmypet) = with (itemView) {
                tvMypetId.text = context.getString(R.string.item_Vetmypet_id, Vetmypet.id)
                tvDoctorName.text = Vetmypet.doctorName
                tvScheduledDate.text = context.getString(R.string.item_Vetmypet_date, Vetmypet.scheduledDate)
                tvScheduledTime.text = context.getString(R.string.item_Vetmypet_time, Vetmypet.scheduledTime)
            }
    }

    // Inflates XML items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mypet, parent, false)
        )
    }

    // Binds data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Vetmypet = mypet[position]

        holder.bind(Vetmypet)

    }

    // Number of elements
    override fun getItemCount()= mypet.size
}