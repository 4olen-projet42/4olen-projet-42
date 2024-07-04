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
import androidx.navigation.NavHostController
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun UserListEvent(
    events: List<EventShort>,
    navController: NavHostController,
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
                EventItem(
                    event = item,
                    navController = navController
                )
                HorizontalDivider(
                    thickness = 10.dp,
                    color = Color.Transparent
                )
            }
        }
    }
}