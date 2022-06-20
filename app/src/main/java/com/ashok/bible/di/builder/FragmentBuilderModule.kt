package com.ashok.bible.di.builder

import com.ashok.bible.ui.One
import com.ashok.bible.ui.Two
import com.ashok.bible.ui.bottomsheet.BottomSheetFragment
import com.ashok.bible.ui.favorite.FavoriteFragment
import com.ashok.bible.ui.highlights.HighlightsFragment
import com.ashok.bible.ui.home.HomeFragment
import com.ashok.bible.ui.lyrics.LyricsFirstLangFragment
import com.ashok.bible.ui.lyrics.LyricsFragment
import com.ashok.bible.ui.lyrics.LyricsSecondLangFragment
import com.ashok.bible.ui.notes.NotesFragment
import com.ashok.bible.ui.quotes.QuotesFragment
import com.ashok.bible.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun highlightsFragment(): HighlightsFragment

    @ContributesAndroidInjector
    internal abstract fun favoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    internal abstract fun notesFragment(): NotesFragment

    @ContributesAndroidInjector
    internal abstract fun dashHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun settingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    internal abstract fun lyricsFragment(): LyricsFragment

    @ContributesAndroidInjector
    internal abstract fun lyricsFirstLangFragment(): LyricsFirstLangFragment

    @ContributesAndroidInjector
    internal abstract fun lyricsSecondLangFragment(): LyricsSecondLangFragment

    @ContributesAndroidInjector
    internal abstract fun quotesFragment(): QuotesFragment

    @ContributesAndroidInjector
    internal abstract fun one(): One

    @ContributesAndroidInjector
    internal abstract fun two(): Two

    @ContributesAndroidInjector
    internal abstract fun mBottomSheetFragment(): BottomSheetFragment



}