package com.example.adebuser.ui.book_ride.select_time

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.adebuser.R
import com.example.adebuser.databinding.RvSelectTimeHourlyBinding
import com.example.adebuser.ui.book_ride.select_time.model.SelectTimeListHourly

public class SelectTimeHourlyAdapter(
    val context: Context,
    var selectTimeListener: SelectTimeHourlyListener,
    var selectTimeHourlyList: ArrayList<SelectTimeListHourly>


) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SelectTimeHourlyAdapter.SelectTimeHourlyViewHolder>() {

    var selectedItem=0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectTimeHourlyViewHolder {
        val binding = RvSelectTimeHourlyBinding.inflate(LayoutInflater.from(context), parent, false)
        return SelectTimeHourlyViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return selectTimeHourlyList.size
    }

    override fun onBindViewHolder(holder: SelectTimeHourlyViewHolder, position: Int) {
        holder.bind(selectTimeHourlyList[position].time,position)

    }

    inner class SelectTimeHourlyViewHolder(private val binding: RvSelectTimeHourlyBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        val tvTime=binding.tvHour

        fun bind(time: String, position: Int) {
            binding.tvHour.text=time.toString().trim()
            binding.tvHour.setOnClickListener {
                selectedItem=position
                notifyDataSetChanged()

                selectTimeListener.onHourlyTime(position,false)

            }

            if(selectedItem==adapterPosition)
            {
                binding.tvHour.setBackgroundResource(R.drawable.drawable_light_blue_curve)
                binding.tvHour.setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            else
            {
                binding.tvHour.setBackgroundResource(R.drawable.drawable_grey_editext)

                //light_blue
                binding.tvHour.setTextColor(ContextCompat.getColor(context, R.color.light_blue))
            }


        }

    }

    interface SelectTimeHourlyListener
    {
        fun onHourlyTime(position: Int,clearAllSelection:Boolean)
    }


}