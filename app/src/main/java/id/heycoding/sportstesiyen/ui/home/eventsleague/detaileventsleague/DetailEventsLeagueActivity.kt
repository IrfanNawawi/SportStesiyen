package id.heycoding.sportstesiyen.ui.home.eventsleague.detaileventsleague

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import id.heycoding.sportstesiyen.data.entity.EventLeague
import id.heycoding.sportstesiyen.databinding.ActivityDetailEventsLeagueBinding
import id.heycoding.sportstesiyen.utils.ConstSports

class DetailEventsLeagueActivity : AppCompatActivity() {
    private var _activityDetailEventsLeagueBinding: ActivityDetailEventsLeagueBinding? = null
    private val activityDetailEventsLeagueBinding get() = _activityDetailEventsLeagueBinding
    private var urlVideo: String? = null
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailEventsLeagueBinding =
            ActivityDetailEventsLeagueBinding.inflate(layoutInflater)
        setContentView(activityDetailEventsLeagueBinding?.root)

        setSupportActionBar(activityDetailEventsLeagueBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (supportActionBar != null) {
            supportActionBar?.title = ""
        }

        setupUI()
    }

    private fun setupUI() {
        val extras = intent.extras
        if (extras != null) {
            val getData = extras.getParcelable<EventLeague>(ConstSports.EXTRA_EVENT)

            activityDetailEventsLeagueBinding?.apply {
                Glide.with(this@DetailEventsLeagueActivity).load("${getData?.strFanart}/preview")
                    .into(imgBannerEvent)
                tvVenueEvent.text = "${getData?.strVenue}, "
                tvCountryEvent.text = getData?.strCountry
                tvDateEvent.text = getData?.dateEvent
                tvHomeEvent.text = getData?.strHomeTeam
                tvAwayEvent.text = getData?.strAwayTeam
                tvHomeScoreEvent.text = getData?.intHomeScore
                tvAwayScoreEvent.text = getData?.intAwayScore
                tvDetailEvent.text = getData?.strEvent
                tvLeagueDetailEvent.text = "${getData?.strLeague}, "
                tvSeasonDetailEvent.text = getData?.strSeason
                tvDescDetailEvent.text = getData?.strDescriptionEN
                urlVideo = getData?.strVideo
//                initializePlayer(ConstSports.DUMMY_VIDEO)

                appbarDetailEvent.addOnOffsetChangedListener(object :
                    AppBarLayout.OnOffsetChangedListener {
                    var isShow = false
                    var scrollRange = -1
                    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.totalScrollRange
                        }
                        if (scrollRange + verticalOffset == 0) {
                            tvTitleToolbar.text = getData?.strEvent
                            clDetailEvent.visibility = View.GONE
                            isShow = true
                        } else if (isShow) {
                            tvTitleToolbar.text = ""
                            clDetailEvent.visibility = View.VISIBLE
                            isShow = false
                        }
                    }
                })
            }
        }
    }

//    private fun initializePlayer(liveUrl: String) {
//        player = ExoPlayer.Builder(this)
//            .build()
//            .also { exoPlayer ->
//                activityDetailEventsLeagueBinding?.playerviewDetailEvent?.player = exoPlayer
//
//                val mediaItem = MediaItem.fromUri(liveUrl)
//                exoPlayer.setMediaItem(mediaItem)
//                exoPlayer.playWhenReady = playWhenReady
//                exoPlayer.seekTo(currentItem, playbackPosition)
//                exoPlayer.prepare()
//            }
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _activityDetailEventsLeagueBinding = null
    }

    public override fun onStart() {
        super.onStart()
//        if (Util.SDK_INT > 24) {
//            initializePlayer(ConstSports.DUMMY_VIDEO)
//        }
    }

    public override fun onResume() {
        super.onResume()
//        hideSystemUi()
//        if (Util.SDK_INT <= 24 || player == null) {
//            initializePlayer(ConstSports.DUMMY_VIDEO)
//        }
    }

    public override fun onPause() {
        super.onPause()
//        if (Util.SDK_INT <= 24) {
//            releasePlayer()
//        }
    }

    public override fun onStop() {
        super.onStop()
//        if (Util.SDK_INT > 24) {
//            releasePlayer()
//        }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

//    private fun hideSystemUi() {
//        activityDetailEventsLeagueBinding?.playerviewDetailEvent?.systemUiVisibility =
//            (View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
//    }
}