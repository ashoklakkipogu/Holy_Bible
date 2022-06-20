package com.ashok.bible.ui.admin

import android.net.Uri
import android.view.View
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.data.remote.model.QuotesModel
import com.ashok.bible.databinding.ActivityLyricPostBinding
import com.ashok.bible.databinding.ActivityNotificationPostBinding
import com.ashok.bible.databinding.ActivityQuotesPostBinding
import com.ashok.bible.ui.model.NotificationMsgModel
import com.ashok.bible.utils.DialogBuilder
import com.ashok.bible.utils.DialogListenerForLyricFilter
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_notification_post.*
import kotlinx.android.synthetic.main.activity_notification_post.view.*


class QuotesPosActivity : BaseActivity<AdminViewModel, ActivityQuotesPostBinding>() {

    var filterValue = AppConstants.FILTER_ALL


    override fun getViewModel(): Class<AdminViewModel> {
        return AdminViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_quotes_post
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.submit_btn -> {
                val author = binding.editAuthor.text.toString()
                val quotes = binding.editQuotes.text.toString()



                if (author.isEmpty()) {
                    showSnackbar("Please enter author")
                } else if (quotes.isEmpty()) {
                    showSnackbar("Please enter quotes")
                } else if (binding.selectLng.text == AppConstants.SELECT_LNG) {
                    showSnackbar("Please select language")
                } else {
                    showDialog()
                    val obj = QuotesModel()
                    obj.author = author
                    obj.quote = quotes
                    obj.language = filterValue
                    obj.createdDate = Utils.getCurrentTime()
                    viewModel.createQuotes(obj)
                }
            }
            R.id.select_lng -> {
                DialogBuilder.showFilter(
                        this,
                        filterValue,
                        object : DialogListenerForLyricFilter {
                            override fun language(filterValue: String) {
                                this@QuotesPosActivity.filterValue = filterValue
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
            createQuotes.observe(this@QuotesPosActivity, Observer {
                dismissDialog()
                if (it != null) {
                    binding.editAuthor.setText("")
                    binding.editQuotes.setText("")
                    binding.selectLng.text = AppConstants.SELECT_LNG
                    showSnackbar("Create Quotes Successfully.")
                }
            })

            error.observe(this@QuotesPosActivity, Observer {
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
