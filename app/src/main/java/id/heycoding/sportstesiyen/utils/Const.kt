package id.heycoding.sportstesiyen.utils

object Const {
    const val BASE_URL_THESPORTDB = "https://www.thesportsdb.com/api/v1/json/3/"
    const val ID_LEAGUE_THESPORTDB = "4328"
    const val YEAR_SEASON_LEAGUE_THESPORTDB = "2022-2023"
    const val LEAGUE_THESPORTDB = "English Premier League"

    const val BASE_URL_NEWSAPI = "https://newsapi.org/v2/"
    const val API_KEY_NEWSAPI = "835d5b8b0de0440ab3eb8aaf2410d374"
    const val CATEGORY_NEWSAPI = "sport"
    const val COUNTRY_US_NEWSAPI = "us"
    const val COUNTRY_ID_NEWSAPI = "id"
    const val FROM_NEWSAPI = "2023-01-07"
    const val TO_NEWSAPI = "2023-12-31"
    const val SORT_NEWSAPI = "popularity"

    const val EXTRA_PHONE_NUMBER = "phone_number"
    const val EXTRA_OTP_NUMBER = "otp_number"
    const val EXTRA_USER_ACCOUNT = "user_account"
    const val LIMIT = 5
    const val REQUEST_CAMERA = 100
}

object ConstSports {
    const val GET_ALL_TEAMS_IN_LEAGUE = "search_all_teams.php"
    const val GET_EVENTS_SEASON = "eventsseason.php"
    const val GET_EQUIPMENT_TEAMS = "lookupequipment.php"
    const val GET_STATISTIC_TABLE = "lookuptable.php"
    const val GET_ALL_LEAGUE = "all_leagues.php"
    const val GET_ALL_SEASON = "search_all_seasons.php"

    const val EXTRA_EVENT = "event"
    const val EXTRA_TEAMS = "teams"
    const val EXTRA_DATA_TEAMS = "data_teams"
    const val DUMMY_EMAIL = "mockdroid77@gmail.com"
    const val DUMMY_VIDEO =
        "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
}

object ConstNews {
    const val GET_TOP_HEADLINES_NEWS_SPORT = "top-headlines"
    const val GET_EVERYTHING_NEWS_SPORT = "everything"

    const val EXTRA_NEWS_EVERYTHING = "news_everything"
    const val EXTRA_NEWS_TOPHEADLINE = "news_topheadline"
}