package id.heycoding.sportstesiyen.ui.auth.login

import android.content.Intent
import android.os.Bundle
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
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingActivity

class LoginFragment : Fragment() {

    private var _fragmentLoginBinding: FragmentLoginBinding? = null
    private val fragmentLoginBinding get() = _fragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

        doCheckingAccount()
        return fragmentLoginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AuthActivity).supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        fragmentLoginBinding?.apply {
            btnLogin.setOnClickListener {
                validateAndLogin()
            }
            tvRegister.setOnClickListener {
                (activity as AuthActivity).moveToFragment(RegisterFragment())
            }
        }
    }

    private fun doCheckingAccount() {
        authViewModel.doCheckingUser()
        authViewModel.isCheckingAccount.observe(viewLifecycleOwner) {
            if (it == true) {
                reload()
            }
        }

    }

    private fun validateAndLogin() {
        if (fragmentLoginBinding?.edtLoginEmail?.text!!.isBlank()) {
            fragmentLoginBinding?.edtLoginEmail?.error =
                context?.getString(R.string.txt_email_not_blank)
            return
        } else if (fragmentLoginBinding?.edtLoginPassword?.text!!.isBlank()) {
            fragmentLoginBinding?.edtLoginPassword!!.error =
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
            isSuccess.observe(viewLifecycleOwner) { reload() }
            isMessage.observe(viewLifecycleOwner) { showMessage(it) }
            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        fragmentLoginBinding?.pgLogin!!.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        doCheckingAccount()
    }

    private fun reload() {
        startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun onDetach() {
        super.onDetach()
        _fragmentLoginBinding = null
    }
}