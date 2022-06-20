package com.ashok.bible.ui.lyrics

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.ViewModel
import com.ashok.bible.R
import com.ashok.bible.databinding.ActivityLyricExpandedBinding
import com.ashok.bible.ui.model.LyricsDataTrans
import com.lakki.kotlinlearning.view.base.BaseActivity


class LyricExpandedActivity : BaseActivity<ViewModel,ActivityLyricExpandedBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_lyric_expanded
    }

    override fun init() {
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)


        binding.handlers = this
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            val mLyricsDataTrans = bundle.getSerializable("content") as LyricsDataTrans
            val lyContent = mLyricsDataTrans.content
            val lyTitle = mLyricsDataTrans.title
            binding.lyricTitle.text = lyTitle
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.lyricTxt.text = Html.fromHtml(lyContent, Html.FROM_HTML_MODE_LEGACY);
            } else {
                binding.lyricTxt.text = Html.fromHtml(lyContent);
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.close_img->{
                finish()
            }
        }
    }
    override fun getViewModel(): Class<ViewModel> {
        return ViewModel::class.java
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear()
    }
}
