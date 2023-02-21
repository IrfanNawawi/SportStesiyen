package id.heycoding.sportstesiyen.ui.auth.register

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
import id.heycoding.sportstesiyen.databinding.FragmentRegisterBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.ui.auth.AuthUiState
import id.heycoding.sportstesiyen.ui.auth.AuthViewModel
import id.heycoding.sportstesiyen.ui.otp.OtpActivity
import id.heycoding.sportstesiyen.utils.Const

class RegisterFragment : Fragment() {

    private var _fragmentRegisterBinding: FragmentRegisterBinding? = null
    private val fragmentRegisterBinding get() = _fragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return fragmentRegisterBinding?.root
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
        fragmentRegisterBinding?.apply {
            btnRegister.setOnClickListener {
                validateAndRegister()
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

    private fun validateAndRegister() {
        if (fragmentRegisterBinding?.edtRegisterUsername?.text?.isBlank() == true) {
            fragmentRegisterBinding?.edtRegisterUsername?.error =
                context?.getString(R.string.txt_username_not_blank)
            return
        } else if (
            fragmentRegisterBinding?.edtRegisterEmail?.text?.isBlank() == true) {
            fragmentRegisterBinding?.edtRegisterEmail?.error =
                context?.getString(R.string.txt_email_not_blank)
            return
        } else if (
            fragmentRegisterBinding?.edtRegisterPassword?.text?.isBlank() == true) {
            fragmentRegisterBinding?.edtRegisterPassword?.error =
                context?.getString(R.string.txt_password_not_blank)
            return
        } else {
            doSignUpAccount()
        }
    }

    private fun doSignUpAccount() {
        val userName = fragmentRegisterBinding?.edtRegisterUsername?.text.toString().trim()
        val userEmail = fragmentRegisterBinding?.edtRegisterEmail?.text.toString().trim()
        val userPassword = fragmentRegisterBinding?.edtRegisterPassword?.text.toString().trim()

        authViewModel.doRegister(userName, userEmail, userPassword)
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentRegisterBinding?.pgRegister?.visibility =
            if (isLoading) View.VISIBLE else View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentRegisterBinding = null
    }
}