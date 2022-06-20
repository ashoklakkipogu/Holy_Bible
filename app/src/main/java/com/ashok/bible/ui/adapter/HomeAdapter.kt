package com.ashok.bible.ui.adapter

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.databinding.HomeRowBinding
import com.ashok.bible.ui.details.DetailsActivity
import com.ashok.bible.ui.home.HomeFragment
import com.ashok.bible.utils.*
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter
import java.util.*
import kotlin.collections.ArrayList


class HomeAdapter(
    internal var mContext: HomeFragment,
    var tts: TtsManager?,
    var lng: Locale,
    var pref: SharedPreferences,
    var list: ArrayList<BibleModelEntry>,
    var originalList: ArrayList<BibleModelEntry> = list
) :
    RecyclerBaseAdapter<BibleModelEntry?>(), Filterable {

    var selectedItems = mutableSetOf<Int>()
    var currentPos: Int = 0
    var isPlay = false
    var favList: List<FavoriteModelEntry> = ArrayList()
    var notesList: List<NoteModelEntry> = ArrayList()
    var highlightList: List<HighlightModelEntry> = ArrayList()
    var verseDefaultText:Int = 14
    var verseText:Int = 14
    var indexTextDefault:Int = 12
    var indexText:Int = 14
    var lineSpace:Float = 1f


    override fun getDataAtPosition(position: Int): BibleModelEntry? {
        return list[position]
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        return R.layout.home_row
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var view = holder.binding as HomeRowBinding
        val book = list[position]
        val gradientDrawable = (view.indexBgView.background as GradientDrawable).mutate()
        (gradientDrawable as GradientDrawable).setColor(
            Utils.colorCodeByPos(
                mContext.context,
                position
            )
        )


        val favObjs = favList.filter {
            book.id == it.bibleId
        }
        val noteObjs = notesList.filter {
            book.id == it.bibleId
        }

        val highlightObjs = highlightList.filter {
            book.id == it.bibleId
        }

        if (favObjs.isNotEmpty()) {
            view.bookMarkView.visibility = View.VISIBLE
        } else {
            view.bookMarkView.visibility = View.GONE
        }
        if (noteObjs.isNotEmpty()) {
            view.noteView.visibility = View.VISIBLE
        } else {
            view.noteView.visibility = View.GONE
        }


        val builder = SpannableStringBuilder()
        //val str1 = SpannableString("${book.Versecount}. ")
        val index = book.Versecount.toString()
        view.indexText.text = index
        /*str1.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(mContext?.activity!!, R.color.colorAccent)),
            0,
            str1.length,
            0
        );
        builder.append(str1);*/
        val verseStr = "$index. ${book.verse}"
        /*builder.append(book.verse)
        view.verseText.setText(builder, TextView.BufferType.SPANNABLE);*/

        if (highlightList.isNotEmpty()) {
            if (highlightObjs.isNotEmpty()) {
                val color = highlightObjs[0].color
                val span = SpannableString(verseStr)
                if (color.isNotEmpty()) {
                    span.setSpan(
                        BackgroundColorSpan(Color.parseColor(color)),
                        0,
                        verseStr.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                    builder.append(span)
                    view.verseText.setText(builder, TextView.BufferType.SPANNABLE);
                } else {
                    span.setSpan(UnderlineSpan(), 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    builder.append(span)
                    view.verseText.setText(builder, TextView.BufferType.SPANNABLE);
                }
            } else {
                builder.append(verseStr)
                view.verseText.setText(verseStr);
            }
        } else {
            //builder.append(verseStr)
            view.verseText.setText(verseStr);
        }



        if (selectedItems.contains(position)) {
            if (currentPos == position) {
                view.viewGroup.visibility = View.VISIBLE
            } else {
                view.viewGroup.visibility = View.GONE
            }
            view.verseText.setBackgroundColor(
                ContextCompat.getColor(
                    mContext?.activity!!,
                    R.color.color_1
                )
            )
        } else {
            view.viewGroup.visibility = View.GONE
            view.verseText.setBackgroundColor(Color.TRANSPARENT)
        }


        if (book.Versecount == 1) {
            view.chapterText.visibility = View.VISIBLE
            view.chapterText.text = "${book.Chapter}. "
        } else {
            view.chapterText.visibility = View.GONE
        }
        view.container.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                currentPos = position
                selectedItems.add(position)
                notifyDataSetChanged()
                return true;
            }

        })

        view.container.setOnClickListener {
            val bibleObj = list[position]
            val id = bibleObj.id
            val book = bibleObj.Book
            val chapter = bibleObj.Chapter
            val verseCount = bibleObj.Versecount
            val verse = bibleObj.verse
            val intent = Intent(mContext.context, DetailsActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("book", book)
            intent.putExtra("chapter", chapter)
            intent.putExtra("verseCount", verseCount)
            intent.putExtra("verse", verse)
            mContext.startActivity(intent)
            /*currentPos = position
            selectedItems.add(position)
            notifyDataSetChanged()*/
        }
        view.closeBtn.setOnClickListener {
            clearSelection()
        }
        view.micBtn.setOnClickListener {
            isPlay = if (!isPlay) {
                speakText()
                view.micBtn.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                true
            } else {
                tts?.stop()
                view.micBtn.setImageResource(R.drawable.ic_stop_icon);
                false
            }
        }
        view.addBtn.setOnClickListener {
            showDialog()
        }

        //textsize
        view.verseText.setTextSize(TypedValue.COMPLEX_UNIT_SP, verseText.toFloat());
        view.verseText.setLineSpacing(0f, lineSpace);

    }

    private fun clearSelection() {
        currentPos = 0
        selectedItems.clear()
        tts?.stop()
        isPlay = false
        notifyDataSetChanged()
    }

    private fun showDialog() {
        DialogBuilder.showChapterDialog(
            mContext?.activity!!,
            object : DialogListenerForBookMark {

                override fun dialogBookMark() {
                    if (selectedItems.isNotEmpty()) {
                        var favList: ArrayList<FavoriteModelEntry> = ArrayList()
                        for (values in selectedItems) {
                            val favObj = FavoriteModelEntry()
                            val bibleObj = list[values]
                            favObj.bibleId = bibleObj.id
                            favObj.book = bibleObj.Book
                            favObj.chapter = bibleObj.Chapter
                            favObj.versecount = bibleObj.Versecount
                            favObj.verse = bibleObj.verse
                            favObj.createdDate = Utils.getStringTimeStampWithDate()
                            favObj.bibleIndexName =
                                "${mContext.getBibleIndexName(bibleObj.Book)} ${bibleObj.Chapter}:${bibleObj.Versecount}"
                            //Utils.customEvent(analytics, pref, bibleObj.Book, bibleObj.Chapter, bibleObj.Versecount, bibleObj.verse, bibleObj.id, AppConstants.FAVORITE)
                            favList.add(favObj)

                        }
                        mContext.insertFavorites(favList)
                    }
                    clearSelection()
                }

                override fun dialogNote() {
                    DialogBuilder.showNoteDialog(
                        mContext.activity!!,
                        object : DialogListenerForNote {
                            override fun dialogNote(noteTxt: String) {
                                if (selectedItems.isNotEmpty()) {
                                    var noteList: ArrayList<NoteModelEntry> = ArrayList()
                                    for (values in selectedItems) {
                                        val noteObj = NoteModelEntry()
                                        val bibleObj = list[values]
                                        noteObj.bibleId = bibleObj.id
                                        noteObj.book = bibleObj.Book
                                        noteObj.chapter = bibleObj.Chapter
                                        noteObj.versecount = bibleObj.Versecount
                                        noteObj.verse = bibleObj.verse
                                        noteObj.createdDate = Utils.getStringTimeStampWithDate()
                                        noteObj.noteName = noteTxt
                                        noteObj.bibleIndexName =
                                            "${mContext.getBibleIndexName(bibleObj.Book)} ${bibleObj.Chapter}:${bibleObj.Versecount}"
                                        //Utils.customEvent(analytics, pref, bibleObj.Book, bibleObj.Chapter, bibleObj.Versecount, bibleObj.verse, bibleObj.id, AppConstants.NOTES)
                                        noteList.add(noteObj)
                                    }
                                    mContext.insertNotes(noteList)
                                }
                                clearSelection()
                            }
                        })
                }

                override fun dialogSelectedColor(color: String) {
                    setHighlightData(color)
                }

                override fun dialogHighLight() {
                    setHighlightData(null)
                }

                override fun dialogShare() {
                    if (selectedItems.isNotEmpty()) {
                        val strBuilder = StringBuilder()
                        for (values in selectedItems) {
                            val bibleObj = list[values]
                            //Utils.customEvent(analytics, pref, bibleObj.Book, bibleObj.Chapter, bibleObj.Versecount, bibleObj.verse, bibleObj.id, AppConstants.SHARE)
                            val bibleIndex =
                                "${mContext.getBibleIndexName(bibleObj.Book)} ${bibleObj.Chapter}:${bibleObj.Versecount}"
                            val verse = bibleObj.verse
                            strBuilder.append("$verse \n- $bibleIndex")
                            strBuilder.append("\n");


                        }
                        Utils.shareText(mContext.activity, strBuilder.toString())
                    }
                }

                override fun dialogCopy() {
                    if (selectedItems.isNotEmpty()) {
                        val strBuilder = StringBuilder()
                        for (values in selectedItems) {
                            val bibleObj = list[values]
                            //Utils.customEvent(analytics, pref, bibleObj.Book, bibleObj.Chapter, bibleObj.Versecount, bibleObj.verse, bibleObj.id, AppConstants.COPY)
                            val bibleIndex =
                                "${mContext.getBibleIndexName(bibleObj.Book)} ${bibleObj.Chapter}:${bibleObj.Versecount}"
                            val verse = bibleObj.verse
                            strBuilder.append("$verse \n- $bibleIndex")
                            strBuilder.append("\n");


                        }
                        Utils.copyText(mContext.activity, strBuilder.toString())
                        mContext.showSnackbar("Copied")
                    }
                }
            })
    }

    private fun setHighlightData(color: String?) {
        if (selectedItems.isNotEmpty()) {
            val highlightList: ArrayList<HighlightModelEntry> = ArrayList()
            for (values in selectedItems) {
                val highlightObj = HighlightModelEntry()
                val bibleObj = list[values]
                highlightObj.bibleId = bibleObj.id
                highlightObj.book = bibleObj.Book
                highlightObj.chapter = bibleObj.Chapter
                highlightObj.versecount = bibleObj.Versecount
                highlightObj.verse = bibleObj.verse
                if (color != null) {
                    highlightObj.color = color
                }
                //Utils.customEvent(analytics, pref, bibleObj.Book, bibleObj.Chapter, bibleObj.Versecount, bibleObj.verse, bibleObj.id, AppConstants.HIGHLIGHT)
                highlightObj.createdDate = Utils.getStringTimeStampWithDate()
                highlightObj.bibleIndexName =
                    "${mContext.getBibleIndexName(bibleObj.Book)} ${bibleObj.Chapter}:${bibleObj.Versecount}"

                highlightList.add(highlightObj)
            }
            mContext.insertHighlight(highlightList)
        }
        clearSelection()
    }

    private fun speakText() {
        val sb = StringBuilder()
        if (selectedItems.isNotEmpty()) {
            for (value in selectedItems) {
                sb.append(list[value].verse)
            }
            tts?.say(sb.toString(), lng)
        }


    }

    fun updateData(list: ArrayList<BibleModelEntry>) {
        this.list = list
        this.originalList = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                list = if (charString.isEmpty()) {
                    originalList
                } else {
                    val filteredList: ArrayList<BibleModelEntry> = ArrayList()
                    for (obj in originalList) {
                        if (obj.verse.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                list = filterResults.values as ArrayList<BibleModelEntry>
                notifyDataSetChanged()
            }
        }
    }

    fun getData(): ArrayList<BibleModelEntry> {
        return list;
    }

    fun updateDataFavData(it: List<FavoriteModelEntry>) {
        this.favList = it
        notifyDataSetChanged()
    }

    fun updateDataNotesData(it: List<NoteModelEntry>) {
        this.notesList = it
        notifyDataSetChanged()
    }

    fun updateDataHighlightsData(it: List<HighlightModelEntry>) {
        this.highlightList = it
        notifyDataSetChanged()
    }

    fun updateTheme() {
        val themeSize =  SharedPrefUtils.getTheme(pref)
        var getLineSpace =  SharedPrefUtils.getLineSpace(pref)
        when (themeSize) {
            AppConstants.TEXT_SIZE_1 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_2 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_3 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_4 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_5 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_6 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_7 -> {
                setTheme(themeSize)
            }
            AppConstants.TEXT_SIZE_8 -> {
                setTheme(themeSize)
            }
        }
        when (getLineSpace) {
            AppConstants.TEXT_LINE_1 -> {
                lineSpace = 1f
            }
            AppConstants.TEXT_LINE_2 -> {
                lineSpace = 1.2f
            }
            AppConstants.TEXT_LINE_3 -> {
                lineSpace = 1.5f
            }
        }
        notifyDataSetChanged()

    }

    private fun setTheme(themeSize: Int) {
        verseText = verseDefaultText + themeSize
        indexText = indexTextDefault + themeSize
    }

}