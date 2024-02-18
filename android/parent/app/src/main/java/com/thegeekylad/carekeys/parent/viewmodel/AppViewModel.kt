package com.thegeekylad.carekeys.parent.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.thegeekylad.carekeys.parent.model.BarGraphDataPoint
import com.thegeekylad.carekeys.parent.model.ResponseBarGraph
import com.thegeekylad.carekeys.parent.service.ServiceWrapper
import com.thegeekylad.carekeys.parent.util.Constants.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel : ViewModel() {

    val serviceWrapper = ServiceWrapper()

    // state
    val dailyCategoryChartEntryModel: MutableState<ChartEntryModel?> = mutableStateOf(null)
    val eqChartEntryModel: MutableState<ChartEntryModel?> = mutableStateOf(null)
    val latestEQAverage: MutableState<Float?> = mutableStateOf(null)

    fun barGraph(context: Context) {
        serviceWrapper.barGraph(
            callback = object : Callback<ResponseBarGraph> {
                override fun onResponse(call: Call<ResponseBarGraph>, response: Response<ResponseBarGraph>) {
                    if (response.code() == 500)
                        Toast.makeText(
                            context,
                            "Something snapped.",
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        dailyCategoryChartEntryModel.value = getDailyModelFromResponse(response.body()!!)
                        eqChartEntryModel.value = getEQModelFromResponse(response.body()!!)
                        latestEQAverage.value = getLatestEQAverage(response.body()!!)
                        Log.d(TAG,  response.body()!!.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBarGraph>, t: Throwable) {}
            }
        )
    }

    fun getDailyModelFromResponse(response: ResponseBarGraph): ChartEntryModel =
        entryModelOf(
            entriesOf(
                *response.data.map {
                    it[1].toFloat()
                }.toTypedArray()
            ),

            entriesOf(
                *response.data.map {
                    it[2].toFloat()
                }.toTypedArray()
            ),

            entriesOf(
                *response.data.map {
                    it[3].toFloat()
                }.toTypedArray()
            ),

            entriesOf(
                *response.data.map {
                    it[4].toFloat()
                }.toTypedArray()
            ),
        )

    fun getEQModelFromResponse(response: ResponseBarGraph): ChartEntryModel =
        entryModelOf(
            entriesOf(
                *response.data.map {
                    it[BarGraphDataPoint.getIndex(BarGraphDataPoint.EQ)].toFloat()
                }.toTypedArray()
            )
        )

    fun getLatestEQAverage(response: ResponseBarGraph): Float =
        response.data[response.data.size - 1][BarGraphDataPoint.getIndex(BarGraphDataPoint.EQ)].toFloat()
}