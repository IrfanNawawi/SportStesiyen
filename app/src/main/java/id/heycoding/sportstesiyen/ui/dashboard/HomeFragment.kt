package id.heycoding.sportstesiyen.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.heycoding.sportstesiyen.data.remote.MainWebServices
import id.heycoding.sportstesiyen.databinding.FragmentHomeBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val services = MainWebServices.create()

        textView.setOnClickListener {
            val sportsDisposable = services.getAllSports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    println("Loading.....")
                    textView.text = "Loading....."
                }
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe({
                    val isSuccess = it.sports
                    if (isSuccess != null) {
                        val response = it
                        val data = response?.sports
                        println("Sukses nih")
                        textView.text = data?.toString()
                    } else {
                        println("Error nih")
                        textView.text = "Error nih"
                    }
                    println("Response anjay -> ${it.sports}")
                },{
                    println("Error di rx")
                    it.printStackTrace()
                })
            disposable.add(sportsDisposable)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.dispose()
    }
}