package com.nikita.movies_shmoovies.posters

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.appModule
import com.nikita.movies_shmoovies.common.EXTRA_TYPE
import com.nikita.movies_shmoovies.common.mvp.BaseMvpFragment
import com.nikita.movies_shmoovies.common.utils.findView


class PostersFragment : BaseMvpFragment<PostersPM>(), PostersView {

    companion object {
        var type: PostersFragment.Type = PostersFragment.Type.Movies
        var filter: PostersFragment.Filter = PostersFragment.Filter.Upcoming
        var isLoading = false
        var swipeRefreshLayout: SwipeRefreshLayout? = null
        var page = 1
        lateinit var recyclerView: RecyclerView
        fun create(type: Type): PostersFragment {
            val fragment = PostersFragment()
            isLoading = false
            this.type = type
            page = 1
            fragment.arguments = Bundle(1).apply { putString(EXTRA_TYPE, type.name) }
            return fragment
        }
    }

    override val layout: Int = R.layout.posters_content

    @InjectPresenter
    lateinit var presenter: PostersPresenter

    @ProvidePresenter
    fun providePresenter(): PostersPresenter {
        val type = Type.valueOf(arguments.getString(EXTRA_TYPE))
        val appModule = activity.appModule
        val behavior = when (type) {
            Type.Movies -> MoviePostersBehavior(appModule.postersInteractor, appModule.appRouter)
            Type.TvShows -> TvPostersBehavior(appModule.postersInteractor, appModule.appRouter)
            Type.People -> PeoplePostersBehavior(appModule.postersInteractor, appModule.appRouter)
        }
        return PostersPresenter(behavior)
    }

    private val adapter = PostersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PostersPresenter.view = view

        swipeRefreshLayout = view.findViewById(R.id.refresh_posters) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                presenter.onRefreshTriggered()
                swipeRefreshLayout!!.isRefreshing = false
            }
        })


        recyclerView = view.findView<RecyclerView>(R.id.content_view)
        if (filter != Filter.Top10) {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else {
            recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }

        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager.childCount
                val totalItemCount = recyclerView.layoutManager.itemCount
                val result = IntArray(20)
                val firstVisibleItem = (recyclerView.layoutManager as StaggeredGridLayoutManager)
                        .findFirstVisibleItemPositions(result)
                if ( (!isLoading) && (PostersFragment.filter!=PostersFragment.Filter.Top10) ) {
                    if ((visibleItemCount + firstVisibleItem[0]) >= totalItemCount) {
                        loadMorePosters()
                    }
                }
            }
        })

        adapter.itemClickAction = {
            presenter.onPosterClick(it.id)
        }
    }

    private fun loadMorePosters() {
        isLoading = true
        presenter.loadMorePosters()
    }

    override fun setContent(content: PostersPM) {
        super.setContent(content)
        page = 2
        adapter.data.addAll(content.posters)
        adapter.notifyDataSetChanged()
    }

    override fun addContent(content: PostersPM) {
        page++
        adapter.data.addAll(content.posters)
        adapter.notifyDataSetChanged()
        isLoading = false
    }

    enum class Type {
        Movies, TvShows, People
    }

    enum class Filter {
        Upcoming, Popular, Top10
    }
}