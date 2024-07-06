package com.technobrain.projet42.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.R

@Composable
fun SearchBar(onSearchClick: (text: String) -> Unit = {}) {

    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1.0f)
                .padding(end = 4.dp),
            shape = RoundedCornerShape(14.dp),
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Recherche") }
        )
        Surface(
            modifier = Modifier
                .size(50.dp)
                .clickable { onSearchClick(text) },
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFF007BFF)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color(0xFF1B85F3))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Filter",
                    modifier = Modifier.size(24.dp).align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar()
}