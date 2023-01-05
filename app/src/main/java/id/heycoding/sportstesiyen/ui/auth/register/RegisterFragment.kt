package id.heycoding.sportstesiyen.ui.auth.register

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.heycoding.sportstesiyen.databinding.FragmentRegisterBinding
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.ui.auth.AuthViewModel
import id.heycoding.sportstesiyen.ui.auth.login.LoginFragment

class RegisterFragment : Fragment() {

    private var fragmentRegisterBinding: FragmentRegisterBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private lateinit var pref: SharedPreferences
//    private lateinit var userLoginPref: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
//        initVM()
//        initPref()
        return fragmentRegisterBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AuthActivity).supportActionBar?.hide()
        initView()
    }

    private fun initVM() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

//        authViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
//        authViewModel.message.observe(viewLifecycleOwner) { showMessage(it) }
    }

    private fun initPref() {
//        pref = requireActivity().getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
//        userLoginPref = Preferences(requireContext())
    }

    private fun initView() {
        fragmentRegisterBinding?.apply {
            btnRegister.setOnClickListener {
                Toast.makeText(context, "Berhasil Didaftarkan", Toast.LENGTH_LONG).show()
                (activity as AuthActivity).moveToFragment(LoginFragment())
//                validateAndRegister()
            }
        }
    }

    private fun validateAndRegister() {
        if (fragmentRegisterBinding?.edtRegisterUsername?.text!!.isBlank()) {
            fragmentRegisterBinding?.edtRegisterUsername!!.error = "Username tidak boleh kosong"
            return
        } else if (
            fragmentRegisterBinding?.edtRegisterEmail?.text!!.isBlank()) {
            fragmentRegisterBinding?.edtRegisterEmail!!.error = "Email tidak boleh kosong"
            return
        } else if (
            fragmentRegisterBinding?.edtRegisterPassword?.text!!.isBlank()) {
            fragmentRegisterBinding?.edtRegisterPassword!!.error = "Password tidak boleh kosong"
            return
        } else {
            doRegister()
        }
    }

    private fun doRegister() {
        val username = fragmentRegisterBinding?.edtRegisterUsername?.text.toString().trim()
        val userEmail = fragmentRegisterBinding?.edtRegisterEmail?.text.toString().trim()
        val userPassword = fragmentRegisterBinding?.edtRegisterPassword?.text.toString().trim()

        authViewModel.apply {
//            doRegister(username, userEmail, userPassword)
//            isError.observe(viewLifecycleOwner) {
//                if (it != true) {
//                    (activity as MainActivity).moveToFragment(HomeFragment())
//                }
//            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentRegisterBinding?.pgRegister!!.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentRegisterBinding = null
    }
}