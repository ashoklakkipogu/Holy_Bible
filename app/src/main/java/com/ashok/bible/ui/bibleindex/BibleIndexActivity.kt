package com.ashok.bible.ui.bibleindex

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.databinding.ActivityBibleIndexBinding
import com.ashok.bible.ui.adapter.BibleIndexAdapter
import com.ashok.bible.ui.adapter.BibleIndexNumberAdapter
import com.ashok.bible.utils.LocalizationUtil
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.TtsManager
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.BaseActivity
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import kotlin.collections.ArrayList

class BibleIndexActivity : BaseActivity<BibleIndexViewModel, ActivityBibleIndexBinding>() {
 var data = ""
    var bibleIndex: ArrayList<BibleIndexModelEntry> = ArrayList()
    var bibleChapter: ArrayList<Int> = ArrayList()
    var bibleVerse: ArrayList<Int> = ArrayList()
    lateinit var bibleIndexAdapter: BibleIndexAdapter
    private var bibleChapterAdapter: BibleIndexNumberAdapter? =null
    lateinit var searchView: MaterialSearchView
    var bookId: Int = 0
    var chapterId: Int = 0
    var verseId: Int = 0
    var pageType = 1
    var selectedPageType = 1
    lateinit var progressBar: ProgressBar
    var tts: TtsManager? = null
    var lng: Locale = Locale.US




    override fun getLayoutRes(): Int {
        return R.layout.activity_bible_index
    }

    override fun init() {
        chapterId = intent.getIntExtra("chapterId", 0)
        verseId = intent.getIntExtra("verseId", 0)
        bookId = intent.getIntExtra("bookId", 0)
        pageType = intent.getIntExtra("pageType", 1)
        selectedPageType = pageType

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val drawable = toolbar.navigationIcon
        val navColor = ContextCompat.getColor(this, R.color.colorAccent_2)
        drawable?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            navColor,
            BlendModeCompat.SRC_ATOP
        )

