package com.technobrain.projet42.ui.component


import android.content.Context
import androidx.documentfile.provider.DocumentFile
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technobrain.projet42.ui.document.DocumentViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@Composable
fun DocumentForm(context: Context, viewModel: DocumentViewModel, onDocumentAdded: () -> Unit) {
    val documentPath = remember { mutableStateOf("") }
    val documentName = remember { mutableStateOf("") }

    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri!!)
        val tempFile = File(context.cacheDir, "tempfile_" + System.currentTimeMillis())

        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }

        documentPath.value = tempFile.path

        val documentFile = DocumentFile.fromSingleUri(context, uri)
        documentName.value = documentFile?.name ?: "Unknown"
        onDocumentAdded()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = {
                documentPickerLauncher.launch("application/*")
                      },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors( Color(0xFF1B85F3)),
        ){
            Text(text = "Ajouter un document")
        }

        if (documentPath.value.isNotEmpty()) {
            Text(
                text = "Le Document '${documentName.value}' à bien été ajouté",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            viewModel.uploadFile(documentPath.value, documentName.value)

        }
    }
}