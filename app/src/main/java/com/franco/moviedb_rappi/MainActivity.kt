package com.franco.moviedb_rappi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.franco.moviedb_rappi.databinding.ActivityMainBinding
import com.franco.moviedb_rappi.presentation.adapter.*
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModel
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MovieViewModelFactory
    @Inject
    lateinit var movieAdapter: MovieAdapter
    @Inject
    lateinit var searchMovieAdapter: SearchMovieAdapter
    @Inject
    lateinit var searchTvAdapter: SearchTvAdapter
    @Inject
    lateinit var tvAdapter: TvAdapter
    @Inject
    lateinit var movieTopRateAdapter: MovieTopRateAdapter
    @Inject
    lateinit var tvTopRateAdapter: TvTopRateAdapter

    lateinit var viewModel : MovieViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        super.onCreate(savedInstanceState)
        setTheme(R.style.MovieDB_Rappi)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentHost) as NavHostFragment
        val navController = navHostFragment.navController

        binding.buttomNavigation.setupWithNavController(
            navController
        )

        viewModel = ViewModelProvider(this, factory)
            .get(MovieViewModel::class.java)
    }
}