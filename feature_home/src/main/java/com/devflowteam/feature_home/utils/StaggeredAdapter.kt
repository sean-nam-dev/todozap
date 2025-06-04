package com.devflowteam.feature_home.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devflowteam.core.utils.TimeFormatManager
import com.devflowteam.domain.model.ToDo
import com.devflowteam.feature_home.R

class StaggeredAdapter(
    private val items: List<ToDo>,
    private val onItemClickAction: (Int) -> Unit
) : RecyclerView.Adapter<StaggeredAdapter.StaggeredViewHolder>() {

    class StaggeredViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.date)
        val textViewText: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggeredViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item_card, parent, false)
        return StaggeredViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaggeredViewHolder, position: Int) {
        val currentItem = items[position]

        holder.textViewDate.text = TimeFormatManager.transform(
            currentItem.date,
            TimeFormatManager.Format.WeekDayMonthFormat
        )
        holder.textViewText.text = currentItem.text
        holder.itemView.setOnClickListener {
            onItemClickAction(currentItem.id)
        }
    }

    override fun getItemCount() = items.size
}