package id.heycoding.sportstesiyen.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private var _fragmentCategoryBinding: FragmentCategoryBinding? = null
    private val fragmentCategoryBinding get() = _fragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentCategoryBinding = FragmentCategoryBinding.inflate(layoutInflater)
        return fragmentCategoryBinding?.root
    }
}