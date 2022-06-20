package com.ashok.bible.ui.model

import com.ashok.bible.common.AppConstants
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.time.hours

class NotificationMsgModel {
    @SerializedName("to") var to:String = "/topics/${AppConstants.SUBSCRIBE_TO_TOPIC}"
    @SerializedName("priority") var priority:String = "high"
    @SerializedName("notification") var notification: Notification = Notification()
    @SerializedName("data") var data: Data = Data()


    class Notification {
        @SerializedName("title") var title:String = ""
        @SerializedName("body") var body:String = ""
        @SerializedName("badge") var badge:String = "1"
        @SerializedName("click_action") var clickAction:String = "OPEN_ACTIVITY_NOTIFICATION"
        @SerializedName("icon") var icon:String = "ic_notification"

    }
    class Data {
        @SerializedName("picture") var picture:String = ""
        @SerializedName("notification") var notification:String = "yes"

    }
}