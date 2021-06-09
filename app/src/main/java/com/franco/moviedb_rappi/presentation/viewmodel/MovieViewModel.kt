package com.franco.moviedb_rappi.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import com.franco.moviedb_rappi.data.model.*
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.usecase.api.*
import com.franco.moviedb_rappi.domain.usecase.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(
        private val app : Application,
        private val getPopularMovieUseCase : GetPopularMovieUseCase,
        private val getPopularTvUseCase: GetPopularTvUseCase,
        private val getTopRateMovieUseCase: GetTopRateMovieUseCase,
        private val getTopRateTvUseCase: GetTopRateTvUseCase,
        private val getSearchMovieUseCase: GetSearchMovieUseCase,
        private val getSearchTvUseCase: GetSearchTvUseCase,
        private val getMoviesDbUseCase: GetMoviesDbUseCase,
        private val getTvDbUseCase: GetTvDbUseCase,
        private val insertMovieDbUseCase: InsertMovieDbUseCase,
        private val insertTvDbUseCase: InsertTvDbUseCase,
        private val getMoviesTopRateDbUseCase: GetMoviesTopRateDbUseCase,
        private val getTvShowTopRateDbUseCase: GetTvShowTopRateDbUseCase,
        private val insertMovieTopRateDbUseCase: InsertMovieTopRateDbUseCase,
        private val insertTvTopRateDbUseCase: InsertTvTopRateDbUseCase,
        private val searchDataBaseUseCase: SearchDataBaseUseCase,
        private val searchTvDataBaseUseCase: SearchTvDataBaseUseCase
) : AndroidViewModel(app){

    val origin : MutableLiveData<Int> = MutableLiveData()

    //Refere to Movie popular elements
    val searchsMovieResult : MutableLiveData<Resource<APIResponseMovie>> = MutableLiveData()
    val movieData : MutableLiveData<Resource<APIResponseMovie>> = MutableLiveData()
    val movieDataAdd : MutableLiveData<List<Movie>> = MutableLiveData()
    val movieDataAddImp : LiveData<List<Movie>>
        get() = movieDataAdd


    val movieSearchDataAdd : MutableLiveData<List<Movie>> = MutableLiveData()
    val movieSearchDataAddImp : LiveData<List<Movie>>
        get() = movieSearchDataAdd

    val tvSearchDataAdd : MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvSearchDataAddImp : LiveData<List<TvShow>>
        get() = tvSearchDataAdd




    val movieDataDb : MutableLiveData<List<Movie>> = MutableLiveData()
    val movieDataDbImp : LiveData<List<Movie>>
        get() = movieDataDb

    //Refere to Tv popularElements
    val searchsTvResult : MutableLiveData<Resource<APIResponseTv>> = MutableLiveData()
    val tvData : MutableLiveData<Resource<APIResponseTv>> = MutableLiveData()
    val tvDataAdd : MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvDataAddImp : LiveData<List<TvShow>>
        get() = tvDataAdd
    val tvDataDb : MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvDataDbImp : LiveData<List<TvShow>>
        get() = tvDataDb

    //Refere to Movie Top Rate
    val movieRateData : MutableLiveData<Resource<APIResponseMovieTopRate>> = MutableLiveData()
    val movieRateDataAdd : MutableLiveData<List<MovieTopRate>> = MutableLiveData()
    val movieRateDataAddImp : LiveData<List<MovieTopRate>>
        get() = movieRateDataAdd
    val movieRateDataDb : MutableLiveData<List<MovieTopRate>> = MutableLiveData()
    val movieRateDataDbImp : LiveData<List<MovieTopRate>>
        get() = movieRateDataDb

    //Refere to Tv Top Rate
    val tvRateData : MutableLiveData<Resource<APIResponseTvShowTopRate>> = MutableLiveData()
    val tvRateDataAdd : MutableLiveData<List<TvShowTopRate>> = MutableLiveData()
    val tvRateDataAddImp : LiveData<List<TvShowTopRate>>
        get() = tvRateDataAdd
    val tvRateDataDb : MutableLiveData<List<TvShowTopRate>> = MutableLiveData()
    val tvRateDataDbImp : LiveData<List<TvShowTopRate>>
        get() = tvRateDataDb

    val movieSelected : MutableLiveData<Movie> = MutableLiveData()
    val movieSelectedInfo : LiveData<Movie>
        get() = movieSelected

    val movieTopRateSelected : MutableLiveData<MovieTopRate> = MutableLiveData()
    val movieTopRateSelectedInfo : LiveData<MovieTopRate>
        get() = movieTopRateSelected

    //Navigation Utiliti
    val tvSelected : MutableLiveData<TvShow> = MutableLiveData()
    val tvSelectedInfo : LiveData<TvShow>
        get() = tvSelected

    val tvTopRateSelected : MutableLiveData<TvShowTopRate> = MutableLiveData()
    val tvTopRateSelectedInfo : LiveData<TvShowTopRate>
        get() = tvTopRateSelected


    //Utiliti
    val isOnline : MutableLiveData<Int> = MutableLiveData()
    val isOnLineImp : LiveData<Int>
        get() = isOnline

    fun getPopularMovie(language : String, page : Int) = viewModelScope.launch(Dispatchers.IO) {
        movieData.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val movieResult = getPopularMovieUseCase.execute(language, page)
                    movieData.postValue(movieResult)
            } else {
                movieData.postValue(Resource.Error("Connection to Internet is not available"))
            }
        } catch (e : Exception) {
            movieData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getPopularTv(language : String, page : Int) = viewModelScope.launch(Dispatchers.IO) {
        tvData.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val tvResult = getPopularTvUseCase.execute(language, page)
                tvData.postValue(tvResult)
            } else {
                tvData.postValue(Resource.Error("Connection to Internet is not available"))
            }
        } catch (e : Exception) {
            tvData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getTopRateMovie(language : String, page : Int) = viewModelScope.launch(Dispatchers.IO) {
        movieRateData.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val movieResult = getTopRateMovieUseCase.execute(language, page)
                movieRateData.postValue(movieResult)
            } else {
                movieRateData.postValue(Resource.Error("Connection to Internet is not available"))
            }
        } catch (e : Exception) {
            movieRateData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getTopRateTv(language : String, page : Int) = viewModelScope.launch(Dispatchers.IO) {
        tvRateData.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val tvResult = getTopRateTvUseCase.execute(language, page)
                tvRateData.postValue(tvResult)
            } else {
                tvRateData.postValue(Resource.Error("Connection to Internet is not available"))
            }
        } catch (e : Exception) {
            tvRateData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getSearchMovieResult(query : String, page: Int, language : String) = viewModelScope.launch(Dispatchers.IO) {
        searchsMovieResult.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getSearchMovieUseCase.execute(
                    query,
                    page,
                    language
                )
                searchsMovieResult.postValue(response)
            } else {
                searchsMovieResult.postValue(Resource.Error("No internet connection"))
            }
        } catch(e : Exception) {
            searchsMovieResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getSearchTvResult(query : String, page: Int, language : String) = viewModelScope.launch {
        searchsTvResult.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getSearchTvUseCase.execute(
                        query,
                        page,
                        language
                )
                searchsTvResult.postValue(response)
            } else {
                searchsTvResult.postValue(Resource.Error("No internet connection"))
            }
        } catch(e : Exception) {
            searchsTvResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        isOnline.value = 1
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        isOnline.value = 1
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        isOnline.value = 1
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        isOnline.value = 0
        return false
    }

    //Get offline to DB
    fun getTvDb() = liveData {
        getTvDbUseCase.execute().collect {
            emit(it)
            tvDataDb.value = it
        }
    }

    fun getSearchDataBase(searchQuery : String) = liveData {
        searchDataBaseUseCase.execute(searchQuery).collect {
            emit(it)
        }
    }

    fun getSearchTvDataBase(searchQuery : String) = liveData {
        searchTvDataBaseUseCase.execute(searchQuery).collect {
            emit(it)
        }
    }

    fun getMoviesDb() = liveData {
        getMoviesDbUseCase.execute().collect {
            emit(it)
            movieDataDb.value = it
        }
    }

    fun getMoviesTopRateDb() = liveData {
        getMoviesTopRateDbUseCase.execute().collect() {
            emit(it)
        }
    }

    fun getTvShowTopRateDb() = liveData {
        getTvShowTopRateDbUseCase.execute().collect {
            emit(it)
        }
    }

    fun saveMovie(movie: List<Movie>) = viewModelScope.launch {
        insertMovieDbUseCase.execute(movie)
    }

    fun saveTv(tv: List<TvShow>) = viewModelScope.launch {
        insertTvDbUseCase.execute(tv)
    }

    fun saveMovieRate(movieTopRate: List<MovieTopRate>) = viewModelScope.launch {
        insertMovieTopRateDbUseCase.execute(movieTopRate)
    }

    fun saveTvRate(tvShowTopRate: List<TvShowTopRate>) = viewModelScope.launch {
        insertTvTopRateDbUseCase.execute(tvShowTopRate)
    }
}