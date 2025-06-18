package pro.danyloplaksyvyi.todotesttask.features.auth.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(onSignInSuccess: () -> Unit, onSignUpNavigate: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onSignInSuccess) {
            Text("Go to Task List Screen")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onSignUpNavigate) {
            Text("Go to Sign Up Screen")
        }
    }
}