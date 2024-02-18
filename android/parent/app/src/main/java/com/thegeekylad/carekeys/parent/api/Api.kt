package com.thegeekylad.carekeys.parent.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import com.thegeekylad.carekeys.parent.R
import com.thegeekylad.carekeys.parent.model.ResponseBarGraph
import com.thegeekylad.carekeys.parent.util.Constants
import com.thegeekylad.carekeys.parent.util.Constants.Companion.TAG
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel
import io.javalin.Javalin

class Api(
    private val app: Javalin,
    private val viewModel: AppViewModel,
    private val context: Context
) {

    fun onText() {
        app.post("/text") {
            Log.d(TAG, "Heard from backend!")
            val res = ResponseBarGraph.parse(it.body())
            val resArr: Array<Array<String>> = Array<Array<String>>(res.size) { i ->
                Array<String>(res[i].size) { j ->
                    res[i][j]
                }
            }

            val responseBarGraph = ResponseBarGraph(resArr)

            viewModel.dailyCategoryChartEntryModel.value = viewModel.getDailyModelFromResponse(responseBarGraph)
            viewModel.eqChartEntryModel.value =viewModel.getEQModelFromResponse(responseBarGraph)
            viewModel.latestEQAverage.value = viewModel.getLatestEQAverage(responseBarGraph)

            Log.d(TAG, viewModel.latestEQAverage.value.toString())

            if (viewModel.latestEQAverage.value!! < Constants.THRESHOLD_NOTIFY)
                issueNotification()
        }
    }

    private fun issueNotification() {
        val channel = NotificationChannel(
            "121",
            "Parental Alerts",
            NotificationManager.IMPORTANCE_HIGH)

        val notificationService = context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationService.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, "121")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Parental Control Alerts")
            .setContentText("There has been some unusual report regarding your child's mental health")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationService.notify(121, builder.build())
    }
}