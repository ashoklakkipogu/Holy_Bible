package com.lakki.kotlinlearning.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.attr.data
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ashok.bible.BR


abstract class RecyclerBaseAdapter<T>() : RecyclerView.Adapter<RecyclerBaseAdapter<T>.MyViewHolder>() {

    lateinit var binding: ViewDataBinding

    abstract fun getDataAtPosition(position: Int): T

    abstract fun getLayoutIdForType(viewType: Int): Int

    var itemClickListener: OnItemClickListener<T>? = null

    interface OnItemClickListener<T> {
        fun onItemClick(item: T, v: View)
    }


    constructor(itemClickListener: OnItemClickListener<T>) : this() {
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(this.getDataAtPosition(position))
        this.itemClickListener?.onItemClick(this.getDataAtPosition(position), holder.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        this.binding = DataBindingUtil.inflate(layoutInflater, this.getLayoutIdForType(viewType), parent, false)
        return this.MyViewHolder(this.binding)

    }


    inner class MyViewHolder(view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {
        var binding: ViewDataBinding = view


        fun getDataBinding(): ViewDataBinding {
            return this.binding
        }

        fun <T> bind(item: T) {
            binding.setVariable(BR.obj, item)

            this.binding.setVariable(BR.handlers, this@RecyclerBaseAdapter)
            this.binding.executePendingBindings()
        }


    }
}