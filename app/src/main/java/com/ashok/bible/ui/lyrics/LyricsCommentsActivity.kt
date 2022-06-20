package com.ashok.bible.ui.lyrics

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.ashok.bible.R
import com.ashok.bible.data.remote.model.LyricsPostModel
import com.ashok.bible.databinding.ActivityLyricsCommentsBinding
import com.lakki.kotlinlearning.view.base.BaseActivity
import javax.inject.Inject


class LyricsCommentsActivity : BaseActivity<LyricsViewModel, ActivityLyricsCommentsBinding>() {

    lateinit var postModel: LyricsPostModel
    override fun getViewModel(): Class<LyricsViewModel> {
        return LyricsViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_lyrics_comments
    }

    fun onClick(view: View) {
        when (view.id) {

        }
    }

    override fun init() {
        binding.handlers = this

        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            postModel = bundle.getSerializable("PostModel") as LyricsPostModel
        }

        /*with(viewModel) {
            getPostData()
            commentsAdd.observe(this@LyricsCommentsActivity, Observer {
                dismissDialog()
            })

            error.observe(this@LyricsCommentsActivity, Observer {
                dismissDialog()
                if (it != null) {
                    showSnackbar(it.getErrorMessage())
                }
            })
        }

        val chatView = binding.chatView
        if (postModel != null) {
            val userID = SharedPrefUtils.getUserId(pref)
            val commentsMap = postModel.comments
            for ((key, value) in commentsMap) {
                var chatType: ChatMessage.Type = ChatMessage.Type.RECEIVED
                if (userID == value.userID) {
                    chatType = ChatMessage.Type.SENT
                }
                chatView.addMessage(
                    ChatMessage(
                        value.message,
                        value.createdData,
                        chatType,
                        value.userName
                    )
                )
            }
        }
        chatView.setOnSentMessageListener {
            viewModel.commentsForPosts(postModel.classID, postModel.id, it)
            true
        }


        chatView.setTypingListener(object : ChatView.TypingListener {
            override fun userStartedTyping() {

            }

            override fun userStoppedTyping() {

            }
        })*/
    }

}
