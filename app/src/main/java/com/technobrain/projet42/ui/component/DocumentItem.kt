package com.technobrain.projet42.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.R
import com.technobrain.projet42.domain.model.DocumentShort
import com.technobrain.projet42.ui.document.DocumentViewModel

@Composable
fun DocumentItem(document: DocumentShort, viewModel: DocumentViewModel, onDocumentReload: () -> Unit) {

    Card(
        modifier = Modifier
            .height(65.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .padding(start = 5.dp, end = 5.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_close_24),
                contentDescription = document.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .clickable {
                        viewModel.deleteDocument(document.id)
                        onDocumentReload()
                    },
                colorFilter = ColorFilter.tint(Color.Red)
            )
        }
    }
}

