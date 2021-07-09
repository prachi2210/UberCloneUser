package com.example.adebuser.ui.book_ride.add_coupons

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.adebuser.databinding.RvCouponsBinding

class CouponAdapter(
    val context: Context
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CouponAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvCouponsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

    }

    inner class ViewHolder(private val binding: RvCouponsBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind() {


        }

    }
}