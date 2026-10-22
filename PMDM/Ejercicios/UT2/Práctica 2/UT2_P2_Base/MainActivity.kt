package com.example.visordam

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.visordam.ui.theme.VisorDamTheme

// VisorDam · UT2 Práctica 2 · Aplicación de partida (no modificar este comentario)

private const val TAG = "VisorMain"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()
        setContent {
            VisorDamTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaPrincipal(
                        onVerDetalle = { abrirDetalle() },
                        onAbrirWeb = { abrirWebCentro() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun abrirDetalle() {
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra(EXTRA_MODULO, getString(R.string.modulo_pmdm))
        startActivity(intent)
    }

    private fun abrirWebCentro() {
        val web = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fpsantacatalina.com"))
        startActivity(web)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}

@Composable
fun PantallaPrincipal(
    onVerDetalle: () -> Unit,
    onAbrirWeb: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.titulo_principal),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = stringResource(R.string.descripcion_principal))
        Button(onClick = onVerDetalle) {
            Text(text = stringResource(R.string.boton_detalle))
        }
        Button(onClick = onAbrirWeb) {
            Text(text = stringResource(R.string.boton_web))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    VisorDamTheme {
        PantallaPrincipal(onVerDetalle = {}, onAbrirWeb = {})
    }
}
