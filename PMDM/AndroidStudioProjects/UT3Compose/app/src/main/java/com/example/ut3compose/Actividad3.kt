package com.example.ut3compose

import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

val logTag: String = "TarjetaContacto"

@Composable
fun TarjetaContacto (nombre: String, cargo: String, email: String, modifier: Modifier = Modifier) {
    Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FotoConIndicador()
                Spacer(Modifier.width(16.dp))
                Column(modifier.weight(1f)) {
                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = cargo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4F5FA8),
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_estrella),
                    contentDescription = "Favorito",
                    tint = Color(0xFFC9A400)
                )
            }
            HorizontalDivider(Modifier.height(5.dp))
            Row(
                modifier,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { Log.d(logTag,"LLAMAR A $nombre")}) { }
                TextButton(onClick = { Log.d(logTag,"MENSAJE A $nombre")}) { }
                TextButton(onClick = { Log.d(logTag,"EMAIL A $nombre")}) { }
            }
    }
}

@Composable
fun FotoConIndicador(modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(64.dp)) {
        Image(
            painterResource(R.drawable.foto_perfil),
            "fotoPerfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().clip(CircleShape))
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(16.dp)
                .border(2.dp,Color.White,CircleShape)
                .background(Color(0xFF43A047),CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaContactoPreview (modifier: Modifier = Modifier) {
    val nombre = "Daniel Núñez Uribe"
    val cargo = "Programador en ciernes"
    val email = "nunu@gmail.com"

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FotoConIndicador()
            Spacer(Modifier.width(16.dp))
            Column(modifier.weight(1f)) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = cargo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF4F5FA8),
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_estrella),
                contentDescription = "Favorito",
                tint = Color(0xFFC9A400)
            )
        }
        HorizontalDivider(Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { Log.d(logTag,"LLAMAR A $nombre")}) { Text("Llamar") }
            TextButton(onClick = { Log.d(logTag,"MENSAJE A $nombre")}) { Text("Mensaje") }
            TextButton(onClick = { Log.d(logTag,"EMAIL A $nombre")}) { Text("Email") }
        }
    }
}