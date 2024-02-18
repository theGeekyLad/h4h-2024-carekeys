package com.thegeekylad.carekeys.parent.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thegeekylad.carekeys.parent.ui.widget.ChartPage
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel

@Composable
fun ProfileScreen(viewModel: AppViewModel) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hello Sunil!",
            style = MaterialTheme.typography.headlineLarge,
        )

        Column {
            Text(
                text = "Children",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(text = "- Rahul")
        }

        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Sign out")
            }
        }
    }
}