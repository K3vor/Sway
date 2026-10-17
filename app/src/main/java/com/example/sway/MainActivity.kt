package com.example.sway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jniStatusMessage = try {
            stringFromJNI()
        } catch (e: UnsatisfiedLinkError) {
            "Native library not loaded: ${e.message}"
        }

        setContent {
            com.example.sway.ui.theme.SwayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = jniStatusMessage,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }

    private external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("sway_native")
        }
    }
}
