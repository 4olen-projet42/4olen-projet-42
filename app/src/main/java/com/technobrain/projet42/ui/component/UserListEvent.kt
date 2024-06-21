package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun UserListEvent(
    events: List<EventShort>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = modifier,
            state = listState
        ) {
            itemsIndexed(events) { _, item ->
                UserEvent(
                    eventShort = item
                )
                HorizontalDivider(
                    thickness = 10.dp,
                    color = Color.Transparent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListEventPreview() {
    MaterialTheme {
        val events = listOf(
            EventShort(
                id = "1",
                name = "Evenement 1",
                date = "Lundi 01 janvier",
                location = "Lyon"
            ),
            EventShort(
                id = "2",
                name = "Evenement 2",
                date = "Lundi 02 janvier",
                location = "Lyon"
            ),
            EventShort(
                id = "3",
                name = "Evenement 3",
                date = "Lundi 03 janvier",
                location = "Lyon"
            )
        )
        UserListEvent(events)
    }
}