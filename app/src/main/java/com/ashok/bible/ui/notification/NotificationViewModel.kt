package com.ashok.bible.ui.notification

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.model.BaseModel
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.model.NotificationMsgModel
import com.ashok.bible.data.remote.repositary.AppRepository
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject


class NotificationViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val pref: SharedPreferences,
    private val dbRepository: DbRepository
) : BaseViewModel() {

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val createPost: MutableLiveData<BaseModel> by lazy { MutableLiveData<BaseModel>() }


    init {

    }

    fun pushNotifications(notificationMsg: NotificationMsgModel) {
        appRepository.sendNotificationMsg(notificationMsg, {
            createPost.value = it

        }, {
            error.value = it
        }).also { compositeDisposable.add(it) }
    }


}
