package com.ashok.bible.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.widget.*
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants


class DialogBuilder {
    companion object {
        fun showChapterDialog(
            context: Activity,
            listener: DialogListenerForBookMark
        ) {
            val inflater = context.layoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_chapter, null)
            val closeView: RelativeLayout = alertLayout.findViewById(R.id.close_view)
            val noteView: TextView = alertLayout.findViewById(R.id.note_view)
            val bookMarkView: TextView = alertLayout.findViewById(R.id.book_mark_view)
            val highlightsView: RelativeLayout = alertLayout.findViewById(R.id.highlights_view)
            val color1: View = alertLayout.findViewById(R.id.color_1)
            val color2: View = alertLayout.findViewById(R.id.color_2)
            val color3: View = alertLayout.findViewById(R.id.color_3)
            val color4: View = alertLayout.findViewById(R.id.color_4)
            val shareView: RelativeLayout = alertLayout.findViewById(R.id.share_view)
            val copyView: RelativeLayout = alertLayout.findViewById(R.id.copy_view)
            val lObjBuilder = AlertDialog.Builder(context).create()
            lObjBuilder.setCancelable(true)
            lObjBuilder.setView(alertLayout);
            closeView.setOnClickListener {
                lObjBuilder.dismiss()
            }
            noteView.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogNote()
            }
            bookMarkView.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogBookMark()
            }
            highlightsView.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogHighLight()
            }
            color1.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogSelectedColor("#FFEB3B")
            }
            color2.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogSelectedColor("#F44336")
            }
            color3.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogSelectedColor("#3F51B5")
            }
            color4.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogSelectedColor("#4CAF50")
            }
            shareView.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogShare()
            }
            copyView.setOnClickListener {
                lObjBuilder.dismiss()
                listener.dialogCopy()
            }
            lObjBuilder.show()
        }

        fun showNoteDialog(
            context: Activity,
            listener: DialogListenerForNote
        ) {
            val inflater = context.layoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_note, null)
            val noteText: EditText = alertLayout.findViewById(R.id.note_text)
            val cancelBtn: Button = alertLayout.findViewById(R.id.cancel)
            val doneBtn: Button = alertLayout.findViewById(R.id.done)

            val lObjBuilder = AlertDialog.Builder(context).create()
            lObjBuilder.setCancelable(false)
            lObjBuilder.setTitle("Bible Notes")
            lObjBuilder.setView(alertLayout)

            cancelBtn.setOnClickListener {
                lObjBuilder.dismiss()
            }
            doneBtn.setOnClickListener {
                val str = noteText.text.toString()
                if (str.isNotEmpty()) {
                    lObjBuilder.dismiss()
                    listener.dialogNote(str)
                } else {
                    Toast.makeText(context, "Please enter the note", Toast.LENGTH_SHORT).show()
                }
            }

            /*lObjBuilder.setPositiveButton("DONE") { pObjDialog, id ->
                val str = noteText.text.toString()
                if(str.isNotEmpty()){
                    pObjDialog.dismiss()
                    listener.dialogNote(str)
                }else{
                    Toast.makeText(context, "Please enter the note", Toast.LENGTH_SHORT).show()
                }

            }
            lObjBuilder.setNegativeButton(
                "Cancel"
            ) { pObjDialog, id -> pObjDialog.dismiss() }*/
            lObjBuilder.show()
        }

        fun showLanguage(
            context: Activity,
            listener: DialogListenerForLanguage
        ) {
            val inflater = context.layoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_language, null)
            val teluguTxt: TextView = alertLayout.findViewById(R.id.telugu_txt)
            val tamilTxt: TextView = alertLayout.findViewById(R.id.tamil_txt)
            val englishTxt: TextView = alertLayout.findViewById(R.id.english_txt)

            val lObjBuilder = AlertDialog.Builder(context).create()
            lObjBuilder.setCancelable(true)
            lObjBuilder.setTitle("Change Language")
            lObjBuilder.setView(alertLayout)

            teluguTxt.setOnClickListener {
                listener.language(AppConstants.TELUGU)
                lObjBuilder.dismiss()
            }
            tamilTxt.setOnClickListener {
                listener.language(AppConstants.TAMIL)
                lObjBuilder.dismiss()
            }
            englishTxt.setOnClickListener {
                listener.language(AppConstants.ENGLISH)
                lObjBuilder.dismiss()
            }
            lObjBuilder.show()
        }

        fun showNameDialog(
            context: Activity,
            listener: DialogListenerForName
        ) {
            val inflater = context.layoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_name, null)
            val nameTxt: EditText = alertLayout.findViewById(R.id.name_text)
            val cancelBtn: Button = alertLayout.findViewById(R.id.cancel)
            val doneBtn: Button = alertLayout.findViewById(R.id.done)

            val lObjBuilder = AlertDialog.Builder(context).create()
            lObjBuilder.setCancelable(false)
            lObjBuilder.setTitle("Name")
            lObjBuilder.setView(alertLayout)

            cancelBtn.setOnClickListener {
                lObjBuilder.dismiss()
            }
            doneBtn.setOnClickListener {
                val str = nameTxt.text.toString()
                if (str.isNotEmpty()) {
                    lObjBuilder.dismiss()
                    listener.dialogName(str)
                } else {
                    Toast.makeText(context, "Please enter the full name", Toast.LENGTH_SHORT).show()
                }
            }
            lObjBuilder.show()
        }

        fun showFilter(
            context: Activity,
            filterValue: String,
            listener: DialogListenerForLyricFilter
        ) {
            val inflater = context.layoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_lyric_filter, null)

            val radioGroup: RadioGroup = alertLayout.findViewById(R.id.radio_group)
            radioGroup.clearCheck()
            val radio1 = radioGroup.findViewById(R.id.radio_all) as RadioButton
            val radio2 = radioGroup.findViewById(R.id.radio_telugu) as RadioButton
            val radio3 = radioGroup.findViewById(R.id.radio_tamil) as RadioButton
            val radio4 = radioGroup.findViewById(R.id.radio_english) as RadioButton
            val radio5 = radioGroup.findViewById(R.id.radio_hindi) as RadioButton
            when(filterValue){
                AppConstants.FILTER_ALL->{
                    radio1.isChecked = true
                }
                AppConstants.FILTER_TELUGU->{
                    radio2.isChecked = true
                }
                AppConstants.FILTER_TAMIL->{
                    radio3.isChecked = true
                }
                AppConstants.FILTER_ENGLISH->{
                    radio4.isChecked = true
                }
                AppConstants.FILTER_HINDI->{
                    radio5.isChecked = true
                }
            }
            val lObjBuilder = AlertDialog.Builder(context).create()
            lObjBuilder.setCancelable(true)
            lObjBuilder.setTitle(context.getString(R.string.select_language))
            lObjBuilder.setView(alertLayout)
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val rb = group.findViewById(checkedId) as RadioButton
                rb.isChecked = true
                var filterValue = AppConstants.FILTER_ALL
                when(rb.id){
                    R.id.radio_all->{
                        filterValue = AppConstants.FILTER_ALL
                    }
                    R.id.radio_telugu->{
                        filterValue = AppConstants.FILTER_TELUGU
                    }
                    R.id.radio_tamil->{
                        filterValue = AppConstants.FILTER_TAMIL
                    }
                    R.id.radio_english->{
                        filterValue = AppConstants.FILTER_ENGLISH
                    }
                    R.id.radio_hindi->{
                        filterValue = AppConstants.FILTER_HINDI
                    }
                }
                listener.language(filterValue)
                lObjBuilder.dismiss()
            }
            lObjBuilder.show()
        }
    }


}

interface DialogListenerForBookMark {
    abstract fun dialogBookMark()
    abstract fun dialogNote()
    abstract fun dialogSelectedColor(color: String)
    abstract fun dialogHighLight()
    abstract fun dialogShare()
    abstract fun dialogCopy()
}

interface DialogListenerForNote {
    abstract fun dialogNote(string: String)

}

interface DialogListenerForName {
    abstract fun dialogName(string: String)

}

interface DialogListenerForLanguage {
    abstract fun language(id: String)

}

interface DialogListenerForLyricFilter {
    abstract fun language(filterValue: String)
}