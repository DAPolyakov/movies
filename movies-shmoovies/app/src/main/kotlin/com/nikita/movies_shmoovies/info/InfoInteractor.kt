package com.nikita.movies_shmoovies.info

import com.nikita.movies_shmoovies.common.network.MoviesService
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
                val homepage : String?)

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

class BaseInfoInteractor(val moviesService: MoviesService) : InfoInteractor {

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
        var hours = minutes / 60
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
                details.title,
                details.overview,
                details.poster_path,
                details.backdrop_path,
                details.release_date,
                details.genres,
                cast,
                crew,
                details.original_title,
                details.status,
                details.original_language,
                splitRuntime(details.runtime),
                details.budget,
                details.revenue,
                details.homepage
        )
        return info
    }

    override fun getDetailTvShows(): Info {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDetailPeople(): Info {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}