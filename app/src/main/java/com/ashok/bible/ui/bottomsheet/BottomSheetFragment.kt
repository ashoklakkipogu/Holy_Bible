package com.ashok.bible.ui.bottomsheet

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.ui.MainActivity
import com.ashok.bible.utils.SharedPrefUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import javax.inject.Inject

class BottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        pref = (activity as MainActivity).pref
        view.down_img.setOnClickListener {
            dismiss()
        }
        view.small_text.setOnClickListener {
            val textTheme = SharedPrefUtils.getTheme(pref)
            if (textTheme != AppConstants.TEXT_SIZE_1)
                SharedPrefUtils.setTheme(pref, textTheme - 1)
            (activity as MainActivity).setThemeView()

        }
        view.big_text.setOnClickListener {
            val textTheme = SharedPrefUtils.getTheme(pref)
            if (textTheme != AppConstants.TEXT_SIZE_8)
                SharedPrefUtils.setTheme(pref, textTheme + 1)
            (activity as MainActivity).setThemeView()

        }
        view.text_adjust.setOnClickListener {
            val lineSpace = SharedPrefUtils.getLineSpace(pref)
            if (lineSpace == AppConstants.TEXT_LINE_1)
                SharedPrefUtils.setLineSpace(pref, AppConstants.TEXT_LINE_2)
            if (lineSpace == AppConstants.TEXT_LINE_2)
                SharedPrefUtils.setLineSpace(pref, AppConstants.TEXT_LINE_3)
            if (lineSpace == AppConstants.TEXT_LINE_3)
                SharedPrefUtils.setLineSpace(pref, AppConstants.TEXT_LINE_1)
            (activity as MainActivity).setThemeView()
        }
        return view
    }
}