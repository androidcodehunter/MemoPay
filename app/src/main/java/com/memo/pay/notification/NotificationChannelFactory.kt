package com.memo.pay.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.memo.pay.R
/*TODO notification factory builder class to build all types of notification*/
@RequiresApi(Build.VERSION_CODES.O)
class NotificationChannelFactory constructor(val context: Context) {

    private val notificationChannels: MutableList<NotificationChannel> = arrayListOf()

    init {
        init()
    }

    fun createChannel(channelInfo: NotificationChannelInfo): NotificationChannel =
        NotificationChannel(channelInfo.id, channelInfo.name, channelInfo.priority).apply {
            description = channelInfo.description
        }

    private fun init() {
        val generalChannel = createChannel(
            NotificationChannelInfo(
                context.getString(R.string.notifications_general_channel_id),
                context.getString(R.string.notifications_general_channel),
                context.getString(R.string.notifications_general_channel_description),
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )
        notificationChannels.add(generalChannel)
    }

    fun getDefaultChannel(): NotificationChannel = notificationChannels[0]

    data class NotificationChannelInfo(
        val id: String,
        val name: String,
        val description: String,
        val priority: Int
    )
}