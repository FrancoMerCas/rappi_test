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
import com.franco.moviedb_rappi.databinding.DataListItemBinding

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.movieViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.backdrop_path == newItem.backdrop_path
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class movieViewHolder(
        val binding : DataListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie : Movie) {
            val image = BuildConfig.IMG_BASE_URL+movie.poster_path

            binding.imgContainer.load(image) {
                okhttp3.OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(binding.root.context)).build()
                crossfade(false)
                placeholder(R.drawable.ic_rappi_logo_2)
                transformations(RoundedCornersTransformation(30f))
            }

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }
        }
    }

    private var onItemClickListener : ((Movie) -> Unit)? = null
    fun setOnclickListener(listener : (Movie) -> Unit) {
        onItemClickListener = listener
    }
}