package com.ashok.bible.databinding

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ashok.bible.R
import com.ashok.bible.utils.Utils
import com.squareup.picasso.Picasso


class DataBindingAdapter {
    companion object {

        @BindingAdapter("createdData")
        @JvmStatic
        fun setCreatedData(view: TextView, resource: String) {
            if (resource.isNotEmpty()){
                view.text = Utils.getUtcToDDMMYYYYHHMMA(resource)
            }else{
                view.text = ""
            }
        }

        @BindingAdapter("fontFml")
        @JvmStatic
        fun setFontFamily(view: TextView, type:String) {
            if (type.isNotEmpty() && type == "akayakanadaka"){
                val face =
                    Typeface.createFromAsset(view.context.assets, "fonts/AkayaKanadaka-Regular.ttf")
                view.typeface = face
            }


        }
        @BindingAdapter("image")
        @JvmStatic
        fun setImage(view: ImageView, data: String?) {
            if (data.isNullOrEmpty()){
                view.visibility = View.GONE
            }else{
                view.visibility = View.VISIBLE
                Picasso.get().load(data).fit().centerInside().placeholder(R.drawable.place_holder).into(view)
            }
        }



    }

}