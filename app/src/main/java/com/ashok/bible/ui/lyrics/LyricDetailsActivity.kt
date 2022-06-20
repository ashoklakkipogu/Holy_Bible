package com.ashok.bible.ui.lyrics

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.NonNull
import androidx.core.view.doOnNextLayout
import com.ashok.bible.R
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.databinding.ActivityLyricsBinding
import com.ashok.bible.ui.DraggaleViewActivity
import com.lakki.kotlinlearning.view.base.BaseActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.io.Serializable


class LyricDetailsActivity : BaseActivity<LyricsViewModel, ActivityLyricsBinding>() {

    private var isLike = false
    private var isSelectedLang = false
    private var pos = 0
    private lateinit var lyrics: List<LyricsModel>
    private var youTubePlayer: YouTubePlayer? = null
    private lateinit var youTubePlayerView: YouTubePlayerView
    override fun getLayoutRes(): Int {
        return R.layout.activity_lyrics
    }

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.handlers = this
        val bundle = intent.extras
        lyrics = bundle?.getSerializable("lyrics") as List<LyricsModel>
        isSelectedLang = bundle?.getBoolean("isSecondLan")
        pos = bundle?.getInt("pos")
        if (pos == 0) {
            binding.prevBtn.isEnabled = false
            binding.prevBtn.alpha = .5f
            binding.prevBtn.isClickable = false;

        }
        if (pos == lyrics.size-1) {
            binding.nextBtn.isEnabled = false
            binding.nextBtn.alpha = .5f
            binding.nextBtn.isClickable = false;
        }
        youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                val videoId = lyrics.get(pos).youtubeId
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
        setContent()

    }

    private fun setContent() {
        val lyric = lyrics[pos]
        val anim = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 200
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE


        var content = ""
        var title = ""
        if (isSelectedLang) {
            binding.engBtn.text = lyric.language
            content = lyric.contentEn
            title = lyric.titleEn
        } else {
            binding.engBtn.text = "ENGLISH"
            content = lyric.content
            title = lyric.title
        }
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.lyricTxt.text = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    binding.lyricTxt.text = Html.fromHtml(content);
                }
                binding.titleTxt.text = title
            }
        })


        binding.titleTxt.startAnimation(anim)
        binding.lyricTxt.startAnimation(anim)

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.eng_btn -> {
                if (isSelectedLang) {
                    isSelectedLang = false
                    setContent()
                } else {
                    isSelectedLang = true
                    setContent()
                }
                /*val intent = Intent(this, DraggaleViewActivity::class.java)
                startActivity(intent)*/
            }
            R.id.next_btn -> {
                finish()
                val intent = Intent(this, LyricDetailsActivity::class.java)
                val bundle = Bundle()
                val index = lyrics.indexOf(lyrics[pos])
                bundle.putSerializable("lyrics", lyrics as Serializable)
                bundle.putBoolean("isSecondLan", isSelectedLang)
                bundle.putInt("pos", index + 1)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            R.id.prev_btn -> {
                finish()
                val intent = Intent(this, LyricDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("lyrics", lyrics as Serializable)
                bundle.putBoolean("isSecondLan", isSelectedLang)
                val index = lyrics.indexOf(lyrics[pos])
                bundle.putInt("pos", index - 1)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            /*R.id.like_btn -> {
                if (isLike) {
                    isLike = false
                    binding.likeBtn.setImageResource(R.drawable.icon_un_favourite)
                } else {
                    isLike = true
                    binding.likeBtn.setImageResource(R.drawable.icon_favourite)
                }
            }
            R.id.comments_btn ->{
                val intent = Intent(this, LyricsCommentsActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("PostModel", LyricsPostModel())
                intent.putExtras(bundle)
                startActivity(intent)
            }*/

        }
    }

    override fun getViewModel(): Class<LyricsViewModel> {
        return LyricsViewModel::class.java
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onClickComments(view: View) {

    }
}
