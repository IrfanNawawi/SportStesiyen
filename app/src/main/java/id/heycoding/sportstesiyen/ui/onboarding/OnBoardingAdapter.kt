package id.heycoding.sportstesiyen.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sportstesiyen.databinding.ItemSliderOnboardingBinding

class OnBoardingAdapter(private val listOnBoardingData: ArrayList<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSliderOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onBoardingItem: OnBoardingItem) {
            binding.apply {
                imgOnboarding.setImageResource(onBoardingItem.imageOnBoarding)
                tvTitleOnboarding.text = onBoardingItem.titleOnBoarding
                tvDescOnboarding.text = onBoardingItem.descOnBoarding
            }
        }
    }

    fun setOnBoardingData(onBoarding: List<OnBoardingItem>) {
        val diffCallback = OnBoardingCallback(listOnBoardingData, onBoarding)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listOnBoardingData.clear()
        listOnBoardingData.addAll(onBoarding)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSliderOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOnBoardingData[position])
    }

    override fun getItemCount(): Int = listOnBoardingData.size
}