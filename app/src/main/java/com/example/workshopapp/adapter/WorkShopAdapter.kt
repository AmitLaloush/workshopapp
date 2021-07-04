package com.example.workshopapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.R
//import org.greenrobot.eventbus.EventBus


private class WorkShopDiffUtilCallback() : DiffUtil.ItemCallback<WorkShopModel>() {

    override fun areItemsTheSame(
        oldItem: WorkShopModel,
        newItem: WorkShopModel
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: WorkShopModel,
        newItem: WorkShopModel
    ): Boolean {
        return oldItem.compareTo(newItem) == 0
    }
}

class WorkShopAdapter(val context: Context, val listener: EventListener) : RecyclerView.Adapter<WorkShopAdapter.ViewHolder>() {
    private val asyncListDiffer = AsyncListDiffer(
        this,
        WorkShopDiffUtilCallback()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.work_shop_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workshopItem = asyncListDiffer.currentList[position]
        holder.bind(workshopItem)
    }

    fun setData(newItems: List<WorkShopModel>) {
        asyncListDiffer.submitList(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val mName = view.findViewById<TextView>(R.id.tv_name)
        private val mDescription = view.findViewById<TextView>(R.id.tv_description)
        private val mImage = view.findViewById<ImageView>(R.id.iv_image)

        fun bind(workshop: WorkShopModel) {
            mName.text = workshop.name
            mDescription.text = workshop.description
            Glide.with(context).load(workshop.image).placeholder(R.mipmap.ic_launcher).into(mImage)

            view.setOnClickListener {
                listener.onWorkshopClicked(workshop)
            }
        }
    }

    interface EventListener{
        fun onWorkshopClicked(workShopModel: WorkShopModel)
    }
}

