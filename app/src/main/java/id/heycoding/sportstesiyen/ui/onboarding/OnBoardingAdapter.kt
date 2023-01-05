package id.heycoding.sportstesiyen.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sportstesiyen.databinding.ListSliderOnboardingBinding

class OnBoardingAdapter(private val onBoardingItem: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListSliderOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onBoardingItem: OnBoardingItem) {
            binding.apply {
                imgOnboarding.setImageResource(onBoardingItem.imageOnBoarding)
                tvTitleOnboarding.text = onBoardingItem.titleOnBoarding
                tvDescOnboarding.text = onBoardingItem.descOnBoarding
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListSliderOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onBoardingItem[position])
    }

    override fun getItemCount(): Int = onBoardingItem.size
}