package id.heycoding.sportstesiyen.data

import id.heycoding.sportstesiyen.data.source.response.*
import id.heycoding.sportstesiyen.domain.model.*

fun List<League>?.mappingLeagueToUseCaseEntity(): List<Leagues> {
    val newList: MutableList<Leagues> = mutableListOf()

    this?.forEach {
        newList.add(
            Leagues(
                idLeague = it.idLeague,
                strLeague = it.strLeague,
                strLeagueAlternate = it.strLeagueAlternate,
                strSport = it.strSport
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<EventLeague>?.mappingEventLeagueToUseCaseEntity(): List<Event> {
    val newList: MutableList<Event> = mutableListOf()

    this?.forEach {
        newList.add(
            Event(
                dateEvent = it.dateEvent,
                dateEventLocal = it.dateEventLocal,
                idAPIfootball = it.idAPIfootball,
                idAwayTeam = it.idAwayTeam,
                idEvent = it.idEvent,
                idHomeTeam = it.idHomeTeam,
                idLeague = it.idLeague,
                idSoccerXML = it.idSoccerXML,
                intAwayScore = it.intAwayScore,
                intHomeScore = it.intHomeScore,
                intRound = it.intRound,
                intScore = it.intScore,
                intScoreVotes = it.intScoreVotes,
                intSpectators = it.intSpectators,
                strAwayTeam = it.strAwayTeam,
                strBanner = it.strBanner,
                strCity = it.strCity,
                strCountry = it.strCountry,
                strDescriptionEN = it.strDescriptionEN,
                strEvent = it.strEvent,
                strEventAlternate = it.strEventAlternate,
                strFanart = it.strFanart,
                strFilename = it.strFilename,
                strHomeTeam = it.strHomeTeam,
                strLeague = it.strLeague,
                strLocked = it.strLocked,
                strMap = it.strMap,
                strOfficial = it.strOfficial,
                strPoster = it.strPoster,
                strPostponed = it.strPostponed,
                strResult = it.strResult,
                strSeason = it.strSeason,
                strSport = it.strSport,
                strSquare = it.strSquare,
                strStatus = it.strStatus,
                strTVStation = it.strTVStation,
                strThumb = it.strThumb,
                strTime = it.strTime,
                strTimeLocal = it.strTimeLocal,
                strTimestamp = it.strTimestamp,
                strTweet1 = it.strTweet1,
                strTweet2 = it.strTweet2,
                strTweet3 = it.strTweet3,
                strVenue = it.strVenue,
                strVideo = it.strVideo
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<Equipment>?.mappingJerseyToUseCaseEntity(): List<Jersey> {
    val newList: MutableList<Jersey> = mutableListOf()

    this?.forEach {
        newList.add(
            Jersey(
                date = it.date,
                idEquipment = it.idEquipment,
                idTeam = it.idTeam,
                strEquipment = it.strEquipment,
                strSeason = it.strSeason,
                strType = it.strType,
                strUsername = it.strUsername
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<Season>?.mappingSeasonToUseCaseEntity(): List<Seasons> {
    val newList: MutableList<Seasons> = mutableListOf()

    this?.forEach {
        newList.add(
            Seasons(
                strSeason = it.strSeason,
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<TeamsLeague>?.mappingTeamsToUseCaseEntity(): List<Teams> {
    val newList: MutableList<Teams> = mutableListOf()

    this?.forEach {
        newList.add(
            Teams(
                idAPIfootball = it.idAPIfootball,
                idLeague = it.idLeague,
                idLeague2 = it.idLeague2,
                idLeague3 = it.idLeague3,
                idLeague4 = it.idLeague4,
                idLeague5 = it.idLeague5,
                idLeague6 = it.idLeague6,
                idLeague7 = it.idLeague7,
                idSoccerXML = it.idSoccerXML,
                idTeam = it.idTeam,
                intFormedYear = it.intFormedYear,
                intLoved = it.intLoved,
                intStadiumCapacity = it.intStadiumCapacity,
                strAlternate = it.strAlternate,
                strCountry = it.strCountry,
                strDescriptionCN = it.strDescriptionCN,
                strDescriptionDE = it.strDescriptionDE,
                strDescriptionEN = it.strDescriptionEN,
                strDescriptionES = it.strDescriptionES,
                strDescriptionFR = it.strDescriptionFR,
                strDescriptionHU = it.strDescriptionHU,
                strDescriptionIL = it.strDescriptionIL,
                strDescriptionIT = it.strDescriptionIT,
                strDescriptionJP = it.strDescriptionJP,
                strDescriptionNL = it.strDescriptionNL,
                strDescriptionNO = it.strDescriptionNO,
                strDescriptionPL = it.strDescriptionPL,
                strDescriptionPT = it.strDescriptionPT,
                strDescriptionRU = it.strDescriptionRU,
                strDescriptionSE = it.strDescriptionSE,
                strDivision = it.strDivision,
                strFacebook = it.strFacebook,
                strGender = it.strGender,
                strInstagram = it.strInstagram,
                strKeywords = it.strKeywords,
                strKitColour1 = it.strKitColour1,
                strKitColour2 = it.strKitColour2,
                strKitColour3 = it.strKitColour3,
                strLeague = it.strLeague,
                strLeague2 = it.strLeague2,
                strLeague3 = it.strLeague3,
                strLeague4 = it.strLeague4,
                strLeague5 = it.strLeague5,
                strLeague6 = it.strLeague6,
                strLeague7 = it.strLeague7,
                strLocked = it.strLocked,
                strManager = it.strManager,
                strRSS = it.strRSS,
                strSport = it.strSport,
                strStadium = it.strStadium,
                strStadiumDescription = it.strStadiumDescription,
                strStadiumLocation = it.strStadiumLocation,
                strStadiumThumb = it.strStadiumThumb,
                strTeam = it.strTeam,
                strTeamBadge = it.strTeamBadge,
                strTeamBanner = it.strTeamBanner,
                strTeamFanart1 = it.strTeamFanart1,
                strTeamFanart2 = it.strTeamFanart2,
                strTeamFanart3 = it.strTeamFanart3,
                strTeamFanart4 = it.strTeamFanart4,
                strTeamJersey = it.strTeamJersey,
                strTeamLogo = it.strTeamLogo,
                strTeamShort = it.strTeamShort,
                strTwitter = it.strTwitter,
                strWebsite = it.strWebsite,
                strYoutube = it.strYoutube
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<Articles>?.mappingNewsToUseCaseEntity(): List<News> {
    val newList: MutableList<News> = mutableListOf()

    this?.forEach {
        newList.add(
            News(
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                source = Sources(name = it.source.name),
                title = it.title,
                url = it.url,
                content = it.content
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<Table>?.mappingStatisticToUseCaseEntity(): List<Statistic> {
    val newList: MutableList<Statistic> = mutableListOf()

    this?.forEach {
        newList.add(
            Statistic(
                dateUpdated = it.dateUpdated,
                idLeague = it.idLeague,
                idStanding = it.idStanding,
                idTeam = it.idTeam,
                intDraw = it.intDraw,
                intGoalDifference = it.intGoalDifference,
                intGoalsAgainst = it.intGoalsAgainst,
                intGoalsFor = it.intGoalsFor,
                intLoss = it.intLoss,
                intPlayed = it.intPlayed,
                intPoints = it.intPoints,
                intRank = it.intRank,
                intWin = it.intWin,
                strDescription = it.strDescription,
                strForm = it.strForm,
                strLeague = it.strLeague,
                strSeason = it.strSeason,
                strTeam = it.strTeam,
                strTeamBadge = it.strTeamBadge
            )
        )
    }



    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}