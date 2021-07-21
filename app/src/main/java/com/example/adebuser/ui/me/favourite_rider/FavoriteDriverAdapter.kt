package com.example.adebuser.ui.me.favourite_rider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.adebuser.R
import com.example.adebuser.databinding.RvFavouriteRiderBinding
import com.example.adebuser.ui.me.favourite_rider.response.FavoriteDriver

public class FavoriteDriverAdapter(
    val context: Context,
    val bookDriver: BookDriver,
    val favoriteRiderList: ArrayList<FavoriteDriver>,
    val from: String?
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<FavoriteDriverAdapter.FavouriteRiderViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteRiderViewHolder {
        val binding = RvFavouriteRiderBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavouriteRiderViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return favoriteRiderList.size
    }

    override fun onBindViewHolder(holder: FavouriteRiderViewHolder, position: Int) {
        holder.bind(favoriteRiderList[position])

    }

    inner class FavouriteRiderViewHolder(private val binding: RvFavouriteRiderBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteDriver: FavoriteDriver) {

            if (from == "profile") {
                binding.btnBook.visibility = View.GONE
                binding.tvTime.visibility = View.GONE

            }

            binding.tvName.text = favoriteDriver.name

            Glide.with(context)
                .load(favoriteDriver.profilePic)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.profile)
                )
                .into(binding.ivProfile)

            binding.btnBook.setOnClickListener {
                bookDriver.onBookClick(adapterPosition, favoriteDriver.favoriteRef)
            }

        }

    }


    interface BookDriver {
        fun onBookClick(position: Int, userRef: String)

    }
}