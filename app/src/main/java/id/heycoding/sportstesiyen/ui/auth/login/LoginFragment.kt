package id.heycoding.sportstesiyen.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        setupUI()
        setupObserve()
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

    private fun setupObserve() {
        authViewModel.apply {
            isValidate.observe(viewLifecycleOwner) { moveToMain(it) }
            isSuccess.observe(viewLifecycleOwner) { moveToMain(it) }
            isError.observe(viewLifecycleOwner) { showMessage(it) }
            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
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
        }

    }

    private fun showLoading(isLoading: Boolean) {
        fragmentLoginBinding?.pgLogin?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        val view = layoutInflater.inflate(R.layout.popup_data_not_found, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val imgClosePopup: ImageView = view.findViewById(R.id.img_close_popup)
        val tvErrorPopup: TextView = view.findViewById(R.id.tv_error_popup)

        imgClosePopup.setOnClickListener {
            dialog.cancel()
        }
        tvErrorPopup.text = message
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
            startActivity(Intent(activity, MainActivity::class.java))
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