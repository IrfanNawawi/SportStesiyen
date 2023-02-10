package id.heycoding.sportstesiyen.ui.home.topheadlinenews.detailtopheadlinenews

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.databinding.ActivityDetailNewsTopheadlineBinding
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.Helper

class DetailNewsTopHeadlineActivity : AppCompatActivity() {

    private var _activityDetailNewsTopheadlineBinding: ActivityDetailNewsTopheadlineBinding? = null
    private val activityDetailNewsTopheadlineBinding get() = _activityDetailNewsTopheadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailNewsTopheadlineBinding =
            ActivityDetailNewsTopheadlineBinding.inflate(layoutInflater)
        setContentView(activityDetailNewsTopheadlineBinding?.root)

        setSupportActionBar(activityDetailNewsTopheadlineBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (supportActionBar != null) {
            supportActionBar?.title = ""
        }

        if (isOnline(this)) {
            initViews()
        } else {
            showErrorConnection()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun showErrorConnection() {
        val view = layoutInflater.inflate(R.layout.popup_error_connection, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val tvRetryConnectionHome: TextView = view.findViewById(R.id.tv_retry_connection_home)
        tvRetryConnectionHome.setOnClickListener { dialog.cancel() }
    }

    private fun initViews() {
        val extras = intent.extras
        if (extras != null) {
            val getData =
                extras.getParcelable<Articles>(ConstNews.EXTRA_NEWS_TOPHEADLINE)


            activityDetailNewsTopheadlineBinding?.apply {
                contentDetailNews.tvTitleDetailNews.text = getData?.title
                contentDetailNews.tvDescDetailNews.text = getData?.description
                contentDetailNews.tvContentDetailNews.text = getData?.content
                contentDetailNews.tvDateDetailNews.text =
                    Helper.timeAgo(getData?.publishedAt)

                if (getData?.author != "" && getData?.source?.name != "") {
                    contentDetailNews.cvAuthorNews.visibility =
                        View.VISIBLE
                    contentDetailNews.tvAuthorDetailNews.text =
                        getData?.author

                    contentDetailNews.cvSourceNews.visibility =
                        View.VISIBLE
                    contentDetailNews.tvSourceDetailNews.text =
                        getData?.source?.name
                } else if (getData.source?.name != null && getData.source.name != "") {
                    contentDetailNews.cvSourceNews.visibility =
                        View.VISIBLE
                    contentDetailNews.tvSourceDetailNews.text =
                        getData.source.name
                } else if (getData.author != null && getData.author != "") {
                    contentDetailNews.cvAuthorNews.visibility =
                        View.VISIBLE
                    contentDetailNews.tvAuthorDetailNews.text =
                        getData.author
                } else {
                    contentDetailNews.cvSourceNews.visibility =
                        View.GONE
                    contentDetailNews.cvSourceNews.visibility =
                        View.GONE
                }

                appbarDetailNews.addOnOffsetChangedListener(object :
                    AppBarLayout.OnOffsetChangedListener {
                    var isShow = false
                    var scrollRange = -1
                    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.totalScrollRange
                        }
                        if (scrollRange + verticalOffset == 0) {
                            tvTitleToolbar.text = getData?.title
                            isShow = true
                        } else if (isShow) {
                            tvTitleToolbar.text = ""
                            isShow = false
                        }
                    }
                })
            }

            activityDetailNewsTopheadlineBinding?.imgDetailNews?.let {
                Glide.with(this)
                    .load(getData?.urlToImage ?: 0)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailNewsTopheadlineBinding = null
    }
}