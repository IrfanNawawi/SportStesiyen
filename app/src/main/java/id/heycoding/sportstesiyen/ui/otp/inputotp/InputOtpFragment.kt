package id.heycoding.sportstesiyen.ui.otp.inputotp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.FragmentInputOtpBinding
import id.heycoding.sportstesiyen.ui.otp.OtpActivity
import id.heycoding.sportstesiyen.ui.otp.verificationotp.VerificationOtpFragment
import id.heycoding.sportstesiyen.utils.Const
import java.util.concurrent.TimeUnit


class InputOtpFragment : Fragment() {

    private var _fragmentInputOtpBinding: FragmentInputOtpBinding? = null
    private val fragmentInputOtpBinding get() = _fragmentInputOtpBinding
    private var auth: FirebaseAuth = Firebase.auth
    private var phoneNumber: String? = null
    private var userAccount: String? = null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentInputOtpBinding = FragmentInputOtpBinding.inflate(layoutInflater, container, false)
        getDataArguments()
        return fragmentInputOtpBinding?.root
    }

    private fun getDataArguments() {
        userAccount = activity?.intent?.getStringExtra(Const.EXTRA_USER_ACCOUNT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as OtpActivity).supportActionBar?.hide()
        setupUI()
    }

    private fun setupUI() {
        fragmentInputOtpBinding?.apply {
            btnGetOtp.setOnClickListener {
                validateAndGetOtp()
            }
        }
    }

    private fun validateAndGetOtp() {
        fragmentInputOtpBinding?.edtOtpPhone?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 1 && s.toString().startsWith("0")) {
                    s?.clear()
                    Toast.makeText(context, "No telepon tidak boleh awalan 0", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
        if (fragmentInputOtpBinding?.edtOtpPhone?.text?.isBlank() == true) {
            fragmentInputOtpBinding?.edtOtpPhone?.error =
                context?.getString(R.string.txt_phone_not_blank)
            return
        } else {
            doGetOtp()
        }
    }

    private fun doGetOtp() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                fragmentInputOtpBinding?.btnGetOtp?.visibility = View.VISIBLE
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                fragmentInputOtpBinding?.btnGetOtp?.visibility = View.VISIBLE
                showMessage(p0.message)
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                fragmentInputOtpBinding?.btnGetOtp?.visibility = View.VISIBLE
                moveToVerifyOtp(p0)
            }
        }

        phoneNumber = fragmentInputOtpBinding?.edtOtpPhone?.text.toString().trim()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+62$phoneNumber")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun showMessage(message: String?) {
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

    fun moveToVerifyOtp(token: String) {
        val fragment = VerificationOtpFragment()
        val bundle = Bundle()
        bundle.putString(Const.EXTRA_PHONE_NUMBER, phoneNumber)
        bundle.putString(Const.EXTRA_USER_ACCOUNT, userAccount)
        bundle.putString(Const.EXTRA_OTP_NUMBER, token)
        fragment.arguments = bundle
        (activity as OtpActivity).moveToFragment(fragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentInputOtpBinding = null
    }
}