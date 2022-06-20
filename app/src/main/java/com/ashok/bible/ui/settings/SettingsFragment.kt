package com.ashok.bible.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.data.local.dao.BibleDao
import com.ashok.bible.databinding.FragmentSettingBinding
import com.ashok.bible.ui.feedback.FeedbackActivity
import com.ashok.bible.ui.home.HomeViewModel
import com.ashok.bible.utils.DialogBuilder
import com.ashok.bible.utils.DialogListenerForLanguage
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.Utils
import com.ashok.bible.data.local.dao.FavoriteDao
import com.ashok.bible.data.local.dao.HighlightDao
import com.ashok.bible.data.local.dao.NoteDao
import com.google.gson.Gson
import com.lakki.kotlinlearning.view.base.BaseFragment
import javax.inject.Inject


class SettingsFragment : BaseFragment<HomeViewModel, FragmentSettingBinding>() {

    @Inject
    lateinit var highlightDao: HighlightDao
    @Inject
    lateinit var noteDao: NoteDao
    @Inject
    lateinit var favoriteDao: FavoriteDao

    @Inject
    lateinit var b: BibleDao

    override fun getLayoutRes(): Int {
        return R.layout.fragment_setting
    }

    override fun init() {
        binding.handlers = this
        binding.dayNightSwitch.isChecked = SharedPrefUtils.isDayOrNight(pref)

        binding.dayNightSwitch.setOnCheckedChangeListener { _, isChecked ->
            SharedPrefUtils.setDayOrNight(pref, isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.feedback_view -> {
                startActivity(Intent(activity, FeedbackActivity::class.java))
            }
            R.id.change_language -> {
                DialogBuilder.showLanguage(
                    activity!!,
                    object : DialogListenerForLanguage {
                        override fun language(selectedLan: String) {
                            val lan  = SharedPrefUtils.getLanguage(pref)
                            lan?.let {
                                if (selectedLan !=lan ){
                                    val ab: AlertDialog.Builder = AlertDialog.Builder(activity)
                                    ab.setTitle("Change language")
                                    ab.setMessage("Are you sure you want to change the language?")
                                    ab.setPositiveButton("Yes") { _, id ->
                                        highlightDao.getAllHighlight().observe(this@SettingsFragment, Observer {highlighList->
                                            if(highlighList.isNotEmpty()){
                                                SharedPrefUtils.saveHighLights(pref, Gson().toJson(highlighList))
                                            }
                                            favoriteDao.getAllFavorites().observe(this@SettingsFragment, Observer {favoriteList ->
                                                if(favoriteList.isNotEmpty()){
                                                    SharedPrefUtils.saveFavorite(pref, Gson().toJson(favoriteList))
                                                }
                                                noteDao.getAllNote().observe(this@SettingsFragment, Observer {noteList ->
                                                    if(noteList.isNotEmpty()){
                                                        SharedPrefUtils.saveNote(pref, Gson().toJson(noteList))
                                                    }
                                                    Utils.deleteDb(activity!!, selectedLan, pref)
                                                })
                                            })

                                        })
                                    }
                                    ab.setNegativeButton(
                                        "No"
                                    ) { pObjDialog, id -> pObjDialog.dismiss() }
                                    ab.show()


                                }
                            }




                            //(activity as MainActivity).moveToHomePage()
                        }

                    })
            }
            R.id.share_view -> {
                Utils.shareApp(activity!!)
            }
        }
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
}
