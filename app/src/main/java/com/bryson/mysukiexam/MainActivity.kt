package com.bryson.mysukiexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bryson.mysukiexam.AppNavHost
import com.bryson.mysukiexam.ui.theme.MySukiExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MySukiExamTheme {
                AppNavHost()
            }
        }
    }
}
