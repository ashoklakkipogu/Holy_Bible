package com.ashok.bible.ui.lyrics

import android.os.Bundle
import android.os.Handler
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashok.bible.R
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.databinding.FragmentLyricsFirstLanBinding
import com.ashok.bible.listener.RecyclerViewLoadMoreScroll
import com.ashok.bible.ui.adapter.LyricsAdapter
import com.lakki.kotlinlearning.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_lyrics_first_lan.*


class LyricsFirstLangFragment : BaseFragment<LyricsViewModel, FragmentLyricsFirstLanBinding>() {

    lateinit var adapter: LyricsAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    var index: Int = 0


    override fun getLayoutRes(): Int {
        return R.layout.fragment_lyrics_first_lan
    }

    override fun init() {
        binding.handlers = this
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                index = 0
                if (newText!!.isEmpty()){
                    adapter.updateData(viewModel.getAllLyricsByLimit(index) as ArrayList<LyricsModel>)
                }else{
                    adapter.updateData(viewModel.getSongsByTitle(index, newText) as ArrayList<LyricsModel>)
                }
                //adapter
                //adapter.filter.filter(newText)
                return false
            }
        })
        val bundle = arguments
        val isSecondLan = bundle?.getBoolean("isSecondLan")
        /*Utils.verticalRecyclerView(binding.recyclerView, activity)
        adapter = LyricsAdapter(this, lyrics, isSecondLan, binding.emptyView)
        binding.recyclerView.adapter = adapter*/


        //binding.recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = LyricsAdapter(
            this,
            viewModel.getAllLyricsByLimit(index) as ArrayList<LyricsModel>,
            isSecondLan!!,
            binding.emptyView
        )
        binding.recyclerView.adapter = adapter

        scrollListener = RecyclerViewLoadMoreScroll(linearLayoutManager)
        scrollListener.setOnLoadMoreListener {
            index += 20
            loadMoreData()
        }
        binding.recyclerView.addOnScrollListener(scrollListener)

    }


    override fun getViewModel(): Class<LyricsViewModel> {
        return LyricsViewModel::class.java
    }

    fun updateSearch(query: String) {
        adapter.filter.filter(query)
    }

    fun onClickDraggableView(
        obj: LyricsModel,
        list: List<LyricsModel>,
        isSecondLan: Boolean,
        index: Int
    ) {
        val parentFrag: LyricsFragment = this.parentFragment as LyricsFragment
        parentFrag.onClickDraggableView(obj, list, isSecondLan, index)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear()
    }


    private fun loadMoreData() {

        adapter.addLoadingView()
        Handler().postDelayed({
            var query = binding.searchView.query.toString()
            var list: List<LyricsModel> = if (query.isEmpty()){
                viewModel.getAllLyricsByLimit(index)
            }else{
                viewModel.getSongsByTitle(index, query)
            }

            adapter.removeLoadingView()
            adapter.addData(list)
            adapter.notifyDataSetChanged()
            scrollListener.setLoaded()
        }, 500)
    }

    fun updateNewData() {
        index = 0
        adapter.updateData(viewModel.getAllLyricsByLimit(index) as ArrayList<LyricsModel>)
    }

    fun filterBylan(filterValue: String) {
        //adapter.
    }
}
