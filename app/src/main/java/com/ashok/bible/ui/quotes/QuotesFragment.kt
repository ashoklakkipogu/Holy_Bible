package com.ashok.bible.ui.quotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.remote.model.QuotesModel
import com.ashok.bible.databinding.FragmentQuotesBinding
import com.ashok.bible.ui.adapter.QuotesIndexAdapter
import com.ashok.bible.utils.SharedPrefUtils
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdRequest
import com.lakki.kotlinlearning.view.base.BaseFragment
import java.io.Serializable
import javax.inject.Inject


class QuotesFragment : BaseFragment<QuotesViewModel, FragmentQuotesBinding>() {

    //lateinit var quotesAdapter: QuotesAdapter
    lateinit var mQuotesIndexAdapter: QuotesIndexAdapter
    private lateinit var animation: ShimmerFrameLayout
    var modelList: ArrayList<String> = ArrayList()
    var quotes: Map<String, List<QuotesModel>> = mapOf()
    var langFilter: String= ""


    @Inject
    lateinit var adRequest: AdRequest

    override fun getLayoutRes(): Int {
        return R.layout.fragment_quotes
    }

    override fun init() {
        binding.handlers = this
        binding.adView.loadAd(adRequest)
        animation = binding.loadingAnimation
        /*Utils.verticalRecyclerView(binding.recyclerView, activity)
        quotesAdapter = QuotesAdapter(this, modelList);
        binding.recyclerView.adapter = quotesAdapter
        animation = binding.loadingAnimation*/
        val recyclerView = binding.recyclerView
        mQuotesIndexAdapter = QuotesIndexAdapter(this, modelList);
        val gridLayoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = mQuotesIndexAdapter
        val lang = SharedPrefUtils.getLanguage(pref)
        when (lang) {
            AppConstants.TELUGU -> {
                langFilter = AppConstants.FILTER_TELUGU
            }
            AppConstants.TAMIL -> {
                langFilter = AppConstants.FILTER_TAMIL
            }
            AppConstants.ENGLISH -> {
                langFilter = AppConstants.FILTER_ENGLISH
            }
        }

        with(viewModel) {
            animation.startShimmer()
            animation.visibility = View.VISIBLE
            getQuotes(langFilter)
            quotesData.observe(this@QuotesFragment, Observer {
                animation.stopShimmer();
                animation.visibility = View.GONE
                if (it != null) {
                    quotes =it
                    for(key in it.keys){
                        modelList.add(key)
                    }

                    mQuotesIndexAdapter.updateData(modelList)
                    /*val filteredMap = it.filter { data->
                        data.language == langFilter
                    }
                    quotesAdapter.updateData(filteredMap)*/
                }
            })
            error.observe(this@QuotesFragment, Observer {
                animation.stopShimmer();
                animation.visibility = View.GONE
                if (it != null) {
                    showSnackbar(it.getErrorMessage())
                }
            })
        }

    }


    override fun getViewModel(): Class<QuotesViewModel> {
        return QuotesViewModel::class.java
    }

    fun onclickChapter(obj: String) {
        if (quotes.isNotEmpty()){
            val quotesList = quotes[obj]

            val intent = Intent(
                context,
                QuotesDetailsActivity::class.java
            )
            intent.putExtra("TITLE", obj)
            val bundle = Bundle()
            bundle.putSerializable("QUOTES_LIST", quotesList as Serializable)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

}
