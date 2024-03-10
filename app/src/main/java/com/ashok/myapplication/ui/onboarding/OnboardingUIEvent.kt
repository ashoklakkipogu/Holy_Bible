package com.ashok.myapplication.ui.onboarding

import android.content.Context
import com.ashok.myapplication.data.local.entity.UserModel

sealed class OnboardingUIEvent {
     data class InsertBible(val userName:String? =null, val langauge: String):OnboardingUIEvent()
     object OnEventIsFirstTime:OnboardingUIEvent()
}