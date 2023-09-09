package com.example.earzikimarket.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.reciperoulette.data.model.supabaseAdapter.SupabaseAdapter
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    val supabaseAdapter = SupabaseAdapter()

    sealed class SignUpState {
        object Initial : SignUpState()
        object Loading : SignUpState()
        data class Success(val email: String) : SignUpState()
        data class Error(val message: String) : SignUpState()
    }

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Initial)
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun signUp(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading

            try {
                val user = supabaseAdapter.gotrue.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                _signUpState.value = SignUpState.Success(email)

            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Error signing up: ${e.message}")
                _signUpState.value = SignUpState.Error("Error signing up: ${e.message}")
            }
        }
    }

    fun resetLoginState() {
        _signUpState.value = SignUpState.Initial
        _loginState.value = LoginState.Initial
    }


    sealed class LoginState {
        object Initial : LoginState()
        object Loading : LoginState()
        data class Success(val email: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                supabaseAdapter.gotrue.loginWith(Email){
                    this.email = email
                    this.password = password
                }
                _loginState.value = LoginState.Success(email)

            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Error logging in: ${e.message}")
                _loginState.value = LoginState.Error("Error logging in: ${e.message}")
            }
        }
    }

}
