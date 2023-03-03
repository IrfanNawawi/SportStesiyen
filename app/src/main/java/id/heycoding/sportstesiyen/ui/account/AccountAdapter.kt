package id.heycoding.sportstesiyen.ui.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.databinding.ItemAccountMenuBinding
import id.heycoding.sportstesiyen.utils.PreferencesApp


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

                val checkedItem = PreferencesApp(itemView.context).darkMode
                swActionAccount.isChecked = (checkedItem == 1)

                swActionAccount.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        PreferencesApp(itemView.context).darkMode = 1
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        PreferencesApp(itemView.context).darkMode = 0
                    }
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