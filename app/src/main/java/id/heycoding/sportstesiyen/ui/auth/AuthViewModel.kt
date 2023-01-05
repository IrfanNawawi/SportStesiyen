package id.heycoding.sportstesiyen.ui.auth

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

//    private val _userLogin = MutableLiveData<UserLoginResult>()
//    val userLogin: LiveData<UserLoginResult> = _userLogin
//
//    private val _isError = MutableLiveData<Boolean>()
//    val isError: LiveData<Boolean> = _isError
//
//    private val _message = MutableLiveData<String>()
//    val message: LiveData<String> = _message
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val services = WebServices.create()
//
//    fun doLogin(email: String, password: String) {
//        _isLoading.value = true
//        services.loginUser(email, password)
//            .enqueue(object : Callback<UserLoginResponse> {
//                override fun onResponse(
//                    call: Call<UserLoginResponse>,
//                    response: Response<UserLoginResponse>
//                ) {
//                    _isLoading.value = false
//                    if (!response.isSuccessful) {
//                        _message.value = response.message()
//                    }
//                    _isError.value = response.body()?.error
//                    _message.value = response.body()?.message
//                    _userLogin.value = response.body()?.loginResult
//                }
//
//                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
//                    _message.value = t.message
//                    _isLoading.value = false
//                    Log.i("LoginFragment", "subscribe: gagal lah")
//                }
//            })
//    }
//
//    fun doRegister(name: String, email: String, password: String) {
//        _isLoading.value = true
//        services.registerUser(name, email, password)
//            .enqueue(object : Callback<UserRegisterResponse> {
//                override fun onResponse(
//                    call: Call<UserRegisterResponse>,
//                    response: Response<UserRegisterResponse>
//                ) {
//                    _isLoading.value = false
//                    if (!response.isSuccessful) {
//                        _message.value = response.message()
//                    }
//                    _isError.value = response.body()?.error
//                    _message.value = response.body()?.message
//                }
//
//                override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {
//                    _message.value = t.message
//                    _isLoading.value = false
//                }
//
//            })
//    }
}