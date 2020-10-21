package com.example.weatherprototype.details.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherprototype.databinding.DetailsViewpagerItemBinding
import com.example.weatherprototype.details.list.DetailsListAdapter
import com.example.weatherprototype.details.list.DetailsListItem

class DetailsPagerAdapter : RecyclerView.Adapter<PagerViewHolder>() {

    private val items = mutableListOf<DetailsPagerItem>()
    fun updateItems(newItems: List<DetailsPagerItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(DetailsViewpagerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    data class DetailsPagerItem(
        val listItems: List<DetailsListItem>
    )
}

class PagerViewHolder(private val viewBinding: DetailsViewpagerItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private val weatherAdapter = DetailsListAdapter()

    fun bind(item: DetailsPagerAdapter.DetailsPagerItem) {
        viewBinding.content.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(viewBinding.root.context)
        }
        weatherAdapter.updateItems(item.listItems)
    }
}