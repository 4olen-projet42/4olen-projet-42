package com.technobrain.projet42.ui.document

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.technobrain.projet42.ui.component.DocumentForm
import com.technobrain.projet42.ui.component.DocumentItem

@Composable
fun DocumentScreen(
    context: Context
) {
    val viewModel: DocumentViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val refreshTrigger = remember { mutableStateOf(0) }

    LaunchedEffect(refreshTrigger.value) {
        viewModel.getDocuments()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {


        when (val state = uiState) {
            is DocumentState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is DocumentState.Loaded -> {
                items(state.documents) { document ->
                    DocumentItem(document, viewModel){
                        refreshTrigger.value++
                    }
                    HorizontalDivider(
                        thickness = 10.dp,
                        color = Color.Transparent
                    )
                }
            }
            is DocumentState.Error -> {
                Log.e("DocumentScreen", state.message)
            }
            is DocumentState.Empty -> {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pas de résultats"
                        )
                    }
                }
            }

        }

        item {
            DocumentForm(context, viewModel) {
                refreshTrigger.value++
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DocumentScreenPreview() {
    DocumentScreen(context = LocalContext.current)
}