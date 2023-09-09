package com.example.earzikimarket.ui.view.pages.login

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reciperoulette.data.model.supabaseAdapter.SupabaseAdapter
import com.example.earzikimarket.ui.viewmodel.LoginViewModel
import com.example.reciperoulette.data.util.NavigationRoute
import io.github.jan.supabase.gotrue.providers.builtin.Email


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()


    var email by remember { mutableStateOf("mkottosen@gmail.com") }
    var password by remember { mutableStateOf("Marcus123") }
    var confirmPassword by remember { mutableStateOf("Marcus123") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = { /* Handle Done action if needed */ })
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = { /* Handle Done action if needed */ })
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword) {
                    viewModel.signUp(email, password, navController)
                } else {
                    errorMessage = "Passwords do not match"
                }
            }
        ) {
            Text("Sign Up")
        }

        TextButton(
            onClick = {
                navController.navigate(NavigationRoute.Login.route)
            }
        ) {
            Text("Already a member? Login!")
        }

        // Observe the sign-up state using the view model
        val signUpState by viewModel.signUpState.collectAsState()
        when (signUpState) {
            is LoginViewModel.SignUpState.Loading -> {
                CircularProgressIndicator()
            }
            is LoginViewModel.SignUpState.Success -> {
                val email = (signUpState as LoginViewModel.SignUpState.Success).email
                Text("Sign-up successful! Email: $email")
                navController.navigate(NavigationRoute.Homepage.route)
                viewModel.resetLoginState()
            }
            is LoginViewModel.SignUpState.Error -> {
                val errorMessage = (signUpState as LoginViewModel.SignUpState.Error).message
                Text("Sign-up error: $errorMessage")
                // You can display the error message or handle the error here
            }
            else -> {
                // Show the sign-up form when the state is not loading, success, or error
            }
        }
    }
}

@Composable
fun signUpUser(email: String, password: String, supabaseAdapter: SupabaseAdapter, context: Context) {
    LaunchedEffect(Unit) {
        try {
            val user = supabaseAdapter.gotrue.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        } catch (e: Exception) {
        }
    }
}
