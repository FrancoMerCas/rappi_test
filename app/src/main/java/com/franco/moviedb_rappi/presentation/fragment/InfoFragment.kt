package com.franco.moviedb_rappi.presentation.fragment


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.navigation.fragment.findNavController
import coil.load
import com.franco.moviedb_rappi.BuildConfig
import com.franco.moviedb_rappi.MainActivity
import com.franco.moviedb_rappi.R
import com.franco.moviedb_rappi.databinding.FragmentInfoBinding
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_info.*
import java.util.*

class InfoFragment : Fragment() {
    private lateinit var viewModel : MovieViewModel
    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var urlBackdrop : String
    private lateinit var urlPoster : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        val mediaController = MediaController(activity as MainActivity)
        mediaController.setAnchorView(fragmentInfoBinding.videoExample)

        val onLineUri= Uri.parse("https://cdn.videvo.net/videvo_files/video/free/2021-04/small_watermarked/210329_06B_Bali_1080p_013_preview.webm")
        videoExample.setMediaController(mediaController)
        videoExample.setVideoURI(onLineUri)
        videoExample.start()

        initInfo()
    }

    private fun initInfo() {
        viewModel.origin.observe(viewLifecycleOwner, {
            if (it == 1 || it == 5) {
                urlBackdrop =
                    BuildConfig.IMG_BASE_URL + viewModel.movieSelectedInfo.value?.backdrop_path
                urlPoster =
                    BuildConfig.IMG_BASE_URL + viewModel.movieSelectedInfo.value?.poster_path

                fragmentInfoBinding.apply {
                    imgHeaderPoster.load(urlBackdrop)
                    imgPosterInfo.load(urlPoster)
                    titleInfo.text = viewModel.movieSelectedInfo.value?.title
                    avrData.text =
                        "Vote average ${viewModel.movieSelectedInfo.value?.vote_average.toString()}"
                    textInfo.text = viewModel.movieSelectedInfo.value?.overview
                }
            } else if (it == 2){
                urlBackdrop =
                        BuildConfig.IMG_BASE_URL + viewModel.movieTopRateSelectedInfo.value?.backdrop_path
                urlPoster =
                        BuildConfig.IMG_BASE_URL + viewModel.movieTopRateSelectedInfo.value?.poster_path

                fragmentInfoBinding.apply {
                    imgHeaderPoster.load(urlBackdrop)
                    imgPosterInfo.load(urlPoster)
                    titleInfo.text = viewModel.movieTopRateSelectedInfo.value?.title
                    avrData.text =
                            "Vote average ${viewModel.movieTopRateSelectedInfo.value?.vote_average.toString()}"
                    textInfo.text = viewModel.movieTopRateSelectedInfo.value?.overview
                }
            } else if (it == 3 || it == 6) {
                urlBackdrop =
                    BuildConfig.IMG_BASE_URL + viewModel.tvSelectedInfo.value?.backdrop_path
                urlPoster =
                    BuildConfig.IMG_BASE_URL + viewModel.tvSelectedInfo.value?.poster_path

                fragmentInfoBinding.apply {
                    imgHeaderPoster.load(urlBackdrop)
                    imgPosterInfo.load(urlPoster)
                    titleInfo.text = viewModel.tvSelectedInfo.value?.original_name
                    avrData.text =
                        "Vote average ${viewModel.tvSelectedInfo.value?.vote_average.toString()}"
                    textInfo.text = viewModel.tvSelectedInfo.value?.overview
                }
            } else if (it == 4) {
                urlBackdrop =
                        BuildConfig.IMG_BASE_URL + viewModel.tvTopRateSelectedInfo.value?.backdrop_path
                urlPoster =
                        BuildConfig.IMG_BASE_URL + viewModel.tvTopRateSelectedInfo.value?.poster_path

                fragmentInfoBinding.apply {
                    imgHeaderPoster.load(urlBackdrop)
                    imgPosterInfo.load(urlPoster)
                    titleInfo.text = viewModel.tvTopRateSelectedInfo.value?.original_name
                    avrData.text =
                            "Vote average ${viewModel.tvTopRateSelectedInfo.value?.vote_average.toString()}"
                    textInfo.text = viewModel.tvTopRateSelectedInfo.value?.overview
                }
            }
        })

        viewModel.origin.observe(viewLifecycleOwner, {
            if (it == 1 || it == 3) {
                fragmentInfoBinding.backButton.setOnClickListener {
                    //Back to home
                    findNavController().navigate(R.id.action_infoFragment_to_homeFragment)
                }
            } else if(it == 5 || it == 6) {
                fragmentInfoBinding.backButton.setOnClickListener {
                    //Back to search
                    findNavController().navigate(R.id.action_infoFragment_to_searchFragment)
                }
            }else {
                fragmentInfoBinding.backButton.setOnClickListener {
                    //Back to TopRate
                    findNavController().navigate(R.id.action_infoFragment_to_topRateFragment)
                }
            }
        })
    }

    private fun chargeData() {
        fragmentInfoBinding.apply {
                imgHeaderPoster.load(urlBackdrop)
                imgPosterInfo.load(urlPoster)
                titleInfo.text = viewModel.tvTopRateSelectedInfo.value?.original_name
                avrData.text =
                        "Vote average ${viewModel.tvTopRateSelectedInfo.value?.vote_average.toString()}"
                textInfo.text = viewModel.tvTopRateSelectedInfo.value?.overview
        }
    }
}