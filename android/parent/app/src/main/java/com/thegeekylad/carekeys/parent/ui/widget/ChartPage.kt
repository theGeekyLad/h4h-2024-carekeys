package com.thegeekylad.carekeys.parent.ui.widget

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.thegeekylad.carekeys.parent.chart.rememberMarker
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

enum class ChartType {
    BAR, LINE
}

@ExperimentalMaterial3Api
@Composable
fun ChartPage(
    title: String,
    chartType: ChartType,
    chartEntryModel: ChartEntryModel,
    viewModel: AppViewModel
) {

    val composableScope = rememberCoroutineScope()

//    LaunchedEffect(viewModel.mustRefresh.value) {
//        composableScope.launch(Dispatchers.IO) {
//            execApiCalls()
//        }
//    }

    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Chart(
            modifier = Modifier.fillMaxWidth(),
            chart =
                when(chartType) {
                    ChartType.BAR -> columnChart()
                    ChartType.LINE -> lineChart()
                },
            model = chartEntryModel,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(),
            marker = rememberMarker(),
        )
    }
}
