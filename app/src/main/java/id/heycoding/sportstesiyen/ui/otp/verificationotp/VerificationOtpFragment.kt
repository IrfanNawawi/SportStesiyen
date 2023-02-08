package id.heycoding.sportstesiyen.ui.otp.verificationotp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.databinding.FragmentVerificationOtpBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.otp.OtpActivity
import id.heycoding.sportstesiyen.ui.otp.OtpViewModel
import id.heycoding.sportstesiyen.utils.Const
import java.util.concurrent.TimeUnit

class VerificationOtpFragment : Fragment() {

    private var _fragmentVerificationOtpBinding: FragmentVerificationOtpBinding? = null
    private val fragmentVerificationOtpBinding get() = _fragmentVerificationOtpBinding
    private val otpViewModel: OtpViewModel by activityViewModels()
    private var auth: FirebaseAuth = Firebase.auth
    private var phoneNumber: String? = null
    private var verificationId: String? = null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var code: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentVerificationOtpBinding =
            FragmentVerificationOtpBinding.inflate(layoutInflater, container, false)
        getDataArguments()
        return fragmentVerificationOtpBinding?.root
    }

    private fun getDataArguments() {
        phoneNumber = arguments?.getString(Const.EXTRA_PHONE_NUMBER, "")
        verificationId = arguments?.getString(Const.EXTRA_OTP_NUMBER, "")
        fragmentVerificationOtpBinding?.tvPhoneVerificationOtp?.text = phoneNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as OtpActivity).supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        fragmentVerificationOtpBinding?.apply {
            edtOtpPhone1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) edtOtpPhone2.requestFocus()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            edtOtpPhone2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) edtOtpPhone3.requestFocus()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            edtOtpPhone3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) edtOtpPhone4.requestFocus()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            edtOtpPhone4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) edtOtpPhone5.requestFocus()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            edtOtpPhone5.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) edtOtpPhone6.requestFocus()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })

            btnVerifyOtp.setOnClickListener {
                if (edtOtpPhone1.text.toString().trim().isEmpty() || edtOtpPhone2.text.toString()
                        .trim().isEmpty() || edtOtpPhone3.text.toString().trim()
                        .isEmpty() || edtOtpPhone4.text.toString().trim()
                        .isEmpty() || edtOtpPhone5.text.toString().trim()
                        .isEmpty() || edtOtpPhone6.text.toString().trim().isEmpty()
                ) {
                    Toast.makeText(context, "Please enter valid code", Toast.LENGTH_SHORT).show()
                } else {
                    code = edtOtpPhone1.text.toString() +
                            edtOtpPhone2.text.toString() +
                            edtOtpPhone3.text.toString() +
                            edtOtpPhone4.text.toString() +
                            edtOtpPhone5.text.toString() +
                            edtOtpPhone6.text.toString()
                }

                if (verificationId != null) {
                    val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
                    signInWithPhoneAuthCredential(credential)
                }
            }

            tvResendVerificationOtp.setOnClickListener {
                doGetResendOtp()
            }
        }
    }

    private fun doGetResendOtp() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(context, p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationId = p0
                Toast.makeText(context, "OTP Resend", Toast.LENGTH_SHORT).show()
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+62$phoneNumber")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("VERIFICATION OTP", "signInWithCredential:success")
                task.result?.user?.let { moveToMain(it) }
            } else {
                // Sign in failed, display a message and update the UI
                Log.w("VERIFICATION OTP", "signInWithCredential:failure", task.exception)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(
                        context,
                        "The verification code entered was invalid",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun moveToMain(user: FirebaseUser) {
        startActivity(
            Intent(
                activity,
                MainActivity::class.java
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentVerificationOtpBinding = null
    }
}