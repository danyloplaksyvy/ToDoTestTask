package pro.danyloplaksyvyi.todotesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pro.danyloplaksyvyi.todotesttask.core.navigation.presentation.RootNavigationGraph
import pro.danyloplaksyvyi.todotesttask.ui.theme.ToDoTestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTestTaskTheme {
                RootNavigationGraph()
            }
        }
    }
}