package com.ashok.bible.ui.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import com.ashok.bible.R
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.databinding.BibleIndexNumberRowBinding
import com.ashok.bible.databinding.BibleIndexRowBinding
import com.ashok.bible.databinding.QuotesIndexRowBinding
import com.ashok.bible.ui.bibleindex.BibleIndexActivity
import com.ashok.bible.ui.model.BibleNumberIndexModel
import com.ashok.bible.ui.quotes.QuotesFragment
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter


class QuotesIndexAdapter constructor(var mContext: QuotesFragment?, var list: List<String>, var originalList: List<String> = list):
    RecyclerBaseAdapter<String?>() {

    override fun getDataAtPosition(position: Int): String? {
        return list[position]
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        return R.layout.quotes_index_row
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(list: ArrayList<String>) {
        this.list = list
        this.originalList = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var view = holder.binding as QuotesIndexRowBinding


        /*  val gradientDrawable = (view.bibleChapterCount.background as GradientDrawable).mutate()
          (gradientDrawable as GradientDrawable).setColor(Utils.colorCodeByPos(mContext?.activity, position))
          view.bibleChapterCount.text = ""+(position+1)*/

        view.container.setCardBackgroundColor(Utils.colorCodeByPos(mContext?.context, position))


    }
    public fun onClick(view: View, obj:String){
        mContext?.onclickChapter(obj)
    }

}