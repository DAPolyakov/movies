package com.nikita.movies_shmoovies.info

import com.arellomobile.mvp.InjectViewState
import com.nikita.movies_shmoovies.AppRouter
import com.nikita.movies_shmoovies.common.mvp.BaseMvpPresenter

@InjectViewState
class InfoPresenter (private val behavior: Behavior) : BaseMvpPresenter<InfoView>() {
    
    override fun onFirstViewAttach() = loadContent()

    private fun loadContent() {
        launchLce(viewState) {
            behavior.loadContent()
        }
    }

    abstract class Behavior{
        abstract fun loadContent(): Info
    }
}

class MovieInfoBehavior(private val infoInteractor: InfoInteractor,
                           private val router: AppRouter): InfoPresenter.Behavior() {
    override fun loadContent(): Info = infoInteractor.getDetailMovies()
}