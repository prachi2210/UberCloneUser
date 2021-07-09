package com.example.adebuser.ui.book_ride.select_time

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.adebuser.databinding.RvSelectTimeHourlyBinding

public class SelectTimeHourlyAdapter(
    val context: Context,
    var timePeriodArray: Array<String>,
   var selectTimeListener:SelectTimeHourlyListener

    ) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SelectTimeHourlyAdapter.SelectTimeHourlyViewHolder>() {





    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectTimeHourlyViewHolder {
        val binding = RvSelectTimeHourlyBinding.inflate(LayoutInflater.from(context), parent, false)
        return SelectTimeHourlyViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return timePeriodArray.size
    }

    override fun onBindViewHolder(holder: SelectTimeHourlyViewHolder, position: Int) {

        holder.bind(timePeriodArray[position],position)


    }

    inner class SelectTimeHourlyViewHolder(private val binding: RvSelectTimeHourlyBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {


        fun bind(time: String, position: Int) {
            binding.tvHour.text=time.toString().trim()
            binding.tvHour.setOnClickListener {
                selectTimeListener.onHourlyTime(position)
            }


        }

    }

    interface SelectTimeHourlyListener
    {
        fun onHourlyTime(position: Int)
    }


}