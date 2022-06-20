package com.ashok.bible.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel

import com.ashok.bible.R
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.databinding.FragmentOneBinding
import com.ashok.bible.databinding.FragmentTwoBinding
import com.ashok.bible.ui.lyrics.LyricDetailsActivity
import com.ashok.bible.ui.lyrics.LyricExpandedActivity
import com.ashok.bible.ui.lyrics.LyricsFragment
import com.ashok.bible.ui.model.LyricsDataTrans
import com.lakki.kotlinlearning.view.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.io.Serializable

class Two : BaseFragment<ViewModel, FragmentTwoBinding>() {
    private var isLike = false
    private var isSelectedLang = false
    private var pos = 0
    private lateinit var lyrics: List<LyricsModel>
    private var frag: LyricsFragment? = null
    var lyric: LyricsModel? = null

    fun setLyric(frag: LyricsFragment, obj: List<LyricsModel>, isSecondLan: Boolean, pos: Int) {
        lyrics = obj
        this.frag = frag
        this.isSelectedLang = isSecondLan
        this.pos = pos
        binding.prevBtn.isEnabled = true
        binding.prevBtn.alpha = 1f
        binding.prevBtn.isClickable = true

        binding.nextBtn.isEnabled = true
        binding.nextBtn.alpha = 1f
        binding.nextBtn.isClickable = true
        if (pos == 0) {
            binding.prevBtn.isEnabled = false
            binding.prevBtn.alpha = .5f
            binding.prevBtn.isClickable = false;

        }
        if (pos == lyrics.size - 1) {
            binding.nextBtn.isEnabled = false
            binding.nextBtn.alpha = .5f
            binding.nextBtn.isClickable = false;
        }
        setContent()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_two
    }

    override fun init() {
        binding.handlers = this
        binding.trackBtn.text = "TRACK"
    }

    override fun getViewModel(): Class<ViewModel> {
        return ViewModel::class.java
    }

    private fun setContent() {
        lyric = lyrics[pos]
        val anim = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 200
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE

        var content = ""
        var title = ""
        if (isSelectedLang) {
            binding.engBtn.text = "CHANGE TO " + lyric?.language
            content = lyric?.contentEn!!
            title = lyric?.titleEn!!
        } else {
            binding.engBtn.text = "CHANGE TO ENGLISH"
            content = lyric?.content!!
            title = lyric?.title!!
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
                val index = lyrics.indexOf(lyrics[pos])
                val nextIndex = index + 1
                frag?.onClickDraggableView(lyrics[nextIndex], lyrics, isSelectedLang, nextIndex)
                /*finish()
                val intent = Intent(this, LyricDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("lyrics", lyrics as Serializable)
                bundle.putBoolean("isSecondLan", isSelectedLang)
                bundle.putInt("pos", index + 1)
                intent.putExtras(bundle)
                startActivity(intent)*/
            }
            R.id.prev_btn -> {
                /*finish()
                val intent = Intent(this, LyricDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("lyrics", lyrics as Serializable)
                bundle.putBoolean("isSecondLan", isSelectedLang)
                val index = lyrics.indexOf(lyrics[pos])
                bundle.putInt("pos", index - 1)
                intent.putExtras(bundle)
                startActivity(intent)*/
                val index = lyrics.indexOf(lyrics[pos])
                val preIndex = index - 1
                frag?.onClickDraggableView(lyrics[preIndex], lyrics, isSelectedLang, preIndex)
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
            R.id.track_btn -> {
                if (binding.trackBtn.text.equals("SONG")){
                    val index = lyrics.indexOf(lyrics[pos])
                    frag?.onClickDraggableView(lyrics[index], lyrics, isSelectedLang, index)
                }else{
                    val index = lyrics.indexOf(lyrics[pos])
                    if (lyrics[index].youtubeTrackId.isEmpty()) {
                        showSnackbar("Track is not available for this video.")
                    } else {
                        binding.trackBtn.text = "SONG"
                        frag?.onClickTrack(lyrics[index], lyrics, isSelectedLang, index)
                    }
                }

            }
            R.id.expanded_menu -> {
                var content = ""
                var title = ""
                if (isSelectedLang) {
                    content = lyric?.contentEn!!
                    title = lyric?.titleEn!!
                } else {
                    content = lyric?.content!!
                    title = lyric?.title!!
                }
                val obj = LyricsDataTrans()
                obj.title = title
                obj.content = content
                val intent = Intent(activity, LyricExpandedActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("content", obj)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
    }

    fun setTrackView(b: Boolean) {
        if (b){
            binding.trackBtn.text = "TRACK"
        }else{
            binding.trackBtn.text = "SONG"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear()
    }
}

