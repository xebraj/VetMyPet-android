package com.vetmypet.vetmypet.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vetmypet.vetmypet.R
import com.vetmypet.vetmypet.model.Vetmypet
import kotlinx.android.synthetic.main.item_mypet.view.*

class MypetAdapter
    : RecyclerView.Adapter<MypetAdapter.ViewHolder>() {

     var mypet = ArrayList<Vetmypet>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(Vetmypet: Vetmypet) = with (itemView) {
                tvMypetId.text = context.getString(R.string.item_Vetmypet_id, Vetmypet.id)
                tvDoctorName.text = Vetmypet.doctor.name
                tvScheduledDate.text = context.getString(R.string.item_Vetmypet_date, Vetmypet.scheduledDate)
                tvScheduledTime.text = context.getString(R.string.item_Vetmypet_time, Vetmypet.scheduledTime)

            tvSpecialty.text = Vetmypet.specialty.name
            tvDescription.text = Vetmypet.description
            tvStatus.text = Vetmypet.status
            tvType.text = Vetmypet.type
            tvCreateAt.text = context.getString(R.string.item_Vetmypet_created_at, Vetmypet.createdAt)

            ibExpand.setOnClickListener {
                if (linearLayoutDetails.visibility == View.VISIBLE) {
                    linearLayoutDetails.visibility = View.GONE
                    ibExpand.setImageResource(R.drawable.ic_expand_more)
                } else {
                    linearLayoutDetails.visibility = View.VISIBLE
                    ibExpand.setImageResource(R.drawable.ic_expand_less)
                }

            }

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