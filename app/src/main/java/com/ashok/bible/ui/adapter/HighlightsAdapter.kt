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
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.databinding.HighlightRowBinding
import com.ashok.bible.ui.highlights.HighlightsFragment
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter


class HighlightsAdapter constructor(
    var mContext: HighlightsFragment?,
    var list: List<HighlightModelEntry>,
    var originalList: List<HighlightModelEntry> = list
) :
    RecyclerBaseAdapter<HighlightModelEntry?>() {

    override fun getDataAtPosition(position: Int): HighlightModelEntry? {
        return list[position]
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        return R.layout.highlight_row
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(list: List<HighlightModelEntry>) {
        this.list = list
        this.originalList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val view = holder.binding as HighlightRowBinding
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

        view.container.setOnClickListener {
            mContext?.onClickItem(list)
        }
    }
    fun onClickDelete(view:View, entry: HighlightModelEntry){
        mContext?.deleteRow(entry)
    }
}
