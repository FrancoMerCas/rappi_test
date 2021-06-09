package com.franco.moviedb_rappi.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franco.moviedb_rappi.MainActivity
import com.franco.moviedb_rappi.R
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.databinding.FragmentSearchBinding
import com.franco.moviedb_rappi.presentation.adapter.MovieAdapter
import com.franco.moviedb_rappi.presentation.adapter.SearchMovieAdapter
import com.franco.moviedb_rappi.presentation.adapter.SearchTvAdapter
import com.franco.moviedb_rappi.presentation.adapter.TvAdapter
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private lateinit var searchTvAdapter: SearchTvAdapter
    private var language = "es-ES"
    private var pageMovie = 1
    private var pageTv = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pagesMovie = 0
    private var pagesTv = 0
    private var query = ""
    private var cicle = 0
    private var listToAddMovie : List<Movie> = arrayListOf()
    private var arrayListToAddMovie : ArrayList<Movie> = arrayListOf<Movie>()
    private lateinit var listToAddTv : List<TvShow>
    private var arrayListToAddTv : ArrayList<TvShow> = arrayListOf<TvShow>()
    private val emptyListMovie : List<Movie> = arrayListOf()
    private val emptyListTv : List<TvShow> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchBinding = FragmentSearchBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        searchMovieAdapter = (activity as MainActivity).searchMovieAdapter
        searchTvAdapter = (activity as MainActivity).searchTvAdapter
        typingSearchView()
        initRecyclerView()
        showRecycler(false)

        searchMovieAdapter.setOnclickListener {
            viewModel.movieSelected.value = it
            viewModel.origin.value = 5
            findNavController().navigate(
                    R.id.action_searchFragment_to_infoFragment
            )
        }

        searchTvAdapter.setOnclickListener {
            viewModel.tvSelected.value = it
            viewModel.origin.value = 6
            findNavController().navigate(
                    R.id.action_searchFragment_to_infoFragment
            )
        }
    }

    private fun typingSearchView() {
        fragmentSearchBinding.svSearch.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        viewSearchMovieSingleResult(p0.toString())
                        viewSearchTvResult(p0.toString())
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                       query = p0.toString()
                        MainScope().launch {
                            delay(200)
                             if(p0?.length!! < 1) {
                                listToAddMovie = listOf()
                                searchMovieAdapter.differ.submitList(emptyListMovie)
                                searchTvAdapter.differ.submitList(emptyListTv)
                                showRecycler(false)
                            }
                        }
                        return false
                    }
                })

        fragmentSearchBinding.svSearch.setOnCloseListener(
            object : SearchView.OnCloseListener{
                override fun onClose(): Boolean {

                    listToAddMovie = listOf()
                    searchMovieAdapter.differ.submitList(emptyListMovie)
                    searchTvAdapter.differ.submitList(emptyListTv)
                    showRecycler(false)
                    return false
                }
            }
        )
    }

    fun viewSearchMovieResult(p0 : String) {
        showProgressBar()
        cicle = 0

        //println("Return: ${viewModel.sear}")
        viewModel.searchsMovieResult.observe(viewLifecycleOwner, { response ->

            when (response) {
                is Resource.Success -> {

                    hideProgressBar()
                    showRecycler(true)

                    response.data?.let {
                        if(listToAddMovie?.size == 0) {
                            listToAddMovie = it.results.toList()
                        }
                    }

                    /*response.data?.let {
                        println("Respònses : ${it}")
                        println("cicle ${cicle}")
                        if (listToAddMovie?.size > 0) {
                            arrayListToAddMovie = arrayListOf()
                            arrayListToAddMovie.addAll(listToAddMovie)
                            arrayListToAddMovie.addAll(it.results.toList())

                            listToAddMovie = arrayListToAddMovie
                        } else {
                            listToAddMovie = it.results.toList()
                        }
                        //viewModel.saveMovie(it.results.toList())
                        searchMovieAdapter.differ.submitList(listToAddMovie)

                        pagesMovie = it.total_pages
                        isLastPage = pageMovie == pagesMovie
                        println("how cicles ${(cicle++) == query.length - 1}")

                    }*/

                }
                is Resource.Loading -> {
                    showRecycler(false)
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    searchMoviesDb(p0)
                    response.message?.let {
                        //Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    fun viewSearchTvResult(p0 : String) {
        viewModel.getSearchTvResult(p0, pageTv, language)
        viewModel.searchsTvResult.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    showRecycler(true)
                    response.data?.let {
                        if (viewModel.tvSearchDataAdd.value?.size != null) {
                            listToAddTv = viewModel.tvSearchDataAddImp.value!!
                            arrayListToAddTv = arrayListOf()
                            arrayListToAddTv.addAll(listToAddTv)
                            arrayListToAddTv.addAll(it.results.toList())

                            listToAddTv = arrayListToAddTv
                            viewModel.tvSearchDataAdd.value = listToAddTv
                        } else {
                            viewModel.tvSearchDataAdd.value = it.results.toList()
                            listToAddTv = viewModel.tvSearchDataAdd.value!!
                        }

                        viewModel.saveTv(it.results.toList())
                        searchTvAdapter.differ.submitList(listToAddTv)

                        pagesTv = it.total_pages
                        isLastPage = pageTv == pagesTv
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    searchTvDb(p0)
                    response.message?.let {
                        Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    fun viewSearchMovieSingleResult(p0 : String) {
        viewModel.getSearchMovieResult(p0, pageTv, language)
        viewModel.searchsMovieResult.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    showRecycler(true)
                    response.data?.let {

                    if (viewModel.movieSearchDataAdd.value?.size != null) {
                        listToAddMovie = viewModel.movieSearchDataAddImp.value!!
                        arrayListToAddMovie = arrayListOf()
                        arrayListToAddMovie.addAll(listToAddMovie)
                        arrayListToAddMovie.addAll(it.results.toList())

                        listToAddMovie = arrayListToAddMovie
                        viewModel.movieSearchDataAdd.value = listToAddMovie
                    } else {
                        viewModel.movieSearchDataAdd.value = it.results.toList()
                        listToAddMovie = viewModel.movieSearchDataAdd.value!!
                    }

                    viewModel.saveMovie(it.results.toList())
                    searchMovieAdapter.differ.submitList(listToAddMovie)

                    pagesMovie = it.total_pages
                    isLastPage = pageMovie == pagesMovie
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showRecycler(true)
                    searchMoviesDb(p0)
                    response.message?.let {
                        Toast.makeText(activity, "An error ocurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun searchMoviesDb(p0: String) {
        Toast.makeText(activity, "We will to show information with local information", Toast.LENGTH_LONG).show()
            viewModel.getSearchDataBase(p0).observe(viewLifecycleOwner, {
                showRecycler(true)
                searchMovieAdapter.differ.submitList(it)
            })

    }

    private fun searchTvDb(p0: String) {
        Toast.makeText(activity, "We will to show information with local information", Toast.LENGTH_LONG).show()
        viewModel.getSearchTvDataBase(p0).observe(viewLifecycleOwner, {
            searchTvAdapter.differ.submitList(it)
        })

    }

    private fun initRecyclerView() {
        fragmentSearchBinding.rvMovieSearch.apply {
            adapter = searchMovieAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@SearchFragment.onScrollListenerMovie)
        }

        fragmentSearchBinding.rvTvSearch.apply {
            adapter = searchTvAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@SearchFragment.onScrollListenerTv)
        }
    }

    private fun showProgressBar(){
        isLoading = true
        fragmentSearchBinding.apply {
            pbSearch.visibility = View.VISIBLE
            }
        showRecycler(false)
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentSearchBinding.apply {
            pbSearch.visibility = View.INVISIBLE
        }
    }

    private fun showRecycler(status : Boolean) {
        var visible = View.VISIBLE
        if (!status) visible = View.INVISIBLE

        fragmentSearchBinding.apply {
                titleMovieSearch.visibility = visible
                rvMovieSearch.visibility = visible
                titleTvSearch.visibility = visible
                rvTvSearch.visibility = visible
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

            val layoutManager = fragmentSearchBinding.rvMovieSearch.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItms = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItms >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                pageMovie++
                viewModel.getSearchMovieResult(query, pageMovie, language)
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

            val layoutManager = fragmentSearchBinding.rvTvSearch.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItms = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItms >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                pageMovie++
                viewModel.getSearchTvResult(query, pageMovie, language)
                isScrolling = false
            }
        }
    }
}