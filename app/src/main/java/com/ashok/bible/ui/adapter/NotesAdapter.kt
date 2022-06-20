package com.ashok.bible.ui.adapter

import android.content.Context
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import com.ashok.bible.R
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.ui.bibleindex.BibleIndexActivity
import com.ashok.bible.ui.model.BibleNumberIndexModel
import com.ashok.bible.ui.notes.NotesFragment
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter


class NotesAdapter constructor(var mContext: NotesFragment?, var list: List<NoteModelEntry>, var originalList: List<NoteModelEntry> = list):
    RecyclerBaseAdapter<NoteModelEntry?>() {

    override fun getDataAtPosition(position: Int): NoteModelEntry? {
        return list[position]
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        return R.layout.note_row
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(list: List<NoteModelEntry>) {
        this.list = list
        this.originalList = list
        notifyDataSetChanged()
    }

    fun onClick(view: View, obj: NoteModelEntry){
        mContext?.onClickItem(obj)
    }

    fun onClickDelete(view:View, entry: NoteModelEntry){
        mContext?.deleteRow(entry)
    }


}