package id.heycoding.sportstesiyen.ui.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.FragmentAccountBinding
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstSports.DUMMY_EMAIL
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.io.ByteArrayOutputStream


class AccountFragment : Fragment(), AccountFragmentCallback {

    private var _fragmentAccountBinding: FragmentAccountBinding? = null
    private val fragmentAccountBinding get() = _fragmentAccountBinding
    private val accountViewModel: AccountViewModel by activityViewModel()
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var imageUri: Uri
    private lateinit var emailAccount: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)

        accountViewModel.apply {
            doCheckingAccount()
        }

        return fragmentAccountBinding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountAdapter = AccountAdapter(this)
        setupObserve()
        setupUI()
    }

    private fun setupObserve() {
        accountViewModel.apply {
            val onAccountMenu = getAccountMenu()
            accountAdapter.setAccountMenuData(onAccountMenu)

            isLogOut.observe(viewLifecycleOwner) { showSignOut() }
            isCheckingAccount.observe(viewLifecycleOwner) { showProfile(it) }
            isSuccessUpdated.observe(viewLifecycleOwner) { showMessage(it) }
            isError.observe(viewLifecycleOwner) { showMessage(it) }
        }
    }

    private fun setupUI() {
        fragmentAccountBinding?.apply {
            rvAccountMenu.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = accountAdapter
                clipToPadding = false
                clipChildren = false
            }

            imgProfileAccount.setOnClickListener {
                intentCamera()
            }
        }
    }


    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, Const.REQUEST_CAMERA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Const.REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image).addOnCompleteListener {
            if (it.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener {
                    it.result?.let {
                        imageUri = it
                        fragmentAccountBinding?.imgProfileAccount?.setImageBitmap(imgBitmap)

                        uploadImageFirebase(imageUri)
                    }
                }
            }
        }
    }

    private fun uploadImageFirebase(imageUri: Uri) {
        accountViewModel.doUploadImageFirebase(imageUri)
    }


    private fun showProfile(user: FirebaseUser?) {
        if (user != null)
            fragmentAccountBinding?.let {
                Glide.with(requireContext()).load(user.photoUrl).into(it.imgProfileAccount)

                emailAccount = user.email.toString()
                it.tvNameAccount.text = user.displayName
                it.tvEmailAccount.text = user.email
            }
        else
            fragmentAccountBinding?.imgProfileAccount?.let {
                Glide.with(requireContext()).load("https://picsum.photos/id/1/200/300").into(
                    it
                )
            }
    }

    private fun showSignOut() {
        startActivity(Intent(activity, AuthActivity::class.java))
    }

    private fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentAccountBinding = null
    }

    override fun onChangePassword() {
        val view = layoutInflater.inflate(R.layout.popup_change_password, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val edtOldPassword: EditText = view.findViewById(R.id.edt_old_password)
        val edtNewPassword: EditText = view.findViewById(R.id.edt_new_password)
        val edtConfirmPassword: EditText = view.findViewById(R.id.edt_confirm_password)
        val tvChangePassword: TextView = view.findViewById(R.id.tv_change_password)
        val imgClosePopup: ImageView = view.findViewById(R.id.img_close_popup)

        imgClosePopup.setOnClickListener {
            dialog.cancel()
        }

        tvChangePassword.setOnClickListener {
            val oldPassword = edtOldPassword.text.toString().trim()
            val newPassword = edtNewPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()
            if (
                oldPassword.isEmpty() || oldPassword.length < 6) {
                edtOldPassword.error =
                    context?.getString(R.string.txt_password_not_blank)
                edtOldPassword.requestFocus()
                return@setOnClickListener
            }

            if (
                newPassword.isEmpty() || newPassword.length < 6) {
                edtNewPassword.error =
                    context?.getString(R.string.txt_password_not_blank)
                edtNewPassword.requestFocus()
                return@setOnClickListener
            }

            if (
                newPassword != confirmPassword) {
                edtConfirmPassword.error =
                    context?.getString(R.string.txt_password_not_same)
                edtConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            accountViewModel.doUpdatePassword(
                fragmentAccountBinding?.tvEmailAccount?.text.toString(),
                oldPassword,
                newPassword
            )
        }
    }

    override fun onChangeLanguage() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    override fun onHelp() {
        val view = layoutInflater.inflate(R.layout.popup_contact_center, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val imgClosePopup: ImageView = view.findViewById(R.id.img_close_popup)
        val edtHelpPopup: EditText = view.findViewById(R.id.edt_help_text)
        val tvEmailPopup: TextView = view.findViewById(R.id.tv_help_email_popup)

        imgClosePopup.setOnClickListener { dialog.cancel() }
        tvEmailPopup.setOnClickListener {
            val help = edtHelpPopup.text.toString().trim()
            if (help.isEmpty()) {
                edtHelpPopup.error =
                    context?.getString(R.string.txt_password_not_same)
                edtHelpPopup.requestFocus()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto: $DUMMY_EMAIL")
            }
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    override fun onLogOutAccount() {
        accountViewModel.doSignOut()
    }
}