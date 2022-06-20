package com.ashok.bible.ui.highlights

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.data.local.BibleDatabase
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.databinding.FragmentHighlightsBinding
import com.ashok.bible.ui.MainActivity
import com.ashok.bible.ui.adapter.HighlightsAdapter
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.Utils
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdRequest
import com.lakki.kotlinlearning.view.base.BaseFragment
import javax.inject.Inject

class HighlightsFragment : BaseFragment<HighlightsViewModel, FragmentHighlightsBinding>() {
    var modelList: List<HighlightModelEntry> = ArrayList()
    lateinit var highlightsAdapter: HighlightsAdapter
    private lateinit var animation: ShimmerFrameLayout

    @Inject
    lateinit var adRequest: AdRequest

    override fun getLayoutRes(): Int {
        return R.layout.fragment_highlights
    }

    override fun init() {
        binding.adView.loadAd(adRequest)
        animation = binding.loadingAnimation
        Utils.verticalRecyclerView(binding.recyclerView, activity)
        highlightsAdapter = HighlightsAdapter(this, modelList);
        binding.recyclerView.adapter = highlightsAdapter

        with(viewModel) {
            animation.startShimmer()
            animation.visibility = View.VISIBLE
            getAllHighlights(this@HighlightsFragment)
            highlightsData.observe(this@HighlightsFragment, Observer {
                animation.stopShimmer();
                animation.visibility = View.GONE
                if (it != null) {
                    val data = it.sortedBy { sort -> sort.createdDate }.reversed()
                    highlightsAdapter.updateData(data)
                    if (data.isEmpty()) {
                        binding.placeholderLabel.visibility = View.VISIBLE
                    } else {
                        binding.placeholderLabel.visibility = View.GONE
                    }
                } else {
                    binding.placeholderLabel.visibility = View.VISIBLE
                }
            })
            deleteHighlight.observe(this@HighlightsFragment, Observer {
                animation.startShimmer()
                animation.visibility = View.VISIBLE
                getAllHighlights(this@HighlightsFragment)
            })
            error.observe(this@HighlightsFragment, Observer {
                animation.stopShimmer();
                animation.visibility = View.GONE
                binding.placeholderLabel.visibility = View.VISIBLE
            })
        }
    }

    override fun getViewModel(): Class<HighlightsViewModel> {
        return HighlightsViewModel::class.java
    }

    fun onClickItem(list: HighlightModelEntry) {
        (activity as MainActivity).onClickItem(list.bibleId)
    }

    fun deleteRow(entry: HighlightModelEntry) {
        val ab: AlertDialog.Builder = AlertDialog.Builder(activity)
        ab.setTitle("Delete")
        ab.setMessage("Are you sure to delete this item?")
        ab.setPositiveButton("Yes") { _, id ->
            viewModel.deleteHighlightById(entry.id)
        }
        ab.setNegativeButton(
            "No"
        ) { pObjDialog, id -> pObjDialog.dismiss() }
        ab.show()

    }
}
