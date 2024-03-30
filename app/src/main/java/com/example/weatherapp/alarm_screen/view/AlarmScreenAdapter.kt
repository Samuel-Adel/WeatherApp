package com.example.weatherapp.alarm_screen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.AlarmItemBinding
import com.example.weatherapp.model.AlarmItem


class AlarmScreenAdapter(
    private val onDeleteClicked: (AlarmItem) -> Unit
) :
    ListAdapter<AlarmItem, AlarmScreenAdapter.AlarmScreenAdapterViewHolder>(
        AlarmsDiffUtil()
    ) {
    private lateinit var binding: AlarmItemBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmScreenAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.alarm_item, parent, false)
        return AlarmScreenAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmScreenAdapterViewHolder, position: Int) {
        val currentLocation = getItem(position)
        holder.binding.alamModel = currentLocation
        binding.imgVDeleteAlarm.setOnClickListener { onDeleteClicked.invoke(currentLocation) }
    }

    inner class AlarmScreenAdapterViewHolder(val binding: AlarmItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class AlarmsDiffUtil : DiffUtil.ItemCallback<AlarmItem>() {
        override fun areItemsTheSame(
            oldItem: AlarmItem,
            newItem: AlarmItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AlarmItem,
            newItem: AlarmItem
        ): Boolean {
            return oldItem == newItem
        }

    }

}