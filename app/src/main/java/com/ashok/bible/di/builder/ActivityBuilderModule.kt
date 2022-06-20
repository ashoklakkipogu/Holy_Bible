package com.ashok.bible.di.builder

import com.ashok.bible.ui.MainActivity
import com.ashok.bible.ui.SplashActivity
import com.ashok.bible.ui.admin.BannerPosActivity
import com.ashok.bible.ui.admin.QuotesPosActivity
import com.ashok.bible.ui.bibleindex.BibleIndexActivity
import com.ashok.bible.ui.details.DetailsActivity
import com.ashok.bible.ui.feedback.FeedbackActivity
import com.ashok.bible.ui.lyrics.LyricDetailsActivity
import com.ashok.bible.ui.lyrics.LyricExpandedActivity
import com.ashok.bible.ui.lyrics.LyricPostActivity
import com.ashok.bible.ui.lyrics.LyricsCommentsActivity
import com.ashok.bible.ui.notification.NotificationPostActivity
import com.ashok.bible.ui.quotes.QuotesDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun bibleIndexActivity(): BibleIndexActivity

    @ContributesAndroidInjector()
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun feedbackActivity(): FeedbackActivity

    @ContributesAndroidInjector()
    abstract fun notificationPostActivity(): NotificationPostActivity

    @ContributesAndroidInjector()
    abstract fun lyricDetailsActivity(): LyricDetailsActivity

    @ContributesAndroidInjector()
    abstract fun lyricsCommentsActivity(): LyricsCommentsActivity

    @ContributesAndroidInjector()
    abstract fun lyricPostActivity(): LyricPostActivity

    @ContributesAndroidInjector()
    abstract fun quotesPosActivity(): QuotesPosActivity

    @ContributesAndroidInjector()
    abstract fun bannerPosActivity(): BannerPosActivity


    @ContributesAndroidInjector()
    abstract fun detailsActivity(): DetailsActivity

    @ContributesAndroidInjector()
    abstract fun quotesDetailsActivity(): QuotesDetailsActivity

    @ContributesAndroidInjector()
    abstract fun lyricExpandedActivity(): LyricExpandedActivity


}