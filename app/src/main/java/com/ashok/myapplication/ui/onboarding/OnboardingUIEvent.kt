package com.ashok.myapplication.ui.onboarding

import android.content.Context

sealed class OnboardingUIEvent {
     data class InsertBible(val langauge: String):OnboardingUIEvent()
     object OnEventIsFirstTime:OnboardingUIEvent()
}