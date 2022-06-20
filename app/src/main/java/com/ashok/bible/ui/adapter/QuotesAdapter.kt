package com.ashok.bible.ui.adapter

import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.view.View
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.remote.model.QuotesModel
import com.ashok.bible.databinding.BibleIndexNumberRowBinding
import com.ashok.bible.databinding.QuotesRowBinding
import com.ashok.bible.databinding.QuotesRowBindingImpl
import com.ashok.bible.ui.favorite.FavoriteFragment
import com.ashok.bible.ui.quotes.QuotesDetailsActivity
import com.ashok.bible.ui.quotes.QuotesFragment
import com.ashok.bible.utils.TtsManager
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter
import kotlinx.android.synthetic.main.quotes_row.view.*
import java.util.*


class QuotesAdapter constructor(var mContext: QuotesDetailsActivity?, var lng: Locale, var tts: TtsManager?, var list: List<QuotesModel>, var originalList: List<QuotesModel> = list) :
        RecyclerBaseAdapter<QuotesModel?>() {
    var selectedPos: Int = -1


    override fun getDataAtPosition(position: Int): QuotesModel? {
        return list[position]
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        return R.layout.quotes_row
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(list: List<QuotesModel>) {
        this.list = list
        this.originalList = list
        notifyDataSetChanged()
    }

    fun onClickShare(view: View, obj: QuotesModel) {
        val strBuilder = StringBuilder()
        val bibleIndex = obj.quote
        val verse = obj.author
        strBuilder.append("$verse \n- $bibleIndex")
        strBuilder.append("\n");
        Utils.shareText(mContext!!, strBuilder.toString())

    }

    fun onClickCopy(view: View, obj: QuotesModel) {
        val strBuilder = StringBuilder()
        val bibleIndex = obj.quote
        val verse = obj.author
        strBuilder.append("$verse \n- $bibleIndex")
        strBuilder.append("\n");
        Utils.copyText(mContext!!, strBuilder.toString())
        mContext!!.showSnackbar("Copied")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val obj = list[position]
        val view = holder.binding as QuotesRowBinding
        view.container.setCardBackgroundColor(Utils.colorCodeByPos(mContext!!, position))
        if(obj.author.isNotEmpty()){
            view.authorName.text = "-${obj.author}"
            view.authorName.visibility = View.VISIBLE
        }else{
            view.authorName.visibility = View.GONE
        }
        if (obj.title.isEmpty()){
            view.bibleTitle.visibility = View.GONE
        }else{
            //view.bibleTitle.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
            view.bibleTitle.visibility = View.VISIBLE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.bibleChapter.text = Html.fromHtml(obj.quote, Html.FROM_HTML_MODE_LEGACY);
        } else {
            view.bibleChapter.text = Html.fromHtml(obj.quote);
        }

        if (selectedPos == position) {
            speakText(obj)
        }
        view.voiceImage.setOnClickListener {
            tts?.stop()
            selectedPos = position
            notifyDataSetChanged()
        }
    }

    private fun speakText(obj: QuotesModel) {
        val text = Utils.html2text(obj.quote)
        tts?.say(text, lng)


    }
}