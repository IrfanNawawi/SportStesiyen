package id.heycoding.sportstesiyen.di

import id.heycoding.sportstesiyen.ui.auth.AuthViewModel
import id.heycoding.sportstesiyen.ui.home.HomeViewModel
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.DetailTeamsLeagueViewModel
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey.JerseyViewModel
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news.NewsViewModel
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingViewModel
import id.heycoding.sportstesiyen.ui.otp.OtpViewModel
import id.heycoding.sportstesiyen.ui.statistic.StatisticViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module

@KoinReflectAPI
val viewModelModule = module {
    viewModel<AuthViewModel>()
    viewModel<OnBoardingViewModel>()
    viewModel<OtpViewModel>()
    viewModel<HomeViewModel>()
    viewModel<DetailTeamsLeagueViewModel>()
    viewModel<JerseyViewModel>()
    viewModel<NewsViewModel>()
    viewModel<StatisticViewModel>()
}