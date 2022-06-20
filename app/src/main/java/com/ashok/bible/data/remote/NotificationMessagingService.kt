package com.ashok.bible.data.remote

import com.ashok.bible.data.remote.model.BaseModel
import com.ashok.bible.ui.model.NotificationMsgModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationMessagingService {
    @POST("send")
    fun sendMessage(@Body notificationModel: NotificationMsgModel): Observable<BaseModel>
}