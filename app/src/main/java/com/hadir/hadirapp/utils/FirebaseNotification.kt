package com.hadir.hadirapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hadir.hadirapp.MainActivity

class FirebaseNotification : FirebaseMessagingService () {

    lateinit var  notificationChannel: NotificationChannel
    private val channelId = "com.hadir.hadirapp"
    private val description = "Reminder Scan"

    lateinit var notificationManager : NotificationManager


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Notifikasi(p0.notification?.body.toString(),p0.notification?.title.toString())

    }


    public fun Notifikasi(message : String, title : String){

        var intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        //Ini Notification buat Android O ke atas (Pake Notification Channel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)


            var notifBuilder = NotificationCompat.Builder(this,channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0,notifBuilder.build())

        } else{

            //Versi dibawah O
            //Ini Builder buat Notifnya
            var notifBuilder = NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0,notifBuilder.build())
        }


    }


}