        toolbar.setTitleTextColor(navColor)
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE
        val lang = SharedPrefUtils.getLanguage(pref)
        when (lang) {
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
        tts = TtsManager(this)
        setChapterRecyclerView()

        with(viewModel) {
            if (pageType == 3){
                progressBar.visibility = View.VISIBLE
                toolbar.title = getString(R.string.verse)
                pageType = 3
                Utils.gridRecyclerView(binding.recyclerView, this@BibleIndexActivity, 5)
                bibleChapterAdapter = BibleIndexNumberAdapter(this@BibleIndexActivity, bibleChapter)
                binding.recyclerView.adapter = bibleChapterAdapter
                viewModel.getBibleByBookIdAndChapterId(this@BibleIndexActivity, bookId, chapterId)
            }else{
                getBibleIndex(this@BibleIndexActivity)
            }

            bibleIndexData.observe(this@BibleIndexActivity, Observer {
                progressBar.visibility = View.GONE
                if (it != null) {
                    if (pageType == 2) {
                        val bibleIndexModelEntry = it[bookId]
                        onclickBook(bibleIndexModelEntry)
                    }
                    var oldTestament: String = ""
                    var newTestament: String = ""
                    when (SharedPrefUtils.getLanguage(pref)) {
                        AppConstants.TELUGU -> {
                            oldTestament = getString(R.string.old_testament_te)
                            newTestament = getString(R.string.new_testament_te)
                        }
                        AppConstants.TAMIL -> {
                            oldTestament = getString(R.string.old_testament_tn)
                            newTestament = getString(R.string.new_testament_tn)
                        }
                        AppConstants.ENGLISH -> {
                            oldTestament = getString(R.string.old_testament)
                            newTestament = getString(R.string.new_testament)
                        }
                    }



                    bibleIndex = it as ArrayList<BibleIndexModelEntry>
                    var bibleIndexModelEntry = BibleIndexModelEntry()
                    SharedPrefUtils.getLanguage(pref)
                    bibleIndexModelEntry.chapter = oldTestament
                    bibleIndex.add(0, bibleIndexModelEntry)
                    bibleIndexModelEntry = BibleIndexModelEntry()
                    bibleIndexModelEntry.chapter = newTestament
                    bibleIndex.add(40, bibleIndexModelEntry)
                    bibleIndexAdapter.updateData(bibleIndex)


                }
            })
            bibleChapters.observe(this@BibleIndexActivity, Observer {
                progressBar.visibility = View.GONE
                pageType = 2
                if (it != null) {
                    bibleChapter = if (it.size == 1) {
                        val value: ArrayList<Int> = ArrayList()
                        value.add(it[0])
                        value
                    } else {
                        it as ArrayList<Int>
                    }
                    bibleChapterAdapter?.updateData(bibleChapter)
                }
            })
            bibleVerseCount.observe(this@BibleIndexActivity, Observer {
                progressBar.visibility = View.GONE
                pageType = 3
                if (it != null) {
                    bibleVerse = if (it.size == 1) {
                        val value: ArrayList<Int> = ArrayList()
                        value.add(it[0])
                        value
                    } else {
                        it as ArrayList<Int>
                    }
                    bibleChapterAdapter?.updateData(bibleVerse)
                }
            })
        }
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { //Do some magic
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (pageType == 1)
                    bibleIndexAdapter.filter.filter(newText)
                else
                    bibleChapterAdapter?.filter?.filter(newText);
                return false
            }
        })
        toolbar.setNavigationOnClickListener {
            /*when (pageType) {
                1 -> {
                    super.onBackPressed();
                }
                2 -> {
                    pageType = 1
                    toolbar.title = getString(R.string.book)
                    setChapterRecyclerView()
                }
                3 -> {
                    pageType = 2
                    toolbar.title = getString(R.string.chapter)
                    bibleChapterAdapter?.updateData(bibleChapter)
                }
            }*/
            onBackPressed();
        }
    }

    private fun setChapterRecyclerView() {
        val recyclerView = binding.recyclerView
        bibleIndexAdapter = BibleIndexAdapter(this, bibleIndex);
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (BibleIndexAdapter.SECTION_VIEW == bibleIndexAdapter.getItemViewType(
                        position
                    )
                ) {
                    2
                } else 1
            }
        }
        recyclerView.layoutManager = gridLayoutManager

        //Utils.gridRecyclerView(binding.recyclerView, this, 2)
        binding.recyclerView.adapter = bibleIndexAdapter
    }

    override fun getViewModel(): Class<BibleIndexViewModel> {
        return BibleIndexViewModel::class.java
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_search, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true
    }

    fun onclickBook(obj: BibleIndexModelEntry) {
        progressBar.visibility = View.VISIBLE
        pageType = 2
        toolbar.title = getString(R.string.chapter)
        this.bookId = obj.chapter_id - 1
        viewModel.getBibleByBookId(this, bookId)
        Utils.gridRecyclerView(binding.recyclerView, this, 5)
        bibleChapterAdapter = BibleIndexNumberAdapter(this, bibleChapter)
        binding.recyclerView.adapter = bibleChapterAdapter
    }

    fun onclickMic(view: View, obj: BibleIndexModelEntry) {
        val micView: ImageView = view.findViewById(R.id.mic_btn)
        speakText(obj.chapter)
        micView.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
    }


    fun onclickChapter(chId: Int) {
        if (pageType != 3) {
            progressBar.visibility = View.VISIBLE
            toolbar.title = getString(R.string.verse)
            pageType = 3
            this.chapterId = chId
            viewModel.getBibleByBookIdAndChapterId(this, bookId, chapterId)
        } else if (pageType == 3) {
            verseId = chId
            val intent = Intent()
            intent.putExtra("BookId", bookId)
            intent.putExtra("ChapterId", chapterId)
            intent.putExtra("VerseId", verseId)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        when(selectedPageType){
            3 ->{
                if (searchView.isSearchOpen ){
                    searchView.closeSearch();
                }else{
                    super.onBackPressed();
                }
            }
            2 ->{
                if (searchView.isSearchOpen ){
                    searchView.closeSearch();
                }else if (pageType == 2 ){
                    super.onBackPressed();
                }else if (pageType == 3 ){
                    toolbar.title = getString(R.string.chapter)
                    pageType = 2
                    bibleChapterAdapter?.updateData(bibleChapter)
                }
            }
            1 ->{
                if (searchView.isSearchOpen ){
                    searchView.closeSearch();
                }else if (pageType == 1 ){
                    super.onBackPressed();
                }else if (pageType == 2 ){
                    pageType = 1
                    toolbar.title = getString(R.string.book)
                    setChapterRecyclerView()
                }else if (pageType == 3 ){
                    toolbar.title = getString(R.string.chapter)
                    pageType = 2
                    bibleChapterAdapter?.updateData(bibleChapter)
                }
            }
        }
    }

    private fun speakText(chapter:String) {
        if (chapter!!.isNotEmpty()) {
            tts?.say(chapter, lng)
        }

    }

    override fun onDestroy() {
        tts?.shutDown()
        super.onDestroy()

    }
}
