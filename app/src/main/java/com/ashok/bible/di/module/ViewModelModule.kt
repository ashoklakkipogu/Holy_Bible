package com.ashok.bible.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashok.bible.ui.MainViewModel
import com.ashok.bible.ui.admin.AdminViewModel
import com.ashok.bible.ui.base.ViewModelFactory
import com.ashok.bible.ui.bibleindex.BibleIndexActivity
import com.ashok.bible.ui.bibleindex.BibleIndexViewModel
import com.ashok.bible.ui.details.DetailsViewModel
import com.ashok.bible.ui.favorite.FavoriteViewModel
import com.ashok.bible.ui.highlights.HighlightsViewModel
import com.ashok.bible.ui.home.HomeViewModel
import com.ashok.bible.ui.lyrics.LyricsViewModel
import com.ashok.bible.ui.notes.NotesViewModel
import com.ashok.bible.ui.notification.NotificationViewModel
import com.ashok.bible.ui.quotes.QuotesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BibleIndexViewModel::class)
    abstract fun bindBibleIndexViewModel(bibleIndexViewModel: BibleIndexViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(favoriteViewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HighlightsViewModel::class)
    abstract fun bindHighlightsViewModel(highlightsViewModel: HighlightsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    abstract fun bindNotesViewModel(notesViewModel: NotesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(notificationViewModel: NotificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LyricsViewModel::class)
    abstract fun bindLyricsViewModel(lyricsViewModel: LyricsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuotesViewModel::class)
    abstract fun bindQuotesViewModel(quotesViewModel: QuotesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel::class)
    abstract fun bindAdminViewModel(adminViewModel: AdminViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}