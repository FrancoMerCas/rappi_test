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
import com.franco.moviedb_rappi.data.model.TvShowTopRate
import com.franco.moviedb_rappi.databinding.DataListItemBinding

class TvTopRateAdapter : RecyclerView.Adapter<TvTopRateAdapter.tvViewHolder>(){

    private val callback = object : DiffUtil.ItemCallback<TvShowTopRate>(){
        override fun areItemsTheSame(oldItem: TvShowTopRate, newItem: TvShowTopRate): Boolean {
            return oldItem.poster_path == newItem.poster_path
        }

        override fun areContentsTheSame(oldItem: TvShowTopRate, newItem: TvShowTopRate): Boolean {
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
        val tvTopRate = differ.currentList[position]
        holder.bind(tvTopRate)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class tvViewHolder(
        val binding : DataListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowTopRate : TvShowTopRate) {
            val image = BuildConfig.IMG_BASE_URL+tvShowTopRate.poster_path

            binding.imgContainer.load(image) {
                okhttp3.OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(binding.root.context)).build()
                crossfade(false)
                placeholder(R.drawable.ic_rappi_logo_2)
                transformations(RoundedCornersTransformation(30f))
            }

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(tvShowTopRate)
                }
            }
        }
    }

    private var onItemClickListener : ((TvShowTopRate) -> Unit)? = null
    fun setOnclickListener(listener : (TvShowTopRate) -> Unit) {
        onItemClickListener = listener
    }
}