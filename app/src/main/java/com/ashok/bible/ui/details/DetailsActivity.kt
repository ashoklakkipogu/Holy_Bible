package com.ashok.bible.ui.details

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.data.remote.model.BannerModel
import com.ashok.bible.databinding.DataBindingAdapter
import com.ashok.bible.databinding.DetailsActivityBinding
import com.ashok.bible.utils.*
import com.lakki.kotlinlearning.view.base.BaseActivity
import java.util.*
import kotlin.collections.ArrayList

class DetailsActivity : BaseActivity<DetailsViewModel, DetailsActivityBinding>() {

    var isPlay = false
    private var id: Int = 0
    private var book: Int = 0
    private var chapter: Int = 0
    private var verseCount: Int = 0
    private var verse: String? = null
    var bibleIndex: List<BibleIndexModelEntry> = ArrayList()
    var tts: TtsManager? = null
    var lng: Locale = Locale.US
    var isBookMark: Boolean = false
    var bookId: Int = 0
    var isNote: Boolean = false
    var noteId: Int = 0

    var isHighLight: Boolean = false
    var highLight: Int = 0
    var bannerModel: List<BannerModel> = ArrayList()


    override fun getLayoutRes(): Int {
        return R.layout.details_activity
    }

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.handlers = this
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

        if (intent != null) {
            id = intent.getIntExtra("id", 0)
            book = intent.getIntExtra("book", 0)
            chapter = intent.getIntExtra("chapter", 0)
            verseCount = intent.getIntExtra("verseCount", 0)
            verse = intent.getStringExtra("verse")

        }

