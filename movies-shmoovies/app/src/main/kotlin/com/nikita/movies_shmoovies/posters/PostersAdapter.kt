package com.nikita.movies_shmoovies.posters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.layoutInflater
import com.nikita.movies_shmoovies.common.utils.load

class PostersAdapter : RecyclerView.Adapter<PostersAdapter.PosterHolder>() {
    var data = ArrayList<PostersPM.Poster>()
    var itemClickAction: ((PostersPM.Poster) -> Unit)? = null
    var topImages = ArrayList<Int>()

    override fun onBindViewHolder(holder: PosterHolder, position: Int) {
        val item = data[position]
        var miniDescribe = item.describe

        val length = 30
        if (miniDescribe.length > length){
            miniDescribe = miniDescribe.substring(0, length-1) + "..."
        }

        holder.image.load(item.image!!)
        holder.title.text = item.title
        holder.date.text = item.date
        holder.describe.text = miniDescribe
        holder.rating.text = item.rating

        if (PostersFragment.filter == PostersFragment.Filter.Top10) {
            holder.topPlace.visibility = View.VISIBLE
            holder.topPlace.setImageResource(topImages[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.posters_item, parent, false)
        val holder = PosterHolder(view)
        view.setOnClickListener { itemClickAction?.invoke(data[holder.adapterPosition]) }
       // itemClickAction!!.invoke()

        topImages.add(R.drawable.top_1)
        topImages.add(R.drawable.top_2)
        topImages.add(R.drawable.top_3)
        topImages.add(R.drawable.top_4)
        topImages.add(R.drawable.top_5)
        topImages.add(R.drawable.top_6)
        topImages.add(R.drawable.top_7)
        topImages.add(R.drawable.top_8)
        topImages.add(R.drawable.top_9)
        topImages.add(R.drawable.top_10)
        return holder
    }

    override fun getItemCount() = data.size

    class PosterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findView<ImageView>(R.id.poster_image)
        val title = itemView.findView<TextView>(R.id.poster_title)
        val describe = itemView.findView<TextView>(R.id.poster_describe)
        val date = itemView.findView<TextView>(R.id.poster_date)
        val rating = itemView.findView<TextView>(R.id.poster_rating)
        val topPlace = itemView.findView<ImageView>(R.id.poster_top_position)
    }


}