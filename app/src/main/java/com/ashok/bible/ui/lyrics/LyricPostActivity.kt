package com.ashok.bible.ui.lyrics

import android.net.Uri
import android.view.View
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.databinding.ActivityLyricPostBinding
import com.ashok.bible.databinding.ActivityNotificationPostBinding
import com.ashok.bible.ui.model.NotificationMsgModel
import com.ashok.bible.utils.DialogBuilder
import com.ashok.bible.utils.DialogListenerForLyricFilter
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_notification_post.*
import kotlinx.android.synthetic.main.activity_notification_post.view.*


class LyricPostActivity : BaseActivity<LyricsViewModel, ActivityLyricPostBinding>() {

    var imageUri: Uri? = null
    var filterValue = AppConstants.FILTER_ALL


    override fun getViewModel(): Class<LyricsViewModel> {
        return LyricsViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_lyric_post
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.submit_btn->{
                val title = binding.editTitle.text.toString()
                val titleEn = binding.editTitleEn.text.toString()
                val dsc = binding.editDesc.text.toString()
                val youtubeId = binding.editYoutube.text.toString()
                val singer = binding.editSinger.text.toString()
                val content = binding.editContent.text.toString()
                val contentEn = binding.editContentEn.text.toString()
                val youtubeTrackId = binding.youtubeLyricId.text.toString()


                if(title.isEmpty()){
                    showSnackbar("Please enter title")
                }else if(content.isEmpty()){
                    showSnackbar("Please enter content")
                }else if(binding.selectLng.text == AppConstants.SELECT_LNG){
                    showSnackbar("Please select language")
                }else {
                    showDialog()
                    val obj = LyricsModel()
                    obj.title = title
                    obj.titleEn = titleEn
                    obj.desc = dsc
                    obj.language = filterValue
                    obj.youtubeId = youtubeId
                    obj.singers = singer
                    obj.content = content
                    obj.contentEn = contentEn
                    obj.youtubeTrackId = youtubeTrackId
                    obj.createdDate = Utils.getCurrentTime()
                    viewModel.createLyric(obj)
                }
                
                


            }
            R.id.select_lng->{
                DialogBuilder.showFilter(
                    this,
                    filterValue,
                    object : DialogListenerForLyricFilter {
                        override fun language(filterValue:String) {
                            this@LyricPostActivity.filterValue =filterValue
                            binding.selectLng.text = filterValue
                        }
                    }
                )
            }
        }
    }

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.handlers = this
        with(viewModel) {
            createLyric.observe(this@LyricPostActivity, Observer {
                dismissDialog()
                if (it != null) {
                    binding.editTitle.setText("")
                    binding.editTitleEn.setText("")
                    binding.editDesc.setText("")
                    binding.editYoutube.setText("")
                    binding.editSinger.setText("")
                    binding.editContent.setText("")
                    binding.editContentEn.setText("")
                    binding.youtubeLyricId.setText("")
                    binding.selectLng.text = AppConstants.SELECT_LNG
                    showSnackbar("Create Lyrics Successfully.")
                }
            })

            error.observe(this@LyricPostActivity, Observer {
                dismissDialog()
                if (it != null) {
                    showSnackbar(it.getErrorMessage())
                }
            })
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
