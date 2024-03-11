package com.ashok.bible.ui.onboarding

data class OnboardingUIState(
    val isLoading:Boolean = false,
    val isBibleInserted:Boolean = false,
    val isFirstTime:Boolean = false,
    val userName:String? = null
)
