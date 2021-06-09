package com.franco.moviedb_rappi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import com.franco.moviedb_rappi.BuildConfig
import com.franco.moviedb_rappi.R
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.databinding.DataListItemBinding

class MovieTopRateAdapter : RecyclerView.Adapter<MovieTopRateAdapter.movieViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<MovieTopRate>(){
        override fun areItemsTheSame(oldItem: MovieTopRate, newItem: MovieTopRate): Boolean {
            return oldItem.backdrop_path == newItem.backdrop_path
        }

        override fun areContentsTheSame(oldItem: MovieTopRate, newItem: MovieTopRate): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieViewHolder {
        val binding = DataListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return movieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: movieViewHolder, position: Int) {
        val movieTopRate = differ.currentList[position]
        holder.bind(movieTopRate)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class movieViewHolder(
        val binding : DataListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieTopRate: MovieTopRate) {
            val image = BuildConfig.IMG_BASE_URL+movieTopRate.poster_path

            binding.imgContainer.load(image) {
                okhttp3.OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(binding.root.context)).build()
                crossfade(false)
                placeholder(R.drawable.ic_rappi_logo_2)
                transformations(RoundedCornersTransformation(30f))
            }

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(movieTopRate)
                }
            }
        }
    }

    private var onItemClickListener : ((MovieTopRate) -> Unit)? = null
    fun setOnclickListener(listener : (MovieTopRate) -> Unit) {
        onItemClickListener = listener
    }
}