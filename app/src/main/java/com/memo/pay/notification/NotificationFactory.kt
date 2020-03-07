package com.memo.pay.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.memo.pay.R

/*TODO Notification factory class to create all kinds of notification. {@link NotificationTemplate} using NotificationTemplate you can achieve all kinds of notification */
class NotificationFactory(val context: Context) {

    private fun createNotification(model: NotificationTemplate): Notification {
        return NotificationCompat.Builder(context, model.channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(model.title)
            .setContentText(model.content)
            .setPriority(model.priority)
            .setAutoCancel(true)
            .apply {
                when (model) {
                    is TextExpandableNotification -> {
                        setStyle(NotificationCompat.BigTextStyle().bigText(model.longText))
                    }
                    is PictureExpandableNotification -> {
                        val pictureBitmap =
                            BitmapFactory.decodeResource(context.resources, model.picture)
                        setLargeIcon(pictureBitmap)
                        setStyle(
                            NotificationCompat.BigPictureStyle()
                                .bigPicture(pictureBitmap)
                                .bigLargeIcon(null)
                                .setBigContentTitle(model.bigContentTitle)
                        )
                    }
                    is InboxNotification -> {
                        val inboxStyle = NotificationCompat.InboxStyle()

                        model.lines.forEach {
                            inboxStyle.addLine(it)
                        }

                        setStyle(inboxStyle)
                    }
                }

                model.actions.forEach { (iconId, title, actionIntent) ->
                    addAction(iconId, title, actionIntent)
                }
            }
            .build()
    }

    fun showNotification(notificationId: Int, notification: NotificationTemplate) {
        NotificationManagerCompat
            .from(context)
            .notify(notificationId, createNotification(notification))
    }

    fun showNotification(notificationId: Int, notification: NotificationTemplate, notificationChannel: NotificationChannel){
        val notificationManager =NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(notificationId, createNotification(notification))

    }
}