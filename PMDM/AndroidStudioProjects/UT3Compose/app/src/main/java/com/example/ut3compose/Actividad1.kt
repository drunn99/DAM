package com.example.ut3compose
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.ut3compose.ui.theme.UT3ComposeTheme

@Composable
    fun Presentacion(nombre: String, modulo: String, ciudad: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(text = "Estudia $modulo", style = MaterialTheme.typography.bodyLarge)
        if(ciudad.isNotEmpty()) {
            Text(text = "Vive en: $ciudad", color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        }
        Icon(
            painterResource(R.drawable.ic_estrella),
            contentDescription = "Estrella",
            tint = MaterialTheme.colorScheme.primary
        )
        Button(onClick = {Log.d("UT3","Hola desde $nombre")}) {
            Text("Saludar")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PresentacionPreview() {
    UT3ComposeTheme {
        Presentacion("Lucía Martín","PMDM","Aranda")
    }
}

@PreviewFontScale
@Preview (name = "Letra Grande", fontScale = 2f, showBackground = true)
@Composable
fun PresentacionPreviewFontScale() {
    UT3ComposeTheme {
        Presentacion("Lucía Martín","PMDM","Aranda")
    }
}

@PreviewLightDark
@Preview
@Composable
fun PresentacionPreviewLightDark() {
    UT3ComposeTheme {
        Surface {
            Presentacion("Lucía Martín","PMDM","Aranda")
        }
    }
}


