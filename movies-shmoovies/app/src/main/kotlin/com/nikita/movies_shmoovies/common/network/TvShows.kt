package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.info.Genre

data class TvShow(val id: String,
                  val name: String,
                  val overview: String,
                  val first_air_date: String,
                  val poster_path: String,
                  val vote_average: String)

data class TvShowDetail(
        val id: String,
        val name: String,
        val overview: String?,
        val poster_path: String?,
        val backdrop_path: String?,
        val genres: List<Genre>,
        val first_air_date: String?,
        val original_name: String?,
        val status: String?,
        val original_language: String?,
        val homepage: String?,
        val episode_run_time: List<String>?,
        val number_of_episodes: String?,
        val number_of_seasons: String?


)
