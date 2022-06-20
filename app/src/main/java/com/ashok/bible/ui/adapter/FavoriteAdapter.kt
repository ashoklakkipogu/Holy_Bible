package com.ashok.bible.ui.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.ashok.bible.R
import com.ashok.bible.databinding.HighlightRowBinding
import com.ashok.bible.databinding.HighlightRowNBinding
import com.ashok.bible.ui.favorite.FavoriteFragment
import com.ashok.bible.ui.model.FavBookMark
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter


class FavoriteAdapter constructor(var mContext: FavoriteFragment?, var list: List<FavBookMark>, var originalList: List<FavBookMark> = list):
    RecyclerBaseAdapter<FavBookMark?>() {

    companion object {
        const val BOOKMARK_VIEW = 0
        const val HIGHLIGHT_VIEW = 1
    }

    override fun getDataAtPosition(position: Int): FavBookMark? {
        return list[position]
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        if (viewType == BOOKMARK_VIEW){
            return R.layout.favorite_row
        }else if (viewType == HIGHLIGHT_VIEW){
            return R.layout.highlight_row_n
        }
        return R.layout.favorite_row
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(list: List<FavBookMark>) {
        this.list = list
        this.originalList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (getItemViewType(position) == HIGHLIGHT_VIEW){
            val view = holder.binding as HighlightRowNBinding
            val list = list[position]
            val verseStr = list.verse
            val builder = SpannableStringBuilder()
            val color = list.color
            val span = SpannableString(verseStr)
            if (color.isNotEmpty()) {
                span.setSpan(
                    BackgroundColorSpan(Color.parseColor(color)),
                    0,
                    verseStr.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                builder.append(span)
                view.contentView.setText(builder, TextView.BufferType.SPANNABLE);
            } else {
                span.setSpan(UnderlineSpan(), 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.append(span)
                view.contentView.setText(builder, TextView.BufferType.SPANNABLE);
            }
        }
    }

    fun onClick(view: View, obj:FavBookMark){
        mContext?.onClickItem(obj)
        //mContext?.onclickChapter(chaptertId)
    }

    fun onClickDelete(view:View, entry: FavBookMark){
        mContext?.deleteRow(entry)
    }

    override fun getItemViewType(position: Int): Int {
        val obj = list[position]
        return if (obj.isFav) {
            BOOKMARK_VIEW
        } else {
            HIGHLIGHT_VIEW
        }
    }

}