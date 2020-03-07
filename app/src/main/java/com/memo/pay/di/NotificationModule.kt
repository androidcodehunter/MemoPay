package com.memo.pay.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.memo.pay.notification.NotificationChannelFactory
import com.memo.pay.notification.NotificationFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val notificationModule = module {
    single { provideNotificationFactory(androidContext()) }
    single { provideNotificationChannel(androidContext()) }
}

fun provideNotificationFactory(context: Context): NotificationFactory {
    return NotificationFactory(context)
}

@RequiresApi(Build.VERSION_CODES.O)
fun provideNotificationChannel(context: Context): NotificationChannelFactory {
    return NotificationChannelFactory(context)
}
