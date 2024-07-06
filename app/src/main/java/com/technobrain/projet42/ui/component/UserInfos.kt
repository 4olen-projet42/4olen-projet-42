package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.domain.model.UserShort

@Composable
fun UserInfo(
    userShort: UserShort,
    statShort: StatShort,
    modifier: Modifier = Modifier,

    ){
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier.size(10.dp))
            Text(
                text = "Email : ",
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
            )
            Text(
                text = userShort.mail,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        HorizontalDivider(
            thickness = 10.dp,
            color = Color.Transparent
        )
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = modifier.size(10.dp))
            Text(
                text = "Distance totale : ",
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
            )
            Text(
                text = statShort.distanceTotale.toString() + " km",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        HorizontalDivider(
            thickness = 10.dp,
            color = Color.Transparent
        )
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = modifier.size(10.dp))
            Text(
                text = "Temps total : ",
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
            )
            Text(
                text = statShort.tempsTotal.toString() + " h",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        HorizontalDivider(
            thickness = 10.dp,
            color = Color.Transparent
        )
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = modifier.size(10.dp))
            Text(
                text = "Nombre de participation : ",
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
            )
            Text(
                text = statShort.nbParticipation.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        HorizontalDivider(
            thickness = 10.dp,
            color = Color.Transparent
        )
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = modifier.size(10.dp))
            Text(
                text = "Classement moyen : ",
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
            )
            Text(
                text = statShort.classementMoyen.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    MaterialTheme {
        val user = UserShort(
            nom = "Nom",
            prenom = "Prenom",
            mail = "mail@mail.com",
            photoUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
        )
        val stat = StatShort(
            distanceTotale = 100.0,
            tempsTotal = 10.0,
            nbParticipation = 5,
            classementMoyen = 3.0
        )
        UserInfo(user, stat)
    }
}