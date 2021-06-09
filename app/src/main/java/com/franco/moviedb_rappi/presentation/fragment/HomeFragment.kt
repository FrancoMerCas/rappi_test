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
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.databinding.FragmentHomeBinding
import com.franco.moviedb_rappi.presentation.adapter.MovieAdapter
import com.franco.moviedb_rappi.presentation.adapter.TvAdapter
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel : MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvAdapter: TvAdapter
    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var listToAddMovie : List<Movie>
    private var arrayListToAddMovie : ArrayList<Movie> = arrayListOf<Movie>()
    private lateinit var listToAddTv : List<TvShow>
    private var arrayListToAddTv : ArrayList<TvShow> = arrayListOf<TvShow>()
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        movieAdapter = (activity as MainActivity).movieAdapter
        tvAdapter = (activity as MainActivity).tvAdapter

        movieAdapter.setOnclickListener {
            viewModel.movieSelected.value = it
            viewModel.origin.value = 1
            findNavController().navigate(
                R.id.action_homeFragment_to_infoFragment
            )
        }

        tvAdapter.setOnclickListener {
            viewModel.tvSelected.value = it
            viewModel.origin.value = 3
            findNavController().navigate(
                R.id.action_homeFragment_to_infoFragment
            )
        }

        initRecyclerView()
        getPopularMovieList()
        getPopularTvList()
    }

    private fun getPopularMovieList() {
        viewModel.getPopularMovie(language, page)

        viewModel.movieData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (viewModel.movieDataAddImp.value?.size != null) {
                            listToAddMovie = viewModel.movieDataAddImp.value!!
                            arrayListToAddMovie = arrayListOf()
                            arrayListToAddMovie.addAll(listToAddMovie)
                            arrayListToAddMovie.addAll(it.results.toList())

                            listToAddMovie = arrayListToAddMovie
                            viewModel.movieDataAdd.value = listToAddMovie
                        } else {
                            viewModel.movieDataAdd.value = it.results.toList()
                            listToAddMovie = viewModel.movieDataAdd.value!!
                        }

                        viewModel.saveMovie(it.results.toList())
                        movieAdapter.differ.submitList(listToAddMovie)

                        pages = it.total_pages
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    getMoviesDb()
                    response.message?.let {
                        //Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun getPopularTvList() {
        viewModel.getPopularTv(language, page)

        viewModel.tvData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    showRecycler()
                    response.data?.let {
                        if (viewModel.tvDataAddImp.value?.size != null) {
                            listToAddTv = viewModel.tvDataAddImp.value!!
                            arrayListToAddTv = arrayListOf()
                            arrayListToAddTv.addAll(listToAddTv)
                            arrayListToAddTv.addAll(it.results.toList())

                            listToAddTv = arrayListToAddTv
                            viewModel.tvDataAdd.value = listToAddTv
                        } else {
                            viewModel.tvDataAdd.value = it.results.toList()
                            listToAddTv = viewModel.tvDataAddImp.value!!
                        }
                        viewModel.saveTv(it.results.toList())
                        tvAdapter.differ.submitList(listToAddTv)

                        pages = it.total_pages
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    getTvDb()
                    response.message?.let {
                        //Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun getMoviesDb() {
        Toast.makeText(activity, "We will to show information with local information", Toast.LENGTH_LONG).show()
        if (viewModel.movieDataDbImp.value?.size == null) {
            showProgressBar()
            hideRecycler()
            viewModel.getMoviesDb().observe(viewLifecycleOwner, {
                movieAdapter.differ.submitList(it)
                hideProgressBar()
                showRecycler()
            })
        }
    }

    private fun getTvDb() {
        if (viewModel.tvDataDbImp.value?.size == null) {
            showProgressBar()
            hideRecycler()
            viewModel.getTvDb().observe(viewLifecycleOwner, {
                tvAdapter.differ.submitList(it)
                hideProgressBar()
                showRecycler()
            })
        }
    }

    private fun initRecyclerView() {
        fragmentHomeBinding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@HomeFragment.onScrollListenerMovie)

        }

        fragmentHomeBinding.rvTv.apply {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@HomeFragment.onScrollListenerTv)
        }
    }

    private fun showProgressBar(){
        viewModel.isOnLineImp.observe(viewLifecycleOwner,
                {
                    if (it == 1) {
                        isLoading = true
                    }
                })
        fragmentHomeBinding.pbHome.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        fragmentHomeBinding.pbHome.visibility = View.INVISIBLE
    }

    private fun hideRecycler() {
        fragmentHomeBinding.apply {
            rvMovies.visibility = View.INVISIBLE
            rvTv.visibility = View.INVISIBLE
            titleMovie.visibility = View.INVISIBLE
            titleTv.visibility = View.INVISIBLE
        }
    }

    private fun showRecycler() {
        fragmentHomeBinding.apply {
            rvMovies.visibility = View.VISIBLE
            rvTv.visibility = View.VISIBLE
            titleMovie.visibility = View.VISIBLE
            titleTv.visibility = View.VISIBLE
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

            val layoutManager = fragmentHomeBinding.rvMovies.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItms = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItms >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                page++
                viewModel.getPopularMovie(language, page)
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

            val layoutManager = fragmentHomeBinding.rvTv.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItms = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItms >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                page++
                viewModel.getPopularTv(language, page)
                isScrolling = false
            }
        }
    }
}