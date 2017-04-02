package com.nikita.movies_shmoovies.info

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.layoutInflater
import com.nikita.movies_shmoovies.common.utils.load

class InfoCreditAdapter : RecyclerView.Adapter<InfoCreditAdapter.InfoCastHolder>(){

    var data = ArrayList<Actor>()

    class InfoCastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findView<ImageView>(R.id.info_cast_image)
        val name = itemView.findView<TextView>(R.id.info_cast_name)
        val role = itemView.findView<TextView>(R.id.info_cast_role)
    }

    override fun onBindViewHolder(holder: InfoCastHolder, position: Int) {
        val item = data[position]
        if (item.image == null){
            holder.image.load(holder.itemView.context.getString(R.string.url_unknown_people), true)
        } else holder.image.load(item.image)
        holder.name.text = item.name
        holder.role.text = item.role
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoCastHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.info_cast_item, parent, false)
        val holder = InfoCastHolder(view)

        return holder
    }

    override fun getItemCount() = data.size

}
