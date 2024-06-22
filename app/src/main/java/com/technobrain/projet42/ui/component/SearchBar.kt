package com.technobrain.projet42.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = { /* Handle text change */ },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(end = 8.dp),
            label = { Text("Search") },
            placeholder = { Text("Search...") },
            shape = RoundedCornerShape(16.dp)
        )
        IconButton(onClick = { /* Handle filter click */ }) {
            Icon(Icons.Filled.List, contentDescription = "Filter")
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar()
}