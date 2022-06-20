package com.ashok.bible.ui.adapter

import android.os.Handler
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ashok.bible.R
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.databinding.LyricRowBinding
import com.ashok.bible.ui.lyrics.*
import com.ashok.bible.utils.RandomColors
import com.lakki.kotlinlearning.view.base.RecyclerBaseAdapter


class LyricsAdapter constructor(
    var mContext: Fragment?,
    var list: ArrayList<LyricsModel>,
    var isSecondLan: Boolean,
    val emptyView: TextView,
    var originalList: List<LyricsModel> = list
) :
    RecyclerBaseAdapter<LyricsModel?>(), Filterable {

    val VIEW_TYPE_ITEM = 0
    val VIEW_TYPE_LOADING = 1

    override fun getDataAtPosition(position: Int): LyricsModel? {
        val obj = list[position];
        obj.isSecondLan = isSecondLan
        return obj
    }

    override fun getLayoutIdForType(viewType: Int): Int {
        if (viewType == VIEW_TYPE_ITEM){
            return R.layout.lyric_row
        }else if(viewType == VIEW_TYPE_LOADING){
            return R.layout.progress_loading
        }
        return R.layout.lyric_row
    }


    fun updateData(list: ArrayList<LyricsModel>) {
        this.list = list
        this.originalList = list
        if (list.isEmpty()) {
            emptyView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.GONE
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val obj = list[position]
        if (!obj.isProgressBar){
            val binding = holder.binding as LyricRowBinding
            if (obj.isSecondLan) {
                binding.icon.letter = list[position].titleEn
            } else {
                binding.icon.letter = list[position].title
            }
            binding.icon.shapeColor = RandomColors().color
        }

    }

    fun onClick(view: View, obj: LyricsModel) {
        val index = list.indexOf(obj)
        if (mContext is LyricsFirstLangFragment) {
            (mContext as LyricsFirstLangFragment).onClickDraggableView(
                obj,
                list,
                isSecondLan,
                index
            )
        } else if (mContext is LyricsSecondLangFragment) {
            (mContext as LyricsSecondLangFragment).onClickDraggableView(
                obj,
                list,
                isSecondLan,
                index
            )
        }
        /*val intent = Intent(mContext!!.context, LyricDetailsActivity::class.java)
        val bundle = Bundle()
        //bundle.putSerializable("lyric", obj)
        bundle.putSerializable("lyrics", list as Serializable)
        bundle.putBoolean("isSecondLan", isSecondLan)
        val index = list.indexOf(obj)
        bundle.putInt("pos", index)
        intent.putExtras(bundle)
        mContext!!.startActivity(intent)*/

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                list = if (charString.isEmpty()) {
                    originalList as ArrayList<LyricsModel>
                } else {
                    val filteredList: ArrayList<LyricsModel> = ArrayList()
                    for (obj in originalList) {
                        if (obj.title.toLowerCase()
                                .contains(charString.toLowerCase()) || obj.titleEn.toLowerCase()
                                .contains(charString.toLowerCase())
                        ) {
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
                list = filterResults.values as ArrayList<LyricsModel>
                notifyDataSetChanged()
            }
        }

    }

    /*override fun onChange(position: Int): CharSequence {
        val obj=list[position]
        return if (obj.isSecondLan){
            if (obj.titleEn.matches(Patterns.PHONE.toRegex()))
                "#"
            else
                obj.titleEn[0].toString().toUpperCase()
        }else{
            if (obj.title.matches(Patterns.PHONE.toRegex()))
                "#"
            else
                obj.title[0].toString().toUpperCase()
        }

    }*/


    fun addLoadingView() {
        Handler().post {
            val lyric = LyricsModel()
            lyric.isProgressBar = true
            list.add(lyric)
            notifyItemInserted(list.size - 1)
        }
    }

    fun removeLoadingView() {
        list.removeAt(list.size - 1)
        notifyItemRemoved(list.size)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].isProgressBar) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun addData(dataViews: List<LyricsModel>) {
        list.addAll(dataViews)
        notifyDataSetChanged()
    }
}