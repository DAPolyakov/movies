package com.nikita.movies_shmoovies.posters

import android.content.Intent
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.nikita.movies_shmoovies.InfoPosterActivity
import com.nikita.movies_shmoovies.AppRouter
import com.nikita.movies_shmoovies.common.mvp.BaseMvpPresenter
import com.nikita.movies_shmoovies.posters.PostersPresenter.Companion.view

@InjectViewState
class PostersPresenter(private val behavior: Behavior) : BaseMvpPresenter<PostersView>() {

  companion object{
    lateinit var view : View
  }

  override fun onFirstViewAttach() = loadContent(pullToRefresh = false)

  private fun loadContent(pullToRefresh: Boolean, addition : Boolean = false) {
    launchLce(viewState, pullToRefresh, addition) {
      behavior.loadContent()
    }
  }

  fun onRefreshTriggered() = loadContent(pullToRefresh = true)
  fun onPosterClick(id: String) = behavior.onPosterClick(id)
  fun loadMorePosters(){
      loadContent(pullToRefresh = true, addition = true)
  }

  abstract class Behavior {
    abstract fun loadContent(): PostersPM
    abstract fun onPosterClick(id: String)
  }
}

class MoviePostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getMovies()

  override fun onPosterClick(id: String) {
    val intent = Intent(view.context, InfoPosterActivity::class.java)
    intent.putExtra("id", id)
    PostersPresenter.view.context.startActivity(intent)
  }
}

class TvPostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getTvShows()

  override fun onPosterClick(id: String) {
    throw UnsupportedOperationException("not implemented")
  }
}

class PeoplePostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getPeople()

  override fun onPosterClick(id: String) {
    throw UnsupportedOperationException("not implemented")
  }
}