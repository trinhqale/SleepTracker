package com.example.sleeptracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

const val ITEM_EXTRA ="ITEM_EXTRA"
private const val TAG = "ItemAdapter"

class ItemAdapter(private val context: Context, private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>()
{
    interface OnItemLongClickListener {
        fun onItemLongClick(itemView: View?, position: Int)
    }

    // Define listener member variable
    private lateinit var listener: OnItemLongClickListener

    // Define the method that allows the parent activity or fragment to define the listener

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val dayTextView = itemView.findViewById<TextView>(R.id.day)
        val durationTextView = itemView.findViewById<TextView>(R.id.duration)
        val isNap = itemView.findViewById<TextView>(R.id.sleep)
        init{
            itemView.setOnLongClickListener(){
                val position = absoluteAdapterPosition
                if(position != RecyclerView.NO_POSITION)
                {
                    listener.onItemLongClick(itemView, position)
                }
                true
        }
        }
        fun bind(item: Item)
        {
            dayTextView.text = "Day " + item.day.toString()
            durationTextView.text = item.duration.toString() + "hours"
            if(item.isNap == true)
            {
                isNap.text = "Nap"
            }
            else
            {
                isNap.text = "Sleep"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val context = parent.context
        val inflater =LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item, parent, false)
        return ViewHolder(context, itemView)
    }

    override fun onBindViewHolder(viewHolder: ItemAdapter.ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = items.size
}