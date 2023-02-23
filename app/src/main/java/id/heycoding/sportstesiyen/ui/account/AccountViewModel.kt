package id.heycoding.sportstesiyen.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.utils.DataDummy

class AccountViewModel : ViewModel() {

    private val _isValidate = MutableLiveData<Boolean>()
    val isValidate: LiveData<Boolean> = _isValidate

    private var auth: FirebaseAuth = Firebase.auth

    fun doSignOut() {
        auth.signOut()
        _isValidate.value = true
    }

    fun getAccountMenu(): List<AccountMenu> = DataDummy.generateAccountMenu()
}