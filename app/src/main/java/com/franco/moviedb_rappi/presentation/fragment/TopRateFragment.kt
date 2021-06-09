package com.franco.moviedb_rappi.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franco.moviedb_rappi.MainActivity
import com.franco.moviedb_rappi.R
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.model.TvShowTopRate
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.databinding.FragmentTopRateBinding
import com.franco.moviedb_rappi.presentation.adapter.MovieAdapter
import com.franco.moviedb_rappi.presentation.adapter.MovieTopRateAdapter
import com.franco.moviedb_rappi.presentation.adapter.TvAdapter
import com.franco.moviedb_rappi.presentation.adapter.TvTopRateAdapter
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModel

class TopRateFragment : Fragment() {
    private lateinit var viewModel : MovieViewModel
    private lateinit var movieTopRateAdapter : MovieTopRateAdapter
    private lateinit var tvTopRateAdapter: TvTopRateAdapter
    private lateinit var topRateBinding: FragmentTopRateBinding
    private lateinit var listToAddMovie : List<MovieTopRate>
    private var arrayListToAddMovie : ArrayList<MovieTopRate> = arrayListOf<MovieTopRate>()
    private lateinit var listToAddTv : List<TvShowTopRate>
    private var arrayListToAddTv : ArrayList<TvShowTopRate> = arrayListOf<TvShowTopRate>()
    private var language = "es-ES"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topRateBinding = FragmentTopRateBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        movieTopRateAdapter = (activity as MainActivity).movieTopRateAdapter
        tvTopRateAdapter = (activity as MainActivity).tvTopRateAdapter

        movieTopRateAdapter.setOnclickListener {
            viewModel.movieTopRateSelected.value = it
            viewModel.origin.value = 2
            findNavController().navigate(
                R.id.action_topRateFragment_to_infoFragment
            )
        }

        tvTopRateAdapter.setOnclickListener {
            viewModel.tvTopRateSelected.value = it
            viewModel.origin.value = 4
            findNavController().navigate(
                R.id.action_topRateFragment_to_infoFragment
            )
        }

        initRecyclerView()
        getTopRateTvList()
        getTopRateMovieList()
    }

    private fun getTopRateMovieList() {
        viewModel.getTopRateMovie(language, page)

        viewModel.movieRateData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    showRecycler()
                    response.data?.let {
                        if(viewModel.movieRateDataAddImp.value?.size != null){
                            listToAddMovie = viewModel.movieRateDataAddImp.value!!
                            arrayListToAddMovie = arrayListOf()
                            arrayListToAddMovie.addAll(listToAddMovie)
                            arrayListToAddMovie.addAll(it.results.toList())

                            listToAddMovie = arrayListToAddMovie
                            viewModel.movieRateDataAdd.value = listToAddMovie
                        } else {
                            viewModel.movieRateDataAdd.value = it.results.toList()
                            listToAddMovie = viewModel.movieRateDataAdd.value!!
                        }

                        viewModel.saveMovieRate(it.results.toList())
                        movieTopRateAdapter.differ.submitList(listToAddMovie)

                        pages = it.total_pages
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    getMoviesTopRateDb()
                    response.message?.let {
                        //Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }

                }
            }
        })
    }

    private fun getTopRateTvList() {
        viewModel.getTopRateTv(language, page)

        viewModel.tvRateData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    showRecycler()
                    response.data?.let {
                        if(viewModel.tvRateDataAddImp.value?.size != null) {
                            listToAddTv = viewModel.tvRateDataAddImp.value!!
                            arrayListToAddTv = arrayListOf()
                            arrayListToAddTv.addAll(listToAddTv)
                            arrayListToAddTv.addAll(it.results.toList())

                            listToAddTv = arrayListToAddTv
                            viewModel.tvRateDataAdd.value = listToAddTv
                        } else {
                            viewModel.tvRateDataAdd.value = it.results.toList()
                            listToAddTv = viewModel.tvRateDataAddImp.value!!
                        }
                        viewModel.saveTvRate(it.results.toList())
                        tvTopRateAdapter.differ.submitList(listToAddTv)

                        pages = it.total_pages
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    getTvShowTopRateDb()
                    response.message?.let {
                        //Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }


    private fun getMoviesTopRateDb() {
        Toast.makeText(activity, "We will to show information with local information", Toast.LENGTH_LONG).show()
        if (viewModel.movieRateDataDbImp.value?.size == null) {
            showProgressBar()
            hideRecycler()
            viewModel.getMoviesTopRateDb().observe(viewLifecycleOwner, {
                movieTopRateAdapter.differ.submitList(it)
                hideProgressBar()
                showRecycler()
            })
        }
    }


    private fun getTvShowTopRateDb() {
        if (viewModel.tvRateDataDbImp.value?.size == null) {
            showProgressBar()
            hideRecycler()
            viewModel.getTvShowTopRateDb().observe(viewLifecycleOwner, {
                tvTopRateAdapter.differ.submitList(it)
                hideProgressBar()
                showRecycler()
            })
        }
    }

    private fun initRecyclerView() {
        topRateBinding.rvMoviesTopRate.apply {
            adapter = movieTopRateAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@TopRateFragment.onScrollListenerMovie)
        }

        topRateBinding.rvTvTopRate.apply {
            adapter = tvTopRateAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@TopRateFragment.onScrollListenerTv)
        }
    }

    private fun showProgressBar(){
        topRateBinding.pbTopRate.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        topRateBinding.pbTopRate.visibility = View.INVISIBLE
    }

    private fun hideRecycler() {
        topRateBinding.apply {
            rvMoviesTopRate.visibility = View.INVISIBLE
            rvTvTopRate.visibility = View.INVISIBLE
            titleMovieTopRate.visibility = View.INVISIBLE
            titleTvTopRate.visibility = View.INVISIBLE
        }
    }

    private fun showRecycler() {
        topRateBinding.apply {
            rvMoviesTopRate.visibility = View.VISIBLE
            rvTvTopRate.visibility = View.VISIBLE
            titleMovieTopRate.visibility = View.VISIBLE
            titleTvTopRate.visibility = View.VISIBLE
        }
    }


    private val onScrollListenerMovie = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = topRateBinding.rvMoviesTopRate.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItms = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItms >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                page++
                viewModel.getTopRateMovie(language, page)
                isScrolling = false
            }
        }
    }

    private val onScrollListenerTv = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = topRateBinding.rvTvTopRate.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItms = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItms >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                page++
                viewModel.getTopRateTv(language, page)
                isScrolling = false
            }
        }
    }
}