package id.heycoding.sportstesiyen.ui.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import id.heycoding.sportstesiyen.databinding.FragmentAccountBinding
import id.heycoding.sportstesiyen.databinding.FragmentHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeViewModel
import id.heycoding.sportstesiyen.ui.home.banner.BannerAdapter
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.ui.home.eventsleague.HomeEventsLeagueAdapter
import id.heycoding.sportstesiyen.utils.Const
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.io.ByteArrayOutputStream
import java.net.URI

class AccountFragment : Fragment(), AccountFragmentCallback {

    private var _fragmentAccountBinding: FragmentAccountBinding? = null
    private val fragmentAccountBinding get() = _fragmentAccountBinding
    private val accountViewModel: AccountViewModel by activityViewModel()
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        getDataArguments()
        return fragmentAccountBinding?.root!!
    }

    private fun getDataArguments() {
        val userAccount = activity?.intent?.getStringExtra(Const.EXTRA_USER_ACCOUNT)
        fragmentAccountBinding?.tvNameAccount?.text = userAccount
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountAdapter = AccountAdapter(this)
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        accountViewModel.apply {
            val onAccountMenu = getAccountMenu()
            accountAdapter.setAccountMenuData(onAccountMenu)
        }
    }

    private fun initViews() {
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
        val ref = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image).addOnCompleteListener{
            if (it.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener {
                    it.result?.let {
                        imageUri = it
                        fragmentAccountBinding?.imgProfileAccount?.setImageBitmap(imgBitmap)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentAccountBinding = null
    }

    override fun onChangePassword() {
        Toast.makeText(context, "Ganti Password Cuy", Toast.LENGTH_SHORT).show()
    }

    override fun onChangeLanguage() {
        Toast.makeText(context, "Ganti Bahasa Cuy", Toast.LENGTH_SHORT).show()
    }

    override fun onHelp() {
        Toast.makeText(context, "Butuh Bantuan Cuy", Toast.LENGTH_SHORT).show()
    }

    override fun onLogOutAccount() {
        Toast.makeText(context, "Keluar Cuy", Toast.LENGTH_SHORT).show()
    }
}