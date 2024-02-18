package com.thegeekylad.carekeys.parent.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thegeekylad.carekeys.parent.R

@Composable
fun HappinessWidget() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Rahul's Stats",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Image(painter = painterResource(id = R.drawable.bliss), contentDescription = null)
    }
}