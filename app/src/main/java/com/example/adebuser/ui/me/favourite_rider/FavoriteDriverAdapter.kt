package com.example.adebuser.ui.me.favourite_rider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.adebuser.databinding.RvFavouriteRiderBinding

public class FavoriteDriverAdapter(
    val context: Context,
    val bookDriver: BookDriver) :
    androidx.recyclerview.widget.RecyclerView.Adapter<FavoriteDriverAdapter.FavouriteRiderViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteRiderViewHolder {
        val binding = RvFavouriteRiderBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavouriteRiderViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: FavouriteRiderViewHolder, position: Int) {
        holder.bind()

    }

    inner class FavouriteRiderViewHolder(private val binding: RvFavouriteRiderBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            binding.btnBook.setOnClickListener {
                bookDriver.onBookClick(adapterPosition)
            }

        }

    }


    interface BookDriver
    {
        fun onBookClick(position: Int)

    }
}