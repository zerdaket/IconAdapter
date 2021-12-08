package com.zerdaket.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zerdaket.example.R
import com.zerdaket.example.data.AppInfo

/**
 * @author zerdaket
 * @date 2021/12/7 10:06 下午
 */
class AppAdapter: RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    private var appList: List<AppInfo>? = null

    fun setAppList(list: List<AppInfo>) {
        appList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_icon, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val info = appList?.get(holder.bindingAdapterPosition) ?: return
        holder.imageView.setImageBitmap(info.icon)
        holder.textView.text = info.name
    }

    override fun getItemCount(): Int = appList?.size ?: 0

    class AppViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.name)
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }
}