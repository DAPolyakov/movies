package com.nikita.movies_shmoovies.info

import com.nikita.movies_shmoovies.common.network.MoviesService
import com.nikita.movies_shmoovies.common.network.TvShowsService
import com.nikita.movies_shmoovies.info.BaseInfoInteractor.Companion.contentId

interface InfoInteractor {
    fun getDetailMovies(): Info
    fun getDetailTvShows(): Info
    fun getDetailPeople(): Info
}

data class Genre(
        val id: String,
        val name: String
)

data class Info(val id: String,
                val title: String,
                val describe: String?,
                val mainImage: String?,
                val secondImage: String?,
                val date: String?,
                val genre: List<Genre>,
                val cast: List<Actor>,
                val crew: List<Actor>,
                val originalTitle : String?,
                val status : String?,
                val originalLanguage: String?,
                val runtime : String?,
                val budget : String?,
                val revenue : String?,
                val homepage: String?,
                val episode_run_time: String?,
                val number_of_episodes: String?,
                val number_of_seasons: String?)

data class Cast(val id: String,
                val profile_path: String?,
                val name: String,
                val character: String)

data class Crew(val id: String,
                val profile_path: String?,
                val name: String,
                val department: String,
                val job : String)

data class Actor(val id: String,
                 val image: String?,
                 val name : String,
                 val role : String
)

data class ListActors(val cast: List<Cast>, val crew: List<Crew>)

class BaseInfoInteractor(val moviesService: MoviesService, val tvShowsService: TvShowsService) : InfoInteractor {

    companion object {
        var contentId = ""
    }

    private fun splitName(name : String) : String{
        var res = ""
        var key = true
        for(c in name){
            if (key && c == ' ') {
                key = false
                res += '\n'
            }else {
                res += c
            }
        }
        return res
    }
    private fun splitRuntime(time: String?) : String{
        if (time==null){
            return "-"
        }
        var minutes = time.toInt()
        val hours = minutes / 60
        minutes %= 60
        var strMinutes = minutes.toString()
        if (strMinutes.length == 1){
            strMinutes = "0" + strMinutes
        }
        var strHours = hours.toString()
        if (strHours.length == 1){
            strHours = "0" + strHours
        }

        return strHours + ":" + strMinutes
    }

    override fun getDetailMovies(): Info {
        val details = moviesService.getDetails(contentId)
        val credits = moviesService.getCredits(contentId)
        val cast = credits.cast.map{Actor(
                it.id,
                it.profile_path,
                splitName(it.name),
                it.character
        )}
        val crew = credits.crew.map{Actor(
                it.id,
                it.profile_path,
                splitName(it.name),
                it.department + " / " + it.job
        )}

        val info = Info(details.id,
                title = details.title,
                describe = details.overview,
                mainImage = details.poster_path,
                secondImage = details.backdrop_path,
                date = details.release_date,
                genre = details.genres,
                cast = cast,
                crew = crew,
                originalTitle = details.original_title,
                status = details.status,
                originalLanguage = details.original_language,
                runtime = splitRuntime(details.runtime),
                budget = details.budget,
                revenue = details.revenue,
                homepage = details.homepage,
                episode_run_time = null,
                number_of_episodes = null,
                number_of_seasons = null
        )
        return info
    }

    override fun getDetailTvShows(): Info {
        val details = tvShowsService.getDetails(contentId)
        val credits = tvShowsService.getCredits(contentId)

        val cast = credits.cast.map {
            Actor(
                    it.id,
                    it.profile_path,
                    splitName(it.name),
                    it.character
            )
        }
        val crew = credits.crew.map {
            Actor(
                    it.id,
                    it.profile_path,
                    splitName(it.name),
                    it.department + " / " + it.job
            )
        }

        val info = Info(details.id,
                title = details.name,
                describe = details.overview,
                mainImage = details.poster_path,
                secondImage = details.backdrop_path,
                date = details.first_air_date,
                genre = details.genres,
                cast = cast,
                crew = crew,
                originalTitle = details.original_name,
                status = details.status,
                originalLanguage = details.original_language,
                runtime = null,
                budget = null,
                revenue = null,
                homepage = details.homepage,
                episode_run_time = details.episode_run_time?.get(0),
                number_of_episodes = details.number_of_episodes,
                number_of_seasons = details.number_of_seasons
        )
        return info
    }

    override fun getDetailPeople(): Info {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}