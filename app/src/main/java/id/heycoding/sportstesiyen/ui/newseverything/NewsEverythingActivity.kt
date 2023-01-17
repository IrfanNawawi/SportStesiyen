package id.heycoding.sportstesiyen.ui.newseverything

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.heycoding.sportstesiyen.data.remote.response.ArticlesEverything
import id.heycoding.sportstesiyen.databinding.ActivityNewsEverythingBinding
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.Helper

class NewsEverythingActivity : AppCompatActivity() {

    private var _activityNewsEverythingBinding: ActivityNewsEverythingBinding? = null
    private val activityNewsEverythingBinding get() = _activityNewsEverythingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityNewsEverythingBinding = ActivityNewsEverythingBinding.inflate(layoutInflater)
        setContentView(activityNewsEverythingBinding?.root)

        setSupportActionBar(activityNewsEverythingBinding?.toolbarNews)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val getData = extras.getParcelable<ArticlesEverything>(ConstNews.EXTRA_NEWS_EVERYTHING)

            activityNewsEverythingBinding?.apply {
                contentNewsDetail.tvTitleNewsDetail.text =
                    getData?.title
                contentNewsDetail.tvDescNewsDetail.text =
                    getData?.description
                contentNewsDetail.tvContentNewsDetail.text =
                    getData?.content
                contentNewsDetail.tvDateNewsDetail.text =
                    Helper.timeAgo(getData?.publishedAt)

                if (getData?.author != null || getData?.source != null) {
                    contentNewsDetail.tvSourceNewsDetail.visibility =
                        View.VISIBLE
                    contentNewsDetail.tvAuthorNewsDetail.visibility =
                        View.VISIBLE
                    contentNewsDetail.tvSourceNewsDetail.text =
                        getData.source?.name
                    contentNewsDetail.tvAuthorNewsDetail.text =
                        getData.author
                } else {
                    contentNewsDetail.tvSourceNewsDetail.visibility =
                        View.GONE
                    contentNewsDetail.tvAuthorNewsDetail.visibility =
                        View.GONE
                }
            }
            activityNewsEverythingBinding?.imgDetailNews?.let {
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
        _activityNewsEverythingBinding = null
    }
}