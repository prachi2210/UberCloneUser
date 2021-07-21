package com.example.adebuser.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.adebuser.R
import com.example.adebuser.base.MyApplication
import com.example.adebuser.ui.home.HomeScreenActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "PRACHI"
    var msg: String? = null
    var title: String? = null
    var message: String? = null
    var userRef: String? = null
    var type: String? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: ${remoteMessage.data}")
        Log.d(TAG, "onMessageReceived: ${remoteMessage.data["driverRating"]}")
        Log.d(TAG, "onMessageReceived: ${remoteMessage.data["notifyType"]}")
        // sendNotification(remoteMessage.getFrom(), remoteMessage.getFrom());
        if (remoteMessage.data.isNotEmpty()) {


            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            type = remoteMessage.data["notifyType"]



            if (MyApplication.active) {
                val intent = Intent("Message")
                intent.putExtra("type", type)
                intent.putExtra("driverRef", remoteMessage.data["driverRef"])
                intent.putExtra("driverPhoto", remoteMessage.data["driverProfilePic"])
                intent.putExtra("driverName", remoteMessage.data["driverName"])
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                return
            }

            Log.e(TAG, "Message data payload: " + remoteMessage.data)
            sendNotification(
                title,
                body
            )

        }
        try {
            val pm =
                applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
            val isScreenOn =
                pm.isInteractive // check if screen is on
            if (!isScreenOn) {
                val wl = pm.newWakeLock(
                    PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                    "myApp:notificationLock"
                )
                wl.acquire(10000) //set your time in milliseconds
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*Token Refresh*/
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    /*Send Notification*/
    private fun sendNotification(title: String?, body: String?) {
        val pendingIntent: PendingIntent
        var intent: Intent? = null


        intent = Intent(this, HomeScreenActivity::class.java)
        intent.putExtra("type", type)
        intent.action = System.currentTimeMillis().toString()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)


        val random = Random()
        pendingIntent = PendingIntent.getActivity(this, random.nextInt(10), intent, 0)
        val channelId = getString(R.string.channel_id)
        var notification: Uri? = null
        try {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(getNotificationIcon())
                .setColor(resources.getColor(R.color.dark_blue))
                .setContentTitle(title)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(body)
                )
                .setVibrate(longArrayOf(500, 1000))
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_ALL)
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                    getApplicationContext().getPackageName() + "/" + R.raw.notification);
            mChannel = NotificationChannel(
                channelId,
                "KickTraders Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.lightColor = Color.BLUE
            mChannel.enableLights(true)
            mChannel.setShowBadge(true)
            mChannel.vibrationPattern = longArrayOf(500, 1000)
            mChannel.enableVibration(true)

            // Allow lockscreen playback control
            mChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            //            mChannel.setSound(soundUri, audioAttributes);
            notificationManager?.createNotificationChannel(mChannel)
        }
        assert(notificationManager != null)
        val t = Random()
        val notificationId = t.nextInt(10)
        notificationManager.notify(1, notificationBuilder.build())
    }

    /*Notification Icon*/
    private fun getNotificationIcon(): Int {
        val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (useWhiteIcon) R.drawable.ic_logo else R.drawable.ic_logo
    }

}