package com.thegeekylad.carekeys.parent.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.thegeekylad.carekeys.parent.ui.widget.ChartPage
import com.thegeekylad.carekeys.parent.ui.widget.ChartType
import com.thegeekylad.carekeys.parent.ui.widget.HappinessWidget
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: AppViewModel) {
    val dailyCategoryChartEntryModel by remember {
        mutableStateOf(
            entryModelOf(
                entriesOf(5, 1),
                entriesOf(1, 1),
                entriesOf(4, 1),
                entriesOf(0, 3),
            )
        )
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
            HappinessWidget()
        }

        item {
            Divider()
        }

        item {
            ChartPage(
                title = "Daily Category Rundown",
                viewModel = viewModel,
                chartType = ChartType.BAR,
                chartEntryModel = dailyCategoryChartEntryModel
            )
        }

        item {
            ChartPage(
                title = "Mental Health Quotient",
                viewModel = viewModel,
                chartType = ChartType.LINE,
                chartEntryModel = mentalHealthQuotientChartEntryModel
            )
        }
    }
}