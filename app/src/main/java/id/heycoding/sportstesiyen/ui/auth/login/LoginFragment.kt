package id.heycoding.sportstesiyen.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.FragmentLoginBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
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
        initViews()
        initViewModel()
    }

    private fun initViews() {
        fragmentLoginBinding?.apply {
            btnLogin.setOnClickListener {
                validateAndLogin()
            }
            tvRegister.setOnClickListener {
                (activity as AuthActivity).moveToFragment(RegisterFragment())
            }
        }
    }

    private fun initViewModel() {
        authViewModel.apply {
            isSuccess.observe(viewLifecycleOwner) { showCheckingUser(it) }
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

        authViewModel.apply {
            doLogin(userEmail, userPassword)

            isSuccess.observe(viewLifecycleOwner) { moveToOtp(it) }
            isError.observe(viewLifecycleOwner) { showMessage(it) }
            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        }

    }

    private fun showCheckingUser(user: String) {
        if (user.isNotEmpty()) {
            moveToMain(user)
        }
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