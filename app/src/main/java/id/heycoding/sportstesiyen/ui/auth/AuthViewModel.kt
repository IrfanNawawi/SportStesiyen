package id.heycoding.sportstesiyen.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class AuthViewModel : ViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isCheckingUser = MutableLiveData<Boolean>()
    val isCheckingUser: LiveData<Boolean> = _isCheckingUser

    private var auth: FirebaseAuth = Firebase.auth

    fun doCheckingUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _isCheckingUser.value = true
        }
    }

    fun doLogin(email: String, password: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { taskLogin ->
            if (taskLogin.isSuccessful && taskLogin.result != null) {
                _isLoading.value = false
                val user = taskLogin.result.user
                if (user != null) {
                    _isSuccess.value = true
                } else {
                    _isError.value = taskLogin.exception?.localizedMessage
                }
            } else {
                _isLoading.value = false
                _isError.value = taskLogin.exception?.localizedMessage
            }
        }
    }

    fun doRegister(username: String, email: String, password: String) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { taskRegister ->
                if (taskRegister.isSuccessful && taskRegister.result != null) {
                    // Sign in success, update UI with the signed-in user's information
                    _isLoading.value = false
                    val user = auth.currentUser
                    if (user != null) {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }
                        user.updateProfile(profileUpdates).addOnCompleteListener { taskRegister ->
                            if (taskRegister.isSuccessful) {
                                _isSuccess.value = true
                            }
                        }
                    } else {
                        _isError.value = taskRegister.exception?.localizedMessage
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    _isLoading.value = false
                    _isError.value = taskRegister.exception?.localizedMessage
                }
            }
    }
}