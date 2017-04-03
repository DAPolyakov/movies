package com.nikita.movies_shmoovies

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.common.mvp.ErrorDesc
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.load
import com.nikita.movies_shmoovies.info.*
import com.nikita.movies_shmoovies.posters.PostersFragment
import com.nikita.movies_shmoovies.posters.PostersInteractor
import com.nikita.movies_shmoovies.posters.PostersPresenter

class InfoPosterActivity :  MvpAppCompatActivity(), InfoView {

    lateinit private var title : TextView
    lateinit private var describe : TextView
    lateinit private var date : TextView
    lateinit private var imageMain : ImageView
    lateinit private var imageSecond : ImageView
    lateinit private var recyclerViewCast : RecyclerView
    lateinit private var recyclerViewCrew : RecyclerView

    lateinit private var genres : TextView
    lateinit private var originalTitle : TextView
    lateinit private var status : TextView
    lateinit private var originalLanguage : TextView
    lateinit private var runtime : TextView
    lateinit private var budget : TextView
    lateinit private var revenue : TextView
    lateinit private var homepage : TextView

    private val castAdapter = InfoCreditAdapter()
    private val crewAdapter = InfoCreditAdapter()

    override fun setContent(content: Info) {
        title.text = content.title
        describe.text = content.describe
        date.text = content.date
        imageMain.load(content.secondImage) // TODO need clever swap
        imageSecond.load(content.mainImage) // yes, I know

        if (content.cast.isEmpty()){
            findView<TextView>(R.id.content_label_cast).visibility = View.GONE
            recyclerViewCast.visibility = View.GONE
        } else {
            castAdapter.data.addAll(content.cast)
            castAdapter.notifyDataSetChanged()
        }

        if (content.crew.isEmpty()) {
            findView<TextView>(R.id.content_label_crew).visibility = View.GONE
            recyclerViewCrew.visibility = View.GONE
        }else {
            crewAdapter.data.addAll(content.crew)
            crewAdapter.notifyDataSetChanged()
        }

        setText(genres, getOneLineGenres(content.genre))
        setText(originalTitle, content.originalTitle)
        setText(status, content.status)
        setText(originalLanguage, content.originalLanguage)
        setText(runtime, content.runtime)
        setText(budget, content.budget, "$")
        setText(revenue, content.revenue, "$")
        setText(homepage, content.homepage)

        if (PostersFragment.type == PostersFragment.Type.TvShows) {
            val numberOfSeasons = runtime
            val numberOfEpisodes = budget
            val episodeRunTime = revenue
            findView<TextView>(R.id.content_label_runtime).text = getString(R.string.seasons)
            findView<TextView>(R.id.content_label_budget).text = getString(R.string.episodes)
            findView<TextView>(R.id.content_label_revenue).text = getString(R.string.episode_run_time)

            setText(numberOfSeasons, content.number_of_seasons)
            setText(numberOfEpisodes, content.number_of_episodes)
            setText(episodeRunTime, content.episode_run_time)
        }
    }

    private fun setText(view : TextView, text : String?, additionalSymbols : String = ""){
        var x = text
        if (x == null || x == "0" || x == "") {
            x = "-"
        } else {
            x += additionalSymbols
        }
        view.text = x
    }
    private fun getOneLineGenres(genres:List<Genre>) : String{
        var ans = ""
        for (genre in genres){
            if (ans.isNotEmpty()){
                ans += " / "
            }
            ans += genre.name
        }
        return ans
    }

    override fun addContent(content: Info) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun switchToLoading(pullToRefresh: Boolean) {

    }

    override fun switchToError(errorDesc: ErrorDesc) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    @ProvidePresenter
    fun providePresenter(): InfoPresenter{
        val type = Type.valueOf(intent.getStringExtra("type"))
//        val type = Type.Movies
        val appModule = appModule
        val behavior = when (type){
            InfoPosterActivity.Type.Movies -> MovieInfoBehavior(appModule.infoInteractor, appModule.appRouter)
            InfoPosterActivity.Type.TvShows -> TvShowInfoBehavior(appModule.infoInteractor, appModule.appRouter)
            InfoPosterActivity.Type.People -> TODO()
        }
        return InfoPresenter(behavior)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_poster)

        title = findView<TextView>(R.id.content_title)
        describe = findView<TextView>(R.id.content_overview)
        date = findView<TextView>(R.id.content_date)
        imageMain = findView<ImageView>(R.id.content_poster_main)
        imageSecond = findView<ImageView>(R.id.content_poster_second)
        recyclerViewCast = findView<RecyclerView> (R.id.cast_view)
        recyclerViewCrew = findView<RecyclerView> (R.id.crew_view)

        genres = findView<TextView>(R.id.content_genres)
        originalTitle = findView<TextView>(R.id.content_original_title)
        status = findView<TextView>(R.id.content_status)
        originalLanguage = findView<TextView>(R.id.content_original_language)
        runtime = findView<TextView>(R.id.content_runtime)
        budget = findView<TextView>(R.id.content_budget)
        revenue = findView<TextView>(R.id.content_revenue)
        homepage = findView<TextView>(R.id.content_homepage)

        initRecyclerViews()
        BaseInfoInteractor.contentId = intent.getStringExtra("id")
    }

    private fun initRecyclerViews() {
        recyclerViewCast.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        recyclerViewCast.adapter = castAdapter

        recyclerViewCrew.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        recyclerViewCrew.adapter = crewAdapter
    }

    enum class Type {
        Movies, TvShows, People
    }

}