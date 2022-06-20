package com.ashok.bible.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel

import com.ashok.bible.R
import com.ashok.bible.databinding.FragmentHomeBinding
import com.ashok.bible.databinding.FragmentOneBinding
import com.ashok.bible.ui.home.HomeViewModel
import com.ashok.bible.ui.lyrics.LyricsViewModel
import com.lakki.kotlinlearning.view.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class One : BaseFragment<ViewModel, FragmentOneBinding>() {
    private lateinit var youTubePlayerView: YouTubePlayerView
    private var youtubeId: String? = null
    private var youTubePlayerI: YouTubePlayer? = null

    fun setVideo(youtubeId: String) {
        this.youtubeId = youtubeId
        youtubeId.let { youTubePlayerI?.loadVideo(it, 0f) }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_one;
    }

    override fun init() {
        youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayerI = youTubePlayer
                youtubeId?.let { youTubePlayer.loadVideo(it, 0f) }
            }
        })

    }

    override fun getViewModel(): Class<ViewModel> {
        return ViewModel::class.java
    }

    fun pauseVideo() {
        youTubePlayerI?.pause()
    }
}
