package com.ashok.bible.ui.quotes

import android.view.View
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.remote.model.QuotesModel
import com.ashok.bible.databinding.ActivityQuotesDetailsBinding
import com.ashok.bible.ui.adapter.QuotesAdapter
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.TtsManager
import com.ashok.bible.utils.Utils
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdRequest
import com.lakki.kotlinlearning.view.base.BaseActivity
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class QuotesDetailsActivity : BaseActivity<QuotesViewModel, ActivityQuotesDetailsBinding>() {

    lateinit var quotesAdapter: QuotesAdapter
    private lateinit var animation: ShimmerFrameLayout
    var modelList: List<QuotesModel> = ArrayList()
    var tts: TtsManager? = null
    var lng: Locale = Locale.US




    @Inject
    lateinit var adRequest: AdRequest

    override fun getLayoutRes(): Int {
        return R.layout.activity_quotes_details
    }

    override fun init() {
        modelList = intent.getSerializableExtra("QUOTES_LIST") as List<QuotesModel>
        val title = intent.getStringExtra("TITLE")
        tts = TtsManager(this)
        when (SharedPrefUtils.getLanguage(pref)) {
            AppConstants.TELUGU -> {
                lng = Locale(AppConstants.TELUGU_IN)
            }
            AppConstants.TAMIL -> {
                lng = Locale(AppConstants.TAMIL_IN)
            }
            AppConstants.ENGLISH -> {
                lng = Locale(AppConstants.ENGLISH_IN)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = title

        binding.handlers = this
        binding.adView.loadAd(adRequest)
        animation = binding.loadingAnimation
        Utils.verticalRecyclerView(binding.recyclerView, this)
        quotesAdapter = QuotesAdapter(this, lng, tts, modelList)
        binding.recyclerView.adapter = quotesAdapter

        animation.stopShimmer();
        animation.visibility = View.GONE
        quotesAdapter.updateData(modelList)
    }


    override fun getViewModel(): Class<QuotesViewModel> {
        return QuotesViewModel::class.java
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        tts?.shutDown()
        super.onDestroy()

    }

}
