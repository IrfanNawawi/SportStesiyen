package id.heycoding.sportstesiyen.ui.auth.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.FragmentLoginBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.ui.auth.AuthUiState
import id.heycoding.sportstesiyen.ui.auth.AuthViewModel
import id.heycoding.sportstesiyen.ui.auth.register.RegisterFragment
import id.heycoding.sportstesiyen.ui.otp.OtpActivity
import id.heycoding.sportstesiyen.utils.Const

class LoginFragment : Fragment() {

    private var _fragmentLoginBinding: FragmentLoginBinding? = null
    private val fragmentLoginBinding get() = _fragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return fragmentLoginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AuthActivity).supportActionBar?.hide()

        if (isOnline(requireContext())) {
            setupObserver()
            setupUI()
        } else {
            showErrorConnection()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun showErrorConnection() {
        val view = layoutInflater.inflate(R.layout.popup_error_connection, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val tvRetryConnectionHome: TextView = view.findViewById(R.id.tv_retry_connection_home)
        tvRetryConnectionHome.setOnClickListener { dialog.cancel() }
    }

    private fun setupUI() {
        fragmentLoginBinding?.apply {
            btnLogin.setOnClickListener {
                validateAndLogin()
            }
            tvRegister.setOnClickListener {
                (activity as AuthActivity).moveToFragment(RegisterFragment())
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            authViewModel.authUiState.collect {
                when (it) {
                    is AuthUiState.ValidateUser -> {
                        moveToMain(it.status)
                        showLoading(false)
                    }
                    is AuthUiState.Success -> {
                        moveToOtp(it.status)
                        showLoading(false)
                    }
                    is AuthUiState.Error -> {
                        showMessage(it.message)
                        showLoading(false)
                    }
                    is AuthUiState.Loading -> {
                        showLoading(true)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun validateAndLogin() {
        if (fragmentLoginBinding?.edtLoginEmail?.text?.isBlank() == true) {
            fragmentLoginBinding?.edtLoginEmail?.error =
                context?.getString(R.string.txt_email_not_blank)
            return
        } else if (fragmentLoginBinding?.edtLoginPassword?.text?.isBlank() == true) {
            fragmentLoginBinding?.edtLoginPassword?.error =
                context?.getString(R.string.txt_password_not_blank)
            return
        } else {
            doSignAccount()
        }
    }

    private fun doSignAccount() {
        val userEmail = fragmentLoginBinding?.edtLoginEmail?.text.toString().trim()
        val userPassword = fragmentLoginBinding?.edtLoginPassword?.text.toString().trim()

        authViewModel.doLogin(userEmail, userPassword)
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentLoginBinding?.pgLogin?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun moveToOtp(user: String) {
        if (user.isNotEmpty()) {
            startActivity(
                Intent(activity, OtpActivity::class.java).putExtra(
                    Const.EXTRA_USER_ACCOUNT,
                    user
                )
            )
        }
    }

    private fun moveToMain(user: String) {
        if (user.isNotEmpty()) {
            startActivity(
                Intent(activity, MainActivity::class.java).putExtra(
                    Const.EXTRA_USER_ACCOUNT,
                    user
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        authViewModel.doCheckingUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentLoginBinding = null
    }
}