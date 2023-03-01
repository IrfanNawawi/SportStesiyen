package id.heycoding.sportstesiyen.ui.auth.register

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
import id.heycoding.sportstesiyen.databinding.FragmentRegisterBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
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
        setupUI()
        setupObserve()
    }

    private fun setupUI() {
        fragmentRegisterBinding?.apply {
            btnRegister.setOnClickListener {
                validateAndRegister()
            }
        }
    }

    private fun setupObserve() {
        authViewModel.apply {
            isSuccess.observe(viewLifecycleOwner) { showToast(it) }
            isError.observe(viewLifecycleOwner) { showMessage(it) }
            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        }
    }

    private fun validateAndRegister() {
        if (fragmentRegisterBinding?.edtRegisterUsername?.text?.isBlank() == true) {
            fragmentRegisterBinding?.edtRegisterUsername?.error =
                context?.getString(R.string.txt_username_not_blank)
            return
        }  else if (
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

        authViewModel.apply {
            doRegister(userName, userEmail, userPassword)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentRegisterBinding?.pgRegister?.visibility =
            if (isLoading) View.VISIBLE else View.GONE
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

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentRegisterBinding = null
    }
}