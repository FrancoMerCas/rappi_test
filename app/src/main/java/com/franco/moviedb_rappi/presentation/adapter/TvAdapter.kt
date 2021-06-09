package com.franco.moviedb_rappi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import com.franco.moviedb_rappi.BuildConfig
import com.franco.moviedb_rappi.R
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.databinding.DataListItemBinding

class TvAdapter : RecyclerView.Adapter<TvAdapter.tvViewHolder>(){

    private val callback = object : DiffUtil.ItemCallback<TvShow>(){
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.poster_path == newItem.poster_path
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tvViewHolder {
        val binding = DataListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return tvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: tvViewHolder, position: Int) {
        val tv = differ.currentList[position]
        holder.bind(tv)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class tvViewHolder(
        val binding : DataListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv : TvShow) {
            val image = BuildConfig.IMG_BASE_URL+tv.poster_path

            binding.imgContainer.load(image) {
                okhttp3.OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(binding.root.context)).build()
                crossfade(false)
                placeholder(R.drawable.ic_rappi_logo_2)
                transformations(RoundedCornersTransformation(30f))
            }

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(tv)
                }
            }
        }
    }

    private var onItemClickListener : ((TvShow) -> Unit)? = null
    fun setOnclickListener(listener : (TvShow) -> Unit) {
        onItemClickListener = listener
    }
}