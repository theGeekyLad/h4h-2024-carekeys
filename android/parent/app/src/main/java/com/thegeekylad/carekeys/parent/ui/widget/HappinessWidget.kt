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
fun HappinessWidget(score: Float) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Faizan's Stats",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Image(
            painter = painterResource(
                id =
                    if (score >= 0 && score < 20)
                        R.drawable.depressed
                    else if (score > 20 && score <= 40)
                        R.drawable.sad
                    else if (score > 40 && score <= 60)
                        R.drawable.neutral
                    else if (score >= 60 && score < 80)
                        R.drawable.smile
                    else
                        R.drawable.bliss
            ),
            contentDescription = null
        )
    }
}