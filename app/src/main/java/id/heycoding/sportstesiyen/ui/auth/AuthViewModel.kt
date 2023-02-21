package id.heycoding.sportstesiyen.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _authUiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authUiState: StateFlow<AuthUiState> = _authUiState

    private var auth: FirebaseAuth = Firebase.auth

    fun doCheckingUser() = viewModelScope.launch {
        val user = auth.currentUser
        if (user != null) {
            _authUiState.value = AuthUiState.ValidateUser(user.displayName.toString())
        }
    }

    fun doLogin(email: String, password: String) {
        _authUiState.value = AuthUiState.Loading
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { taskLogin ->
            if (taskLogin.isSuccessful && taskLogin.result != null) {
                val user = taskLogin.result.user
                if (user != null) {
                    _authUiState.value = AuthUiState.Success(user.displayName.toString())
                } else {
                    _authUiState.value =
                        AuthUiState.Error(taskLogin.exception?.localizedMessage.toString())
                }
            } else {
                _authUiState.value =
                    AuthUiState.Error(taskLogin.exception?.localizedMessage.toString())
            }
        }
    }

    fun doRegister(username: String, email: String, password: String) {
        _authUiState.value = AuthUiState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { taskRegister ->
                if (taskRegister.isSuccessful && taskRegister.result != null) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    if (user != null) {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }
                        user.updateProfile(profileUpdates).addOnCompleteListener { taskRegister ->
                            if (taskRegister.isSuccessful) {
                                _authUiState.value = AuthUiState.Success(username)
                            }
                        }
                    } else {
                        _authUiState.value =
                            AuthUiState.Error(taskRegister.exception?.localizedMessage.toString())
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    _authUiState.value =
                        AuthUiState.Error(taskRegister.exception?.localizedMessage.toString())
                }
            }
    }
}