        with(viewModel) {
            getBanner()
            getBibleIndex(this@DetailsActivity);
            getFavById(this@DetailsActivity, id)
            getNotesById(this@DetailsActivity, id)
            getHighlightById(this@DetailsActivity, id)
            bibleIndexData.observe(this@DetailsActivity, Observer {
                if (it != null) {
                    bibleIndex = it
                    val bibleIndexName = "${bibleIndex[book].chapter} ${chapter}:${verseCount}"
                    binding.chapterText.text = "- $bibleIndexName"
                    binding.verseText.text = verse
                }
            })

            favData.observe(this@DetailsActivity, Observer {
                if (it != null) {
                    isBookMark = true
                    bookId = it.id
                    binding.bookMarkSelect.visibility = View.VISIBLE
                } else {
                    bookId = 0
                    isBookMark = false
                    binding.bookMarkSelect.visibility = View.GONE
                }
            })
            notesData.observe(this@DetailsActivity, Observer {
                if (it != null) {
                    isNote = true
                    noteId = it.id
                    binding.noteSelect.visibility = View.VISIBLE
                } else {
                    isNote = false
                    noteId = 0
                    binding.noteSelect.visibility = View.GONE
                }
            })
            highlightsData.observe(this@DetailsActivity, Observer {
                isHighLight = false
                highLight = 0
                binding.color1Select.visibility = View.GONE
                binding.color2Select.visibility = View.GONE
                binding.color3Select.visibility = View.GONE
                binding.color4Select.visibility = View.GONE
                binding.highlightsSelect.visibility = View.GONE
                if (it != null) {
                    isHighLight = true
                    highLight = it.id
                    when (it.color) {
                        "#FFEB3B" -> {
                            binding.color1Select.visibility = View.VISIBLE
                        }
                        "#F44336" -> {
                            binding.color2Select.visibility = View.VISIBLE
                        }
                        "#3F51B5" -> {
                            binding.color3Select.visibility = View.VISIBLE
                        }
                        "#4CAF50" -> {
                            binding.color4Select.visibility = View.VISIBLE
                        }
                        else -> {
                            binding.highlightsSelect.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.color1Select.visibility = View.GONE
                    binding.color2Select.visibility = View.GONE
                    binding.color3Select.visibility = View.GONE
                    binding.color4Select.visibility = View.GONE
                    binding.highlightsSelect.visibility = View.GONE
                }
            })
            deleteFavorite.observe(this@DetailsActivity, Observer {
                getFavById(this@DetailsActivity, id)
            })
            deleteNote.observe(this@DetailsActivity, Observer {
                getNotesById(this@DetailsActivity, id)
            })
            deleteHighlight.observe(this@DetailsActivity, Observer {
                getHighlightById(this@DetailsActivity, id)
            })
            bannerData.observe(this@DetailsActivity, Observer {
                if (it != null) {
                    bannerModel = it
                }
            })
            error.observe(this@DetailsActivity, Observer {

            })
        }

    }

    override fun getViewModel(): Class<DetailsViewModel> {
        return DetailsViewModel::class.java
    }

    public fun onClick(view: View) {
        when (view.id) {
            R.id.mic_btn -> {
                isPlay = if (!isPlay) {
                    speakText()
                    binding.micBtn.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                    true
                } else {
                    tts?.stop()
                    binding.micBtn.setImageResource(R.drawable.ic_stop_icon);
                    false
                }
            }
            R.id.book_mark_view -> {
                if (!isBookMark) {
                    isBookMark = true
                    var favList: ArrayList<FavoriteModelEntry> = ArrayList()
                    val favObj = FavoriteModelEntry()
                    favObj.bibleId = id
                    favObj.book = book
                    favObj.chapter = chapter
                    favObj.versecount = verseCount
                    favObj.verse = verse!!
                    favObj.createdDate = Utils.getStringTimeStampWithDate()
                    favObj.bibleIndexName = "${bibleIndex[book].chapter} ${chapter}:${verseCount}"
                    favList.add(favObj)

                    viewModel.insertFavorites(favList)
                } else {
                    isBookMark = false
                    viewModel.deleteFavoriteById(bookId)
                }

            }
            R.id.note_view -> {
                if (!isNote) {
                    DialogBuilder.showNoteDialog(
                            this,
                            object : DialogListenerForNote {
                                override fun dialogNote(noteTxt: String) {
                                    isNote = true
                                    var noteList: ArrayList<NoteModelEntry> = ArrayList()
                                    val noteObj = NoteModelEntry()
                                    noteObj.bibleId = id
                                    noteObj.book = book
                                    noteObj.chapter = chapter
                                    noteObj.versecount = verseCount
                                    noteObj.verse = verse!!
                                    noteObj.createdDate = Utils.getStringTimeStampWithDate()
                                    noteObj.noteName = noteTxt
                                    noteObj.bibleIndexName = "${bibleIndex[book].chapter} ${chapter}:${verseCount}"

                                    noteList.add(noteObj)


                                    viewModel.insertNotes(noteList)
                                }
                            })
                } else {
                    isNote = false
                    viewModel.deleteNoteById(noteId)
                }

            }
            R.id.color_1 -> {
                updateHighlight("#FFEB3B")
            }
            R.id.color_2 -> {
                updateHighlight("#F44336")
            }
            R.id.color_3 -> {
                updateHighlight("#3F51B5")
            }
            R.id.color_4 -> {
                updateHighlight("#4CAF50")
            }
            R.id.highlights_view -> {
                updateHighlight(null)
            }
            R.id.share_view -> {
                /*val bibleIndex = "${bibleIndex[book].chapter} ${chapter}:${verseCount}"
                val verse = verse
                val str = "$bibleIndex - $verse"
                Utils.shareText(this, str)*/
                binding.refreshImage.visibility = View.GONE
                binding.micBtn.visibility = View.GONE
                binding.cardBottom.visibility = View.GONE
                val bitmap = Utils.getBitmapFromView(binding.cardView)
                if (bitmap != null) {
                    Utils.shareBitmap(this, bitmap)
                }
                binding.refreshImage.visibility = View.VISIBLE
                binding.micBtn.visibility = View.VISIBLE
                binding.cardBottom.visibility = View.VISIBLE
            }
            R.id.download_view -> {
                binding.refreshImage.visibility = View.GONE
                binding.micBtn.visibility = View.GONE
                binding.cardBottom.visibility = View.GONE
                val bitmap = Utils.getBitmapFromView(binding.cardView)
                if (bitmap != null) {
                    Utils.saveTempBitmap(this, bitmap)
                }
                binding.refreshImage.visibility = View.VISIBLE
                binding.micBtn.visibility = View.VISIBLE
                binding.cardBottom.visibility = View.VISIBLE
                showSnackbar("Image has been downloaded successfully. \n Android/data/com.ashok.bible/files/${resources.getString(R.string.app_name)}")

            }
            R.id.copy_view -> {
                val bibleIndex = "${bibleIndex[book].chapter} ${chapter}:${verseCount}"
                val verse = verse
                val str = "$verse \n - $bibleIndex"
                Utils.copyText(this, str)
                showSnackbar("Copied")
            }
            R.id.refresh_image -> {


                if (!bannerModel.isNullOrEmpty()) {
                    val bannerObj = bannerModel.random()
                    if (bannerObj.image.isNotEmpty())
                        DataBindingAdapter.setImage(binding.trendingImageView, bannerObj.image)
                }else{
                    showSnackbar("No Internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.")
                }


            }
        }
    }

    private fun updateHighlight(color: String?) {
        if (!isHighLight) {
            isHighLight = true
            val highlightList: ArrayList<HighlightModelEntry> = ArrayList()
            val highlightObj = HighlightModelEntry()
            highlightObj.bibleId = id
            highlightObj.book = book
            highlightObj.chapter = chapter
            highlightObj.versecount = verseCount
            highlightObj.verse = verse!!
            if (color != null) {
                highlightObj.color = color
            }
            highlightObj.createdDate = Utils.getStringTimeStampWithDate()
            highlightObj.bibleIndexName = "${bibleIndex[book].chapter} ${chapter}:${verseCount}"

            highlightList.add(highlightObj)

            viewModel.insertHighlights(highlightList)
        } else {
            isHighLight = false
            viewModel.deleteHighlightById(highLight)
        }
    }

    private fun speakText() {
        if (verse!!.isNotEmpty()) {
            tts?.say(verse.toString(), lng)
        }

    }

    override fun onDestroy() {
        tts?.shutDown()
        super.onDestroy()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}