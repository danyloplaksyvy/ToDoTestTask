package pro.danyloplaksyvyi.todotesttask.features.auth.presentation.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConnectWithGoogleButton(onSignInSuccess: () -> Unit) {
    var isLoading by remember { mutableStateOf(false) }

    // Google Sign In Button
    OutlinedButton(
        onClick = {
            isLoading = true
            // Handle Google sign in
            onSignInSuccess()
        },
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(
            text = buildAnnotatedString {
                append("Continue with ")
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF4285F4), // Google Blue
                        fontWeight = FontWeight.Bold
                    )
                ) { append("Google") }
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}