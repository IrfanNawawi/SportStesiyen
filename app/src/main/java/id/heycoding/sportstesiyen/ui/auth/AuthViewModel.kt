package id.heycoding.sportstesiyen.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.R


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isCheckingAccount = MutableLiveData<Boolean>()
    val isCheckingAccount: LiveData<Boolean> = _isCheckingAccount

    private val _isMessage = MutableLiveData<String>()
    val isMessage: LiveData<String> = _isMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var auth: FirebaseAuth = Firebase.auth

    fun doCheckingUser() {
        val user = auth.currentUser
        _isCheckingAccount.value = user != null
    }


    fun doLogin(email: String, password: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                _isLoading.value = false
                val user = task.result.user
                if (user != null) {
                    _isSuccess.value = true
                } else {
                    _isMessage.value = task.exception?.localizedMessage
                }
            } else {
                _isLoading.value = false
                _isMessage.value = task.exception?.localizedMessage
            }
        }
    }

    fun doRegister(username: String, email: String, password: String) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    // Sign in success, update UI with the signed-in user's information
                    _isLoading.value = false
                    val user = auth.currentUser
                    if (user != null) {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }
                        user.updateProfile(profileUpdates).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _isSuccess.value = true
                            }
                        }
                    } else {
                        _isMessage.value = task.exception?.localizedMessage
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    _isLoading.value = false
                    _isMessage.value = task.exception?.localizedMessage
                }
            }
    }
}