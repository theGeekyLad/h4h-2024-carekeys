package com.thegeekylad.carekeys.parent.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.thegeekylad.carekeys.parent.ui.widget.ChartPage
import com.thegeekylad.carekeys.parent.ui.widget.ChartType
import com.thegeekylad.carekeys.parent.ui.widget.HappinessWidget
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: AppViewModel) {

    val applicationContext = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.serviceWrapper) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.barGraph(applicationContext)
        }
    }

    val mentalHealthQuotientChartEntryModel by remember {
        mutableStateOf(
            entryModelOf(
                entriesOf(50, 80, 80, 100, 60, 70, 80),
            )
        )
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HappinessWidget(score = viewModel.latestEQAverage.value ?: 100f)
        }

        item {
            Divider()
        }

        item {
            ChartPage(
                title = "Daily Category Rundown",
                viewModel = viewModel,
                chartType = ChartType.BAR,
                chartEntryModel = viewModel.dailyCategoryChartEntryModel.value ?: entryModelOf(emptyList())
            )
        }

        item {
            ChartPage(
                title = "Mental Health Quotient",
                viewModel = viewModel,
                chartType = ChartType.LINE,
                chartEntryModel = viewModel.eqChartEntryModel.value ?: entryModelOf(emptyList())
            )
        }
    }
}