package id.heycoding.sportstesiyen.ui.auth.login

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.heycoding.sportstesiyen.databinding.FragmentLoginBinding
import id.heycoding.sportstesiyen.ui.MainActivity
import id.heycoding.sportstesiyen.ui.auth.AuthViewModel
import id.heycoding.storysubmission.BuildConfig.PREF_NAME
import id.heycoding.storysubmission.MainActivity
import id.heycoding.storysubmission.data.remote.response.auth.AuthSession
import id.heycoding.storysubmission.databinding.FragmentLoginBinding
import id.heycoding.storysubmission.ui.auth.AuthViewModel
import id.heycoding.storysubmission.ui.auth.register.RegisterFragment
import id.heycoding.storysubmission.ui.home.HomeFragment
import id.heycoding.storysubmission.utils.Preferences

class LoginFragment : Fragment() {

    private var fragmentLoginBinding: FragmentLoginBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private lateinit var pref: SharedPreferences
    private lateinit var userLoginPref: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        initVM()
        initPref()
        return fragmentLoginBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
        initView()
    }

    private fun initVM() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        authViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        authViewModel.message.observe(viewLifecycleOwner) { showMessage(it) }
    }

    private fun initPref() {
        pref = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        userLoginPref = Preferences(requireContext())
    }

    private fun initView() {
        fragmentLoginBinding?.apply {
            btnLogin.setOnClickListener {
                validateAndLogin()
            }
            tvRegister.setOnClickListener {
                (activity as MainActivity).moveToFragment(RegisterFragment())
            }
        }
    }

    private fun validateAndLogin() {
        if (fragmentLoginBinding?.edtLoginEmail?.text!!.isBlank()) {
            fragmentLoginBinding?.edtLoginEmail?.error = "Email tidak boleh kosong"
            return
        } else if (fragmentLoginBinding?.edtLoginPassword?.text!!.isBlank()) {
            fragmentLoginBinding?.edtLoginPassword!!.error = "Password tidak boleh kosong"
            return
        } else {
            doLogin()
        }
    }

    private fun doLogin() {
        val userEmail = fragmentLoginBinding?.edtLoginEmail?.text.toString().trim()
        val userPassword = fragmentLoginBinding?.edtLoginPassword?.text.toString().trim()

        authViewModel.apply {
            doLogin(userEmail, userPassword)
            userLogin.observe(viewLifecycleOwner) {
                if (it != null) {
                    //save the login session

                    val currentUser = AuthSession(
                        it.name,
                        it.token,
                        it.userId,
                        true
                    )

                    userLoginPref.setUserLogin(currentUser)

                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Login Berhasil")
                        setMessage("Logged atas nama ${it.name}!")
                        setPositiveButton("Ok") { _, _ ->
                            (activity as MainActivity).moveToFragment(HomeFragment())
                        }
                        create()
                        show()
                    }
                }

            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        fragmentLoginBinding?.pgLogin!!.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentLoginBinding = null
    }
}