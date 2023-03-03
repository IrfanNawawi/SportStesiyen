package id.heycoding.sportstesiyen.ui.account

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.utils.DataDummy


class AccountViewModel : ViewModel() {

    private val _isLogOut = MutableLiveData<Boolean>()
    val isLogOut: LiveData<Boolean> = _isLogOut

    private val _isCheckingAccount = MutableLiveData<FirebaseUser?>()
    val isCheckingAccount: LiveData<FirebaseUser?> = _isCheckingAccount

    private val _isSuccessUpdated = MutableLiveData<String>()
    val isSuccessUpdated: LiveData<String> = _isSuccessUpdated

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser

    fun doSignOut() {
        auth.signOut()
        _isLogOut.value = true
    }

    fun doCheckingAccount() {
        if (user != null) {
            _isCheckingAccount.value = user
        }
    }

    fun doUploadImageFirebase(imageUrl: Uri) {
        UserProfileChangeRequest.Builder()
            .setPhotoUri(imageUrl)
            .build().also {
                user?.updateProfile(it)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _isSuccessUpdated.value = "Profile ${user.displayName} Updated"
                    } else {
                        _isError.value = task.exception?.message
                    }
                }
            }
    }

    fun doUpdatePassword(email: String, oldPassword: String, newPassword: String) {
        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        user?.reauthenticate(credential)?.addOnCompleteListener { auth ->
            if (auth.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _isSuccessUpdated.value = "User Password ${user.displayName} Updated"
                    } else {
                        _isError.value = task.exception?.message
                    }
                }
            } else {
                _isError.value = auth.exception?.message
            }
        }
    }

    fun getAccountMenu(): List<AccountMenu> = DataDummy.generateAccountMenu()
}