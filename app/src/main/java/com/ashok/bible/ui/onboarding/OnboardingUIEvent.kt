package com.ashok.bible.ui.onboarding

sealed class OnboardingUIEvent {
     data class InsertBible(val langauge: String):OnboardingUIEvent()
     object OnEventIsFirstTime:OnboardingUIEvent()
}