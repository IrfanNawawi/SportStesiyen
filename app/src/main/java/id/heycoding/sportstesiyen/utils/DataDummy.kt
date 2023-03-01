package id.heycoding.sportstesiyen.utils

import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.ui.account.AccountMenu
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.DetailTeamsMenu
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingItem

object DataDummy {

    fun generateDummyOnBoarding(): ArrayList<OnBoardingItem> {
        val onBoarding = ArrayList<OnBoardingItem>()

        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_category_onboarding,
                titleOnBoarding = "Category Sport",
                descOnBoarding = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_news_onboarding,
                titleOnBoarding = "News Sport",
                descOnBoarding = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_listdata_onboarding,
                titleOnBoarding = "Data Player Sport",
                descOnBoarding = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        return onBoarding
    }

    fun generateDummyBanner(): ArrayList<BannerData> {
        val banner = ArrayList<BannerData>()
        banner.add(
            BannerData(
                imgUrl = "https://www.thesportsdb.com/images/sports/soccer.jpg"
            )
        )
        banner.add(
            BannerData(
                imgUrl = "https://www.thesportsdb.com/images/sports/soccer.jpg"
            )
        )
        return banner
    }

    fun generateDummyDetailTeamsMenu(): ArrayList<DetailTeamsMenu> {
        val detailTeamsMenu = ArrayList<DetailTeamsMenu>()
        detailTeamsMenu.add(
            DetailTeamsMenu(
                menuDetailTeams = "Description"
            )
        )
        detailTeamsMenu.add(
            DetailTeamsMenu(
                menuDetailTeams = "Stadium"
            )
        )
        detailTeamsMenu.add(
            DetailTeamsMenu(
                menuDetailTeams = "Jersey"
            )
        )
        detailTeamsMenu.add(
            DetailTeamsMenu(
                menuDetailTeams = "League"
            )
        )
        detailTeamsMenu.add(
            DetailTeamsMenu(
                menuDetailTeams = "News"
            )
        )
        detailTeamsMenu.add(
            DetailTeamsMenu(
                menuDetailTeams = "Contact"
            )
        )
        return detailTeamsMenu
    }

    fun generateAccountMenu(): ArrayList<AccountMenu> {
        val accountMenu = ArrayList<AccountMenu>()
        accountMenu.add(
            AccountMenu(
                menuAccountId = 1,
                menuAccountImage = R.drawable.ic_password,
                menuAccountName = "Change Password",
                menuAccountSwitch = 0
            )
        )
        accountMenu.add(
            AccountMenu(
                menuAccountId = 2,
                menuAccountImage = R.drawable.ic_dark_mode,
                menuAccountName = "Enable Dark Mode",
                menuAccountSwitch = 1
            )
        )
        accountMenu.add(
            AccountMenu(
                menuAccountId = 3,
                menuAccountImage = R.drawable.ic_language,
                menuAccountName = "Change Language",
                menuAccountSwitch = 0
            )
        )
        accountMenu.add(
            AccountMenu(
                menuAccountId = 4,
                menuAccountImage = R.drawable.ic_help,
                menuAccountName = "Help",
                menuAccountSwitch = 0
            )
        )
        accountMenu.add(
            AccountMenu(
                menuAccountId = 5,
                menuAccountImage = R.drawable.ic_logout,
                menuAccountName = "Log Out",
                menuAccountSwitch = 0
            )
        )
        return accountMenu
    }

}