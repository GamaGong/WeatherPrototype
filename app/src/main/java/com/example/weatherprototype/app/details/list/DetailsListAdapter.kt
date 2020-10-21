package com.example.weatherprototype.app.details.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherprototype.R
import com.example.weatherprototype.app.IconUrl
import com.example.weatherprototype.databinding.DetailsListItemBinding
import java.time.LocalDate

class DetailsListAdapter : RecyclerView.Adapter<DetailsListViewHolder>() {

    private val items = mutableListOf<DetailsListItem>()

    fun updateItems(newItems: List<DetailsListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsListViewHolder {
        return DetailsListViewHolder(
            DetailsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailsListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class DetailsListViewHolder(private val viewBinding: DetailsListItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(data: DetailsListItem) {
        viewBinding.date.text = String.format(
            viewBinding.root.context.getString(R.string.day_month),
            data.date.dayOfMonth,
            data.date.month.name
        )
        viewBinding.maxTemperature.text = data.maxTemperature
        viewBinding.minTemperature.text = data.minTemperature
        viewBinding.dayOfWeek.text = data.date.dayOfWeek.name
        Glide.with(viewBinding.root)
            .load(data.weatherIconUrl.large)
            .into(viewBinding.weatherIcon)
    }
}

data class DetailsListItem(
    val weatherIconUrl: IconUrl,
    val maxTemperature: String,
    val minTemperature: String,
    val date: LocalDate
)