package com.cifpsantacatalina.miprimeraapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cifpsantacatalina.miprimeraapp.ui.theme.MiPrimeraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPrimeraAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Android")
                        InfoDispositivo()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var contador by remember { mutableStateOf(0) }
    Column(modifier = modifier) {
        Text(text = "Hola, $name!")
        Button(onClick = { contador++ }) {
            Text(text = "Pulsado $contador veces")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiPrimeraAppTheme {
        Greeting("Android")
    }
}

@Composable
fun InfoDispositivo(modifier: Modifier = Modifier) {
    val configuracion = LocalConfiguration.current
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Ancho de la ventana: ${configuracion.screenWidthDp} dp")
        Text(text = "Alto de la ventana: ${configuracion.screenHeightDp} dp")
        Text(text = "Densidad: ${configuracion.densityDpi} dpi")
        Text(text = "Nivel de API: ${Build.VERSION.SDK_INT}")
        Text(text = "ABI principal: ${Build.SUPPORTED_ABIS.first()}")
    }
}