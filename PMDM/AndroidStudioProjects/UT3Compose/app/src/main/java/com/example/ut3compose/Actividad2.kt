package com.example.ut3compose
import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ut3compose.ui.theme.UT3ComposeTheme

@Composable
fun Dibujar() {
    Column() {
        Box(Modifier.background(Color.Yellow).padding(24.dp).size(48.dp).background(Color.Red))
        Spacer(Modifier.height(16.dp))
        Box(Modifier.padding(24.dp).background(Color.Yellow).size(48.dp))
        Spacer(Modifier.height(16.dp))
        Text("Hola", Modifier.border(2.dp, Color.Black).padding(16.dp).border(2.dp, Color.Red))
        Spacer(Modifier.height(16.dp))
        Text("Pulsa", Modifier.padding(24.dp).clickable { }.background(Color.Cyan))
    }
}

@Composable
fun Etiqueta(texto: String, destacada: Boolean, modifier: Modifier) {
    val forma = RoundedCornerShape(50)
    val colores = MaterialTheme.colorScheme
    val modifier = if (!destacada) {
        Modifier.clip(forma).background(colores.primaryContainer)
    } else {
        Modifier
    }
}

@Preview(showBackground = true)
@Composable
fun DibujarPreview() {
    UT3ComposeTheme {
        Dibujar()
    }
}




