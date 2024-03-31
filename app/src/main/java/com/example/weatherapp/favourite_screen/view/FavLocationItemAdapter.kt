package com.example.weatherapp.favourite_screen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FavLocatoinItemBinding
import com.example.weatherapp.model.FavouriteLocation


class FavLocationItemAdapter(
    private val onItemClick: (FavouriteLocation) -> Unit,
    private val onDeleteClicked: (FavouriteLocation) -> Unit
) :
    ListAdapter<FavouriteLocation, FavLocationItemAdapter.FavouriteLocationViewHolder>(
        FavouriteLocationDiffUtil()
    ) {
    private lateinit var binding: FavLocatoinItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteLocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fav_locatoin_item, parent, false)
        return FavouriteLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteLocationViewHolder, position: Int) {
        val currentLocation = getItem(position)
        holder.binding.favLocationModel = currentLocation
        binding.imgVDeleteFavLocation.setOnClickListener { onDeleteClicked.invoke(currentLocation) }
        binding.root.setOnClickListener { onItemClick.invoke(currentLocation) }
    }

    inner class FavouriteLocationViewHolder(val binding: FavLocatoinItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class FavouriteLocationDiffUtil : DiffUtil.ItemCallback<FavouriteLocation>() {
        override fun areItemsTheSame(
            oldItem: FavouriteLocation,
            newItem: FavouriteLocation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavouriteLocation,
            newItem: FavouriteLocation
        ): Boolean {
            return oldItem == newItem
        }

    }

}