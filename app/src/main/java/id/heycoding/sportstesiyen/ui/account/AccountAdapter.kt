package id.heycoding.sportstesiyen.ui.account

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.databinding.ItemAccountMenuBinding


class AccountAdapter(private val callback: AccountFragmentCallback) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    private val listAccountData = ArrayList<AccountMenu>()

    inner class ViewHolder(private val binding: ItemAccountMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: AccountMenu) {
            binding.apply {
                Glide.with(itemView.context).load(account.menuAccountImage).into(imgMenuAccount)
                tvMenuAccount.text = account.menuAccountName
                if (account.menuAccountSwitch == 1) {
                    swActionAccount.visibility = View.VISIBLE
                    imgActionAccount.visibility = View.GONE
                } else {
                    swActionAccount.visibility = View.GONE
                    imgActionAccount.visibility = View.VISIBLE
                }

                swActionAccount.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }

                swActionAccount.setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        swActionAccount.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    // always return false since we don't care about handling tapping, flinging, etc.
                    false
                }

                itemView.setOnClickListener {
                    when (account.menuAccountId) {
                        1 -> {
                            callback.onChangePassword()
                        }
                        3 -> {
                            callback.onChangeLanguage()
                        }
                        4 -> {
                            callback.onHelp()
                        }
                        5 -> {
                            callback.onLogOutAccount()
                        }
                    }
                }
            }
        }
    }

    fun setAccountMenuData(account: List<AccountMenu>) {
        listAccountData.clear()
        listAccountData.addAll(account)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAccountMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAccountData[position])
    }

    override fun getItemCount(): Int = listAccountData.size
}