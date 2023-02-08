package id.heycoding.sportstesiyen.ui.otp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class OtpViewModel(application: Application) : AndroidViewModel(application) {

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private val _isMessage = MutableLiveData<String>()
    val isMessage: LiveData<String> = _isMessage

    private val _isVisible = MutableLiveData<Boolean>()
    val isVisible: LiveData<Boolean> = _isVisible
}
