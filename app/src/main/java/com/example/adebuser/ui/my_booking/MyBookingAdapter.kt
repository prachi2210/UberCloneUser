package com.example.adebuser.ui.my_booking

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.adebuser.databinding.RvFavouriteRiderBinding
import com.example.adebuser.databinding.RvMyBookingBinding


public class MyBookingAdapter(
    val context: Context,
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MyBookingAdapter.MyBookingViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyBookingViewHolder {
        val binding = RvMyBookingBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyBookingViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyBookingViewHolder, position: Int) {
        holder.bind()

    }

    inner class MyBookingViewHolder(private val binding: RvMyBookingBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind() {


        }

    }
}