package com.example.adebuser.ui.book_ride.select_time

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.databinding.RvSelectTimeBinding
import com.example.adebuser.extensions.hide
import com.example.adebuser.extensions.show


public class SelectTimeAdapter(
    val context: Context,
    var selectTimeListener: SelectTimeListener,
    var selectTimeList: ArrayList<SelectTimeList>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SelectTimeAdapter.SelectTimeViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectTimeViewHolder {
        val binding = RvSelectTimeBinding.inflate(LayoutInflater.from(context), parent, false)
        return SelectTimeViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return selectTimeList.size
    }

    override fun onBindViewHolder(holder: SelectTimeViewHolder, position: Int) {
        holder.bind(selectTimeList[position], position)
        holder.frameImg.setOnClickListener {

            if (holder.ivAdd.visibility == View.VISIBLE) {
                selectTimeListener.onAddPickup(position)
            } else if (holder.ivMinus.visibility == View.VISIBLE) {
                selectTimeListener.onRemovePickup(position)
            }

        }

    }

    inner class SelectTimeViewHolder(private val binding: RvSelectTimeBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        val frameImg = binding.frameImg
        val ivAdd = binding.ivAdd
        val ivMinus = binding.ivMinus

        fun bind(selectTimeList: SelectTimeList, position: Int) {
            if (position == 0) {
                binding.ivAdd.show()
                binding.ivMinus.hide()
            } else {
                binding.ivMinus.show()
                binding.ivAdd.hide()
            }

        }

    }

    interface SelectTimeListener {
        fun onAddPickup(position: Int)
        fun onRemovePickup(position: Int)
    }
}