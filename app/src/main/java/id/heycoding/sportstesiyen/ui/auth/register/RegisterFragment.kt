package id.heycoding.sportstesiyen.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.FragmentRegisterBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.ui.auth.AuthViewModel
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingActivity

class RegisterFragment : Fragment() {

    private var _fragmentRegisterBinding: FragmentRegisterBinding? = null
    private val fragmentRegisterBinding get() = _fragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        doCheckingAccount()
        return fragmentRegisterBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AuthActivity).supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        fragmentRegisterBinding?.apply {
            btnRegister.setOnClickListener {
                validateAndRegister()
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

    private fun validateAndRegister() {
        if (fragmentRegisterBinding?.edtRegisterUsername?.text!!.isBlank()) {
            fragmentRegisterBinding?.edtRegisterUsername!!.error =
                context?.getString(R.string.txt_username_not_blank)
            return
        } else if (
            fragmentRegisterBinding?.edtRegisterEmail?.text!!.isBlank()) {
            fragmentRegisterBinding?.edtRegisterEmail!!.error =
                context?.getString(R.string.txt_email_not_blank)
            return
        } else if (
            fragmentRegisterBinding?.edtRegisterPassword?.text!!.isBlank()) {
            fragmentRegisterBinding?.edtRegisterPassword!!.error =
                context?.getString(R.string.txt_password_not_blank)
            return
        } else {
            doRegister()
        }
    }

    private fun doRegister() {
        val userName = fragmentRegisterBinding?.edtRegisterUsername?.text.toString().trim()
        val userEmail = fragmentRegisterBinding?.edtRegisterEmail?.text.toString().trim()
        val userPassword = fragmentRegisterBinding?.edtRegisterPassword?.text.toString().trim()

        authViewModel.apply {

            doRegister(userName, userEmail, userPassword)

            isSuccess.observe(viewLifecycleOwner) { reload() }
            isMessage.observe(viewLifecycleOwner) { showMessage(it) }
            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentRegisterBinding?.pgRegister!!.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        doCheckingAccount()
    }

    private fun reload() {
        startActivity(Intent(activity, MainActivity::class.java))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentRegisterBinding = null
    }
}