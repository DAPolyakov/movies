package com.nikita.movies_shmoovies.posters

import com.nikita.movies_shmoovies.common.network.*

interface PostersInteractor {
    fun getMovies(): PostersPM
    fun getTvShows(): PostersPM
    fun getPeople(): PostersPM
}

data class PostersPM(var posters: List<Poster>) {
    data class Poster(val id: String,
                      val title: String,
                      val describe: String,
                      val image: String?,
                      val rating: String,
                      val date: String)
}

class BasePostersInteractor(val moviesService: MoviesService, val tvShowService: TvShowsService) : PostersInteractor {
    override fun getMovies(): PostersPM {
        val result: List<Movie>

        when (PostersFragment.filter) {
            PostersFragment.Filter.Upcoming -> result = moviesService.getUpcoming().results
            PostersFragment.Filter.Popular -> result = moviesService.getPopular().results
            PostersFragment.Filter.Top10 -> result = moviesService.getTop().results.subList(0, 10)
        }

        fun getCorrectValue(s: String?, type : String) : String{
            if (s != null){
                return s
            }
            when (type){
                "id"        ->  return "000000"
                "title"     ->  return "No Title"
                "overview"  ->  return ""
                "image"     ->  return "/mGN0lH2phYfesyEVqP2xvGUaxAQ.jpg"
                "rating"    ->  return "0"
                "date"      ->  return ""
            }
            return "1"
        }

        val posters = result.map { PostersPM.Poster(
                getCorrectValue(it.id, "id"),
                getCorrectValue(it.title, "title"),
                getCorrectValue(it.overview, "overview"),
                getCorrectValue(it.poster_path, "image"),
                getCorrectValue(it.vote_average, "rating"),
                getCorrectValue(it.release_date, "date")
        )}

        return PostersPM(posters)
    }

    override fun getTvShows(): PostersPM {
        val result: List<TvShow>
        when (PostersFragment.filter) {
            PostersFragment.Filter.Upcoming -> result = tvShowService.getOnTheAir().results
            PostersFragment.Filter.Popular -> result = tvShowService.getPopular().results
            PostersFragment.Filter.Top10 -> result = tvShowService.getTop().results.subList(0, 10)
        }
        val posters = result.map { PostersPM.Poster(it.id, it.name, it.overview, it.poster_path, it.vote_average, it.first_air_date) }

        return PostersPM(posters)
    }

    override fun getPeople(): PostersPM {
        return createFakePosters("People")
    }

    private fun createFakePosters(type: String): PostersPM {
        return PostersPM(List(40, {
            PostersPM.Poster(
                    id = it.toString(),
                    title = "$type$it",
                    image = "/tUMdinO528RqibbkKIAIRoGKq0g.jpg",
                    rating = "7.7",
                    date = "1.1.2001",
                    describe = "some describe")
        }))
    }
}