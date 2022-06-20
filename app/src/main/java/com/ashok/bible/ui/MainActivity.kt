package com.ashok.bible.ui

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.data.remote.model.UserModel
import com.ashok.bible.databinding.ActivityMainBinding
import com.ashok.bible.ui.bibleindex.BibleIndexActivity
import com.ashok.bible.ui.bottomsheet.BottomSheetFragment
import com.ashok.bible.ui.feedback.FeedbackActivity
import com.ashok.bible.ui.home.HomeFragment
import com.ashok.bible.ui.lyrics.LyricsFragment
import com.ashok.bible.utils.DialogBuilder
import com.ashok.bible.utils.DialogListenerForName
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.Utils
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lakki.kotlinlearning.view.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
        HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    //public lateinit var searchView: MaterialSearchView
    lateinit var titleView: ConstraintLayout
    lateinit var titleText: TextView
    lateinit var chapterText: TextView
    lateinit var verseText: TextView
    lateinit var darkModeSwitch: SwitchCompat
    //public var actionSearch: MenuItem? = null
    lateinit var navShare: MenuItem
    lateinit var navFeedback: MenuItem
    lateinit var navLanguage: MenuItem

    private var chapterId: Int = 0
    private var verseId: Int = 0
    private var bookId: Int = 0
    private var actionFontSize: MenuItem? = null



    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        titleView = findViewById(R.id.title_view)
        titleText = findViewById(R.id.title_text)
        chapterText = findViewById(R.id.chapter_text)
        verseText = findViewById(R.id.verse_text)
        darkModeSwitch =
                main_navigation_view.menu.findItem(R.id.nav_darkmode_id).actionView as SwitchCompat
        navShare = main_navigation_view.menu.findItem(R.id.navigation_share)
        navFeedback = main_navigation_view.menu.findItem(R.id.navigation_feedback)
        navLanguage = main_navigation_view.menu.findItem(R.id.navigation_language)
        setDarkModeSwitchListener()
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent_2))
        navController = findNavController(R.id.main_nav_host) //Initialising navController

        appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_bookmark,
                R.id.navigation_notes,
                R.id.navigation_quotes,
                R.id.navigation_lyrics
        ) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
                .setDrawerLayout(main_drawer_layout) //Pass the drawer layout id from activity xml
                .build()

        setupActionBarWithNavController(
                navController,
                appBarConfiguration
        ) //Setup toolbar with back button and drawer icon according to appBarConfiguration

        visibilityNavElements(navController) //If you want to hide drawer or bottom navigation configure that in this function
        initSavedData()
        titleText.setOnClickListener {
            val intent = Intent(this, BibleIndexActivity::class.java)
            intent.putExtra("chapterId", chapterId)
            intent.putExtra("verseId", verseId)
            intent.putExtra("bookId", bookId)
            intent.putExtra("pageType", 1)
            startActivityForResult(intent,
                    AppConstants.PAGE_INDEX_REQUEST_CODE
            )
        }

        chapterText.setOnClickListener {
            val intent = Intent(this, BibleIndexActivity::class.java)
            intent.putExtra("chapterId", chapterId)
            intent.putExtra("verseId", verseId)
            intent.putExtra("bookId", bookId)
            intent.putExtra("pageType", 2)
            startActivityForResult(intent,
                    AppConstants.PAGE_INDEX_REQUEST_CODE
            )
        }

        verseText.setOnClickListener {
            val intent = Intent(this, BibleIndexActivity::class.java)
            intent.putExtra("chapterId", chapterId)
            intent.putExtra("verseId", verseId)
            intent.putExtra("bookId", bookId)
            intent.putExtra("pageType", 3)
            startActivityForResult(intent,
                    AppConstants.PAGE_INDEX_REQUEST_CODE
            )
        }
        val name = SharedPrefUtils.getUserName(pref)
        with(viewModel) {
            if (!SharedPrefUtils.isSavedUser(pref) && name != null) {
                val obj = UserModel()
                obj.createdDate = Utils.getCurrentTime()
                obj.userName = name
                obj.language = SharedPrefUtils.getLanguage(pref)!!
                saveUser(obj)
            }
            insertHighlight.observe(this@MainActivity, Observer {
                SharedPrefUtils.removeData(pref, SharedPrefUtils.HIGHLIGHT_MODEL)
            })
            insertFav.observe(this@MainActivity, Observer {
                SharedPrefUtils.removeData(pref, SharedPrefUtils.FAVORITE_MODEL)
            })
            insertNotes.observe(this@MainActivity, Observer {
                SharedPrefUtils.removeData(pref, SharedPrefUtils.NOTE_MODEL)
            })
            userData.observe(this@MainActivity, Observer {
                SharedPrefUtils.setSavedUser(pref)
            })
            error.observe(this@MainActivity, Observer {

            })
        }
        if (name == null)
            initDialogName()

        checkForUpdates()

        drawerNavController()
    }

    private fun initDialogName() {
        DialogBuilder.showNameDialog(
                this,
                object : DialogListenerForName {
                    override fun dialogName(string: String) {
                        SharedPrefUtils.setUserName(pref, string)
                        //firebaseAnalytics.setUserProperty(AppConstants.USER_NAME, string)
                    }
                })
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        actionFontSize = menu.findItem(R.id.action_font_size)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_font_size -> {
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun checkForUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo


        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            AppConstants.UPDATE_REQUEST_CODE
                    );
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace();
                }
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.FLEXIBLE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            AppConstants.UPDATE_REQUEST_CODE
                    );
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace();
                }

            }
        }
    }


    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            /*when (destination.id) {
                R.id.profileFragment -> hideBothNavigation()
                R.id.settingsFragment -> hideBottomNavigation()
                else -> showBothNavigation()
            }*/
            when (destination.id) {
                R.id.navigation_home -> {
                    titleView.visibility = View.VISIBLE
                    actionFontSize?.isVisible = true
                    showBothNavigation()
                }
                /* R.id.navigation_lyric -> {
                    titleView.visibility = View.GONE
                    actionSearch?.isVisible = true
                    showBothNavigation()
                }*/
                R.id.navigation_share -> {
                    Utils.shareApp(this)
                }
                R.id.navigation_feedback -> {
                    startActivity(Intent(this, FeedbackActivity::class.java))
                }
                else -> {
                    titleView.visibility = View.GONE
                    actionFontSize?.isVisible = false
                    showBothNavigation()
                }
            }

        }

    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        main_bottom_navigation_view?.visibility = View.GONE
        main_navigation_view?.visibility = View.GONE
        main_drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        main_bottom_navigation_view?.visibility = View.GONE
        main_navigation_view?.visibility = View.VISIBLE
        main_drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer

        main_navigation_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        main_bottom_navigation_view?.visibility = View.VISIBLE
        main_navigation_view?.visibility = View.VISIBLE
        main_drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        main_navigation_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
        main_bottom_navigation_view?.setupWithNavController(navController) //Setup Bottom navigation with navController
    }

    fun exitApp() { //To exit the application call this function from fragment
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(
                navController,
                appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            main_drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                main_drawer_layout.closeDrawer(GravityCompat.START)
            }
            else -> {
                 val fragment = main_nav_host?.childFragmentManager?.primaryNavigationFragment
                 if(fragment is LyricsFragment){
                     if(fragment.isOpenDraggablePanel){
                         fragment.onBackPressed()
                     }else {
                         super.onBackPressed() //If drawer is already in closed condition then go back
                     }

                 }else{
                     super.onBackPressed() //If drawer is already in closed condition then go back
                 }
            }
        }
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentAndroidInjector
    }

    fun onClickItem(bibleId: Int) {
        var bundle = bundleOf("BibleID" to bibleId)
        navController.navigate(R.id.navigation_home, bundle)
    }

    fun updateToolBar(title: String, chapterId: Int, verseId: Int, bookId: Int) {
        titleText.visibility = View.VISIBLE
        chapterText.visibility = View.VISIBLE
        verseText.visibility = View.VISIBLE

        titleText.text = title
        chapterText.text = chapterId.toString()
        verseText.text = verseId.toString()
        this.chapterId = chapterId
        this.verseId = verseId
        this.bookId = bookId


    }

    private fun initSavedData() {
        val highLightsList = SharedPrefUtils.getData(pref, SharedPrefUtils.HIGHLIGHT_MODEL)
        val favList = SharedPrefUtils.getData(pref, SharedPrefUtils.FAVORITE_MODEL)
        val noteList = SharedPrefUtils.getData(pref, SharedPrefUtils.NOTE_MODEL)
        highLightsList?.let {
            val collectionType = object : TypeToken<ArrayList<HighlightModelEntry>>() {}.type
            val data =
                    Gson().fromJson(highLightsList, collectionType) as ArrayList<HighlightModelEntry>
            viewModel.insertHighlights(data)
        }
        favList?.let {
            val collectionType = object : TypeToken<ArrayList<FavoriteModelEntry>>() {}.type
            val data = Gson().fromJson(favList, collectionType) as ArrayList<FavoriteModelEntry>
            viewModel.insertFavorites(data)
        }
        noteList?.let {
            val collectionType = object : TypeToken<ArrayList<NoteModelEntry>>() {}.type
            val data = Gson().fromJson(noteList, collectionType) as ArrayList<NoteModelEntry>
            viewModel.insertNotes(data)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.PAGE_INDEX_REQUEST_CODE) {
            if (data != null) {
                val bookId = data.getIntExtra("BookId", 0)
                val chapterId = data.getIntExtra("ChapterId", 0)
                val verseId = data.getIntExtra("VerseId", 0)
                val fragment = main_nav_host?.childFragmentManager?.primaryNavigationFragment
                if (fragment is HomeFragment) {
                    fragment.updateValue(bookId, chapterId, verseId)
                }
            }
        } else if (requestCode == AppConstants.UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("UPDATE_STATUS", "Update flow failed! Result code: $resultCode");
            }
        }
    }

    private fun drawerNavController() {
        darkModeSwitch.isChecked = SharedPrefUtils.isDayOrNight(pref)

        navShare.setOnMenuItemClickListener {
            closeDrawer()
            Utils.shareApp(this)
            true
        }
        navFeedback.setOnMenuItemClickListener {
            closeDrawer()
            startActivity(Intent(this, FeedbackActivity::class.java))
            true
        }
        navLanguage.setOnMenuItemClickListener {
            closeDrawer()
            viewModel.languageChange(this, pref)
            true
        }
    }


    private fun closeDrawer() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    private fun setDarkModeSwitchListener() {
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            SharedPrefUtils.setDayOrNight(pref, isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setThemeView() {
        when (val fragment = main_nav_host?.childFragmentManager?.primaryNavigationFragment) {
            is HomeFragment -> {
                fragment.updateTheme()
            }
            /*is LyricsFragment -> {
                isSearch = false
                fragment.updateSearch("")
            }*/
            else -> {
                super.onBackPressed()
            }
        }


    }

}
