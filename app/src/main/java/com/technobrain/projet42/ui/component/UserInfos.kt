package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.domain.model.UserShort

@Composable
fun UserInfo(
    userShort: UserShort,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Card(
            modifier = modifier
                .height(75.dp)
                .clip(RoundedCornerShape(10.dp)),
            content = {
                Row(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = modifier.size(10.dp))
                    Text(
                        text = "Email : " + userShort.mail,
                        modifier = modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        )
        Spacer(modifier.size(10.dp))
        Card(
            modifier = modifier
                .height(75.dp)
                .clip(RoundedCornerShape(10.dp)),
            content = {
                Row(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = modifier.size(10.dp))
                    Text(
                        text = "Distance totale : 20km",
                        modifier = modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        )
        Spacer(modifier.size(10.dp))
        Card(
            modifier = modifier
                .height(75.dp)
                .clip(RoundedCornerShape(10.dp)),
            content = {
                Row(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = modifier.size(10.dp))
                    Text(
                        text = "Temps moyen : 1h24min",
                        modifier = modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        )
        Spacer(modifier.size(10.dp))
        Card(
            modifier = modifier
                .height(75.dp)
                .clip(RoundedCornerShape(10.dp)),
            content = {
                Row(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = modifier.size(10.dp))
                    Text(
                        text = "Nombre de participation : 6",
                        modifier = modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        )
        Spacer(modifier.size(10.dp))
        Card(
            modifier = modifier
                .height(75.dp)
                .clip(RoundedCornerShape(10.dp)),
            content = {
                Row(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = modifier.size(10.dp))
                    Text(
                        text = "Classement moyen : 35e",
                        modifier = modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    MaterialTheme {
        val user = UserShort(
            id = "1",
            nom = "Nom",
            prenom = "Prenom",
            mail = "mail@mail.com",
            photoUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
        )
        UserInfo(user)
    }